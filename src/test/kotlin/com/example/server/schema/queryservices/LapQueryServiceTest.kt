package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.lap.Lap as JolpicaLap
import com.example.client.jolpica.schema.models.lap.RaceLaps as JolpicaRaceLaps
import com.example.client.jolpica.schema.models.lap.Timing as JolpicaTiming

class LapQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val lapQueryService = LapQueryService(jolpicaClient)

    private val sampleCircuit = JolpicaCircuit(
        id = "monaco",
        url = "url",
        name = "Monaco",
        location = JolpicaLocation(locality = "Monte Carlo", country = "Monaco")
    )

    private val sampleRaceLaps = JolpicaRaceLaps(
        season = 2023,
        round = 6,
        url = "url",
        raceName = "Monaco Grand Prix",
        circuit = sampleCircuit,
        date = "2023-05-28",
        laps = listOf(
            JolpicaLap(
                number = 1,
                timings = listOf(
                    JolpicaTiming(driverId = "max_verstappen", position = 1, time = "1:15.000")
                )
            )
        )
    )

    @Test
    fun `laps should fetch and cache race laps`() {
        runBlocking {
            coEvery { jolpicaClient.getLaps(2023, 6) } returns listOf(sampleRaceLaps)

            val result1 = lapQueryService.laps(2023, 6)
            val result2 = lapQueryService.laps(2023, 6)

            assertEquals(result1, result2)
            assertEquals("Monaco Grand Prix", result1.raceName)
            coVerify(exactly = 1) { jolpicaClient.getLaps(2023, 6) }
        }
    }

    @Test
    fun `laps should respect driver filter in cache key`() {
        runBlocking {
            coEvery { jolpicaClient.getLaps(2023, 6) } returns listOf(sampleRaceLaps)

            val resultAll = lapQueryService.laps(2023, 6, null)
            val resultMax = lapQueryService.laps(2023, 6, listOf("max_verstappen"))

            assertEquals(1, resultAll.laps[0].timings.size)
            assertEquals(1, resultMax.laps[0].timings.size)
            coVerify(exactly = 2) { jolpicaClient.getLaps(2023, 6) }
        }
    }
}
