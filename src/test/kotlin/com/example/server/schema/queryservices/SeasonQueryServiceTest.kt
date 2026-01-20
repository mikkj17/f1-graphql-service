package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.season.Season as JolpicaSeason

class SeasonQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val seasonQueryService = SeasonQueryService(jolpicaClient)

    @Test
    fun `seasons should return list of seasons and use cache`() {
        runBlocking {
            val mockSeasons = listOf(
                JolpicaSeason(year = 2023, url = "url1"),
                JolpicaSeason(year = 2024, url = "url2")
            )
            coEvery { jolpicaClient.getSeasons() } returns mockSeasons

            // First call
            val result1 = seasonQueryService.seasons()
            assertEquals(2, result1.size)
            assertEquals(2023, result1[0].year)

            // Second call - should use cache
            val result2 = seasonQueryService.seasons()
            assertEquals(2, result2.size)

            coVerify(exactly = 1) { jolpicaClient.getSeasons() }
        }
    }
}
