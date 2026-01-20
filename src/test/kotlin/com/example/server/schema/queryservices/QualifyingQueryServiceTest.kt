package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class QualifyingQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val qualifyingQueryService = QualifyingQueryService(jolpicaClient)

    @Test
    fun `qualifyings should return list of qualifyings for year`() {
        runBlocking {
            coEvery { jolpicaClient.getQualifyings(2023, null) } returns emptyList()

            val result = qualifyingQueryService.qualifyings(year = 2023)

            assertEquals(0, result.size)
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
}
