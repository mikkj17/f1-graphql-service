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

class CircuitQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val circuitQueryService = CircuitQueryService(jolpicaClient)

    private val sampleJolpicaCircuit = JolpicaCircuit(
        id = "monaco",
        url = "https://test.com/monaco",
        name = "Circuit de Monaco",
        location = JolpicaLocation(
            latitude = 43.7347f,
            longitude = 7.42056f,
            locality = "Monte-Carlo",
            country = "Monaco"
        )
    )

    @Test
    fun `circuits should return circuits for a specific year and round`() {
        runBlocking {
            coEvery { jolpicaClient.getCircuits(2023, 6) } returns listOf(sampleJolpicaCircuit)

            val result = circuitQueryService.circuits(2023, 6)

            assertEquals(1, result.size)
            assertEquals("monaco", result[0].id)
            assertEquals("Circuit de Monaco", result[0].name)
        }
    }

    @Test
    fun `circuits should return all circuits when no year and round provided`() {
        runBlocking {
            coEvery { jolpicaClient.getCircuits(null, null) } returns listOf(sampleJolpicaCircuit)

            val result = circuitQueryService.circuits()

            assertEquals(1, result.size)
            assertEquals("monaco", result[0].id)
        }
    }

    @Test
    fun `circuits should use cache`() {
        runBlocking {
            coEvery { jolpicaClient.getCircuits(2023, 6) } returns listOf(sampleJolpicaCircuit)

            circuitQueryService.circuits(2023, 6)
            circuitQueryService.circuits(2023, 6)

            coVerify(exactly = 1) { jolpicaClient.getCircuits(2023, 6) }
        }
    }
}
