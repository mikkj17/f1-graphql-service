package com.example.server.schema.queryservices

import com.example.client.espn.EspnClient
import com.example.client.espn.schema.models.EspnArticle
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class NewsQueryServiceTest {

    private val espnClient = mockk<EspnClient>()
    private val newsQueryService = NewsQueryService(espnClient)

    private val sampleEspnArticle = EspnArticle(
        id = 12345L,
        headline = "F1 News Headline",
        description = "Description of the news",
        lastModified = "2023-10-27T10:00:00Z",
        published = "2023-10-27T10:00:00Z",
        images = emptyList(),
        links = null
    )

    @Test
    fun `news should return news from espn client`() {
        runBlocking {
            coEvery { espnClient.getNews() } returns listOf(sampleEspnArticle)

            val result = newsQueryService.news()

            assertEquals(1, result.size)
            assertEquals(12345L, result[0].id)
            assertEquals("F1 News Headline", result[0].headline)
        }
    }
}
