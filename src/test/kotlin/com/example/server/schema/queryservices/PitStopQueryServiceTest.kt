package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.ktor.server.plugins.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.pitstop.PitStop as JolpicaPitStop
import com.example.client.jolpica.schema.models.pitstop.RacePitStop as JolpicaRacePitStop

class PitStopQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val pitStopQueryService = PitStopQueryService(jolpicaClient)

    private val sampleCircuit = JolpicaCircuit(
        id = "monaco",
        url = "url",
        name = "Monaco",
        location = JolpicaLocation(locality = "Monte Carlo", country = "Monaco")
    )

    private val sampleRacePitStops = JolpicaRacePitStop(
        season = 2023,
        round = 6,
        url = "url",
        raceName = "Monaco Grand Prix",
        circuit = sampleCircuit,
        date = "2023-05-28",
        time = "15:00:00Z",
        stops = listOf(
            JolpicaPitStop(driverId = "max_verstappen", lap = 20, stop = 1, time = "15:30:00", duration = "24.500")
        )
    )

    @Test
    fun `pitStops should fetch and cache pit stops`() {
        runBlocking {
            coEvery { jolpicaClient.getPitStops(2023, 6) } returns sampleRacePitStops

            val result1 = pitStopQueryService.pitStops(2023, 6)
            val result2 = pitStopQueryService.pitStops(2023, 6)

            assertEquals(result1, result2)
            assertEquals("Monaco Grand Prix", result1.raceName)
            coVerify(exactly = 1) { jolpicaClient.getPitStops(2023, 6) }
        }
    }

    @Test
    fun `pitStops should respect driver filter in cache key`() {
        runBlocking {
            coEvery { jolpicaClient.getPitStops(2023, 6) } returns sampleRacePitStops

            val resultAll = pitStopQueryService.pitStops(2023, 6, null)
            val resultMax = pitStopQueryService.pitStops(2023, 6, listOf("max_verstappen"))

            assertEquals(1, resultAll.stops.size)
            assertEquals(1, resultMax.stops.size)
            coVerify(exactly = 2) { jolpicaClient.getPitStops(2023, 6) }
        }
    }

    @Test
    fun `pitStops should throw NotFoundException when client returns null`() {
        runBlocking {
            coEvery { jolpicaClient.getPitStops(2023, 6) } returns null

            assertFailsWith<NotFoundException> {
                pitStopQueryService.pitStops(2023, 6)
            }
        }
    }
}
