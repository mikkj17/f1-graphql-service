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
import com.example.client.jolpica.schema.models.result.Race as JolpicaRace

class RaceQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val raceQueryService = RaceQueryService(jolpicaClient)

    private val sampleJolpicaRace = JolpicaRace(
        season = 2023,
        round = 1,
        url = "https://test.com",
        name = "Bahrain Grand Prix",
        circuit = JolpicaCircuit(
            id = "bahrain",
            url = "https://test.com/circuit",
            name = "Bahrain International Circuit",
            location = JolpicaLocation(
                latitude = 26.0325f,
                longitude = 50.5106f,
                locality = "Sakhir",
                country = "Bahrain"
            )
        ),
        date = "2023-03-05",
        time = "15:00:00Z",
        results = emptyList()
    )

    @Test
    fun `race should return race when found`() {
        runBlocking {
            coEvery { jolpicaClient.getRaces(2023, 1) } returns listOf(sampleJolpicaRace)

            val result = raceQueryService.race(2023, 1)

            assertEquals(2023, result.season)
            assertEquals(1, result.round)
            assertEquals("Bahrain Grand Prix", result.name)
        }
    }

    @Test
    fun `race should throw NotFoundException when no race found`() {
        runBlocking {
            coEvery { jolpicaClient.getRaces(2023, 2) } returns emptyList()

            assertFailsWith<NotFoundException> {
                raceQueryService.race(2023, 2)
            }
        }
    }

    @Test
    fun `races should require exactly one filter`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                raceQueryService.races(year = 2023, driverId = "alonso")
            }
            assertFailsWith<IllegalArgumentException> {
                raceQueryService.races()
            }
        }
    }

    @Test
    fun `races should return races for a specific year`() {
        runBlocking {
            coEvery { jolpicaClient.getRaces(2023, null) } returns listOf(sampleJolpicaRace)

            val result = raceQueryService.races(year = 2023)

            assertEquals(1, result.size)
            assertEquals(2023, result[0].season)
        }
    }

    @Test
    fun `races should return races by driverId`() {
        runBlocking {
            coEvery { jolpicaClient.getRacesByDriver("verstappen") } returns listOf(sampleJolpicaRace)

            val result = raceQueryService.races(driverId = "verstappen")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getRacesByDriver("verstappen") }
        }
    }

    @Test
    fun `races should return races by constructorId`() {
        runBlocking {
            coEvery { jolpicaClient.getRacesByConstructor("red_bull") } returns listOf(sampleJolpicaRace)

            val result = raceQueryService.races(constructorId = "red_bull")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getRacesByConstructor("red_bull") }
        }
    }

    @Test
    fun `races should return races by circuitId`() {
        runBlocking {
            coEvery { jolpicaClient.getRacesByCircuit("monaco") } returns listOf(sampleJolpicaRace)

            val result = raceQueryService.races(circuitId = "monaco")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getRacesByCircuit("monaco") }
        }
    }

    @Test
    fun `races should use cache`() {
        runBlocking {
            coEvery { jolpicaClient.getRaces(2023, null) } returns listOf(sampleJolpicaRace)

            raceQueryService.races(year = 2023)
            raceQueryService.races(year = 2023)

            coVerify(exactly = 1) { jolpicaClient.getRaces(2023, null) }
        }
    }

    @Test
    fun `mostRecentRace should return race`() {
        runBlocking {
            coEvery { jolpicaClient.getMostRecentRace() } returns sampleJolpicaRace

            val result = raceQueryService.mostRecentRace()

            assertEquals(2023, result.season)
            assertEquals(1, result.round)
        }
    }

    @Test
    fun `mostRecentRace should throw NotFoundException when null returned`() {
        runBlocking {
            coEvery { jolpicaClient.getMostRecentRace() } returns null

            assertFailsWith<NotFoundException> {
                raceQueryService.mostRecentRace()
            }
        }
    }
}
