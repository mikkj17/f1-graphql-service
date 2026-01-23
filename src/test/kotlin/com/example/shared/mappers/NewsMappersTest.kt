package com.example.shared.mappers

import com.example.client.espn.schema.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NewsMappersTest {

    @Test
    fun `toNewsArticle should map EspnArticle to NewsArticle`() {
        val espnArticle = EspnArticle(
            id = 123L,
            headline = "Headline",
            description = "Description",
            lastModified = "2023-10-27T10:00:00Z",
            published = "2023-10-27T10:00:00Z",
            images = listOf(
                EspnImage(id = 1L, url = "url1", caption = "caption1", credit = "credit1")
            ),
            links = EspnLinks(
                web = EspnWebLink(href = "web-url"),
                mobile = EspnMobileLink(href = "mobile-url")
            )
        )

        val newsArticle = espnArticle.toNewsArticle()

        assertEquals(123L, newsArticle.id)
        assertEquals("Headline", newsArticle.headline)
        assertEquals("Description", newsArticle.description)
        assertEquals(1, newsArticle.images.size)
        assertEquals("url1", newsArticle.images[0].url)
        assertNotNull(newsArticle.links)
        assertEquals("web-url", newsArticle.links?.web)
        assertEquals("mobile-url", newsArticle.links?.mobile)
    }
}
