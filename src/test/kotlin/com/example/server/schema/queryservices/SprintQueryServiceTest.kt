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
import com.example.client.jolpica.schema.models.result.Sprint as JolpicaSprint

class SprintQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val sprintQueryService = SprintQueryService(jolpicaClient)

    private val sampleJolpicaSprint = JolpicaSprint(
        season = 2023,
        round = 4,
        url = "https://test.com/sprint",
        name = "Azerbaijan Grand Prix",
        circuit = JolpicaCircuit(
            id = "bakur",
            url = "https://test.com/bakur",
            name = "Baku City Circuit",
            location = JolpicaLocation(
                latitude = 40.3725f,
                longitude = 49.8533f,
                locality = "Baku",
                country = "Azerbaijan"
            )
        ),
        date = "2023-04-29",
        results = emptyList()
    )

    @Test
    fun `sprint should return sprint for year and round`() {
        runBlocking {
            coEvery { jolpicaClient.getSprints(2023, 4) } returns listOf(sampleJolpicaSprint)

            val result = sprintQueryService.sprint(2023, 4)

            assertEquals(2023, result.season)
            assertEquals(4, result.round)
        }
    }

    @Test
    fun `sprint should throw NotFoundException when no sprint found`() {
        runBlocking {
            coEvery { jolpicaClient.getSprints(2023, 4) } returns emptyList()

            assertFailsWith<NotFoundException> {
                sprintQueryService.sprint(2023, 4)
            }
        }
    }

    @Test
    fun `sprints should return list of sprints for year`() {
        runBlocking {
            coEvery { jolpicaClient.getSprints(2023, null) } returns listOf(sampleJolpicaSprint)

            val result = sprintQueryService.sprints(2023)

            assertEquals(1, result.size)
            assertEquals(2023, result[0].season)
        }
    }

    @Test
    fun `sprint should use cache`() {
        runBlocking {
            coEvery { jolpicaClient.getSprints(2023, 4) } returns listOf(sampleJolpicaSprint)

            sprintQueryService.sprint(2023, 4)
            sprintQueryService.sprint(2023, 4)

            coVerify(exactly = 1) { jolpicaClient.getSprints(2023, 4) }
        }
    }

    @Test
    fun `sprints should use cache`() {
        runBlocking {
            coEvery { jolpicaClient.getSprints(2023, null) } returns listOf(sampleJolpicaSprint)

            sprintQueryService.sprints(2023)
            sprintQueryService.sprints(2023)

            coVerify(exactly = 1) { jolpicaClient.getSprints(2023, null) }
        }
    }
}
