package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.result.Qualifying as JolpicaQualifying

class QualifyingQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val qualifyingQueryService = QualifyingQueryService(jolpicaClient)

    private val sampleJolpicaQualifying = JolpicaQualifying(
        season = 2023,
        round = 1,
        url = "https://test.com/qualifying",
        name = "Bahrain Grand Prix",
        circuit = JolpicaCircuit(
            id = "sakhir",
            url = "https://test.com/sakhir",
            name = "Bahrain International Circuit",
            location = JolpicaLocation(
                latitude = 26.0325f,
                longitude = 50.5106f,
                locality = "Sakhir",
                country = "Bahrain"
            )
        ),
        date = "2023-03-04",
        results = emptyList()
    )

    @Test
    fun `qualifying should return qualifying for year and round`() {
        runBlocking {
            coEvery { jolpicaClient.getQualifyings(2023, 1) } returns listOf(sampleJolpicaQualifying)

            val result = qualifyingQueryService.qualifying(2023, 1)

            assertEquals(2023, result.season)
            assertEquals(1, result.round)
            assertEquals("Bahrain Grand Prix", result.name)
        }
    }

    @Test
    fun `qualifyings should return list of qualifyings for year`() {
        runBlocking {
            coEvery { jolpicaClient.getQualifyings(2023, null) } returns listOf(sampleJolpicaQualifying)

            val result = qualifyingQueryService.qualifyings(year = 2023)

            assertEquals(1, result.size)
            assertEquals(2023, result[0].season)
            coVerify(exactly = 1) { jolpicaClient.getQualifyings(2023, null) }
        }
    }

    @Test
    fun `qualifyings should return qualifyings by driverId`() {
        runBlocking {
            coEvery { jolpicaClient.getQualifyingsByDriver("max_verstappen") } returns listOf(sampleJolpicaQualifying)

            val result = qualifyingQueryService.qualifyings(driverId = "max_verstappen")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getQualifyingsByDriver("max_verstappen") }
        }
    }

    @Test
    fun `qualifyings should return qualifyings by constructorId`() {
        runBlocking {
            coEvery { jolpicaClient.getQualifyingsByConstructor("red_bull") } returns listOf(sampleJolpicaQualifying)

            val result = qualifyingQueryService.qualifyings(constructorId = "red_bull")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getQualifyingsByConstructor("red_bull") }
        }
    }

    @Test
    fun `qualifyings should return qualifyings by circuitId`() {
        runBlocking {
            coEvery { jolpicaClient.getQualifyingsByCircuit("sakhir") } returns listOf(sampleJolpicaQualifying)

            val result = qualifyingQueryService.qualifyings(circuitId = "sakhir")

            assertEquals(1, result.size)
            coVerify(exactly = 1) { jolpicaClient.getQualifyingsByCircuit("sakhir") }
        }
    }

    @Test
    fun `qualifyings should use cache`() {
        runBlocking {
            coEvery { jolpicaClient.getQualifyings(2023, null) } returns listOf(sampleJolpicaQualifying)

            qualifyingQueryService.qualifyings(year = 2023)
            qualifyingQueryService.qualifyings(year = 2023)

            coVerify(exactly = 1) { jolpicaClient.getQualifyings(2023, null) }
        }
    }

    @Test
    fun `qualifyings should throw error if multiple parameters provided`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                qualifyingQueryService.qualifyings(year = 2023, driverId = "leclerc")
            }
        }
    }

    @Test
    fun `qualifyings should throw error if no parameters provided`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                qualifyingQueryService.qualifyings()
            }
        }
    }
}
