package com.example.shared.mappers

import com.example.client.espn.schema.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NewsMappersTest {

    @Test
    fun `toNewsArticle should map EspnArticle to NewsArticle`() {
        val espnArticle = EspnArticle(
            id = 123,
            headline = "Headline",
            description = "Description",
            lastModified = "2023-10-27T10:00:00Z",
            published = "2023-10-27T10:00:00Z",
            images = listOf(
                EspnImage(
                    id = 1,
                    name = "image1",
                    url = "url1",
                    caption = "caption1",
                    credit = "credit1",
                    alt = "alt1",
                    width = 100,
                    height = 200
                ),
                EspnImage(id = null, name = "image2", url = "url2", caption = "caption2", width = 0, height = 0)
            ),
            links = EspnLinks(
                web = EspnWebLink(href = "web-url"),
                mobile = EspnMobileLink(href = "mobile-url")
            ),
            byline = "Author Name"
        )

        val newsArticle = espnArticle.toNewsArticle()

        assertEquals(123, newsArticle.id)
        assertEquals("Headline", newsArticle.headline)
        assertEquals("Description", newsArticle.description)
        assertEquals(2, newsArticle.images.size)
        assertEquals(1, newsArticle.images[0].id)
        assertEquals("url1", newsArticle.images[0].url)
        assertEquals("alt1", newsArticle.images[0].alt)
        assertEquals(100, newsArticle.images[0].width)
        assertEquals(200, newsArticle.images[0].height)
        assertEquals(null, newsArticle.images[1].id)
        assertEquals("url2", newsArticle.images[1].url)
        assertNotNull(newsArticle.links)
        assertEquals("web-url", newsArticle.links.web)
        assertEquals("mobile-url", newsArticle.links.mobile)
        assertEquals("Author Name", newsArticle.byline)
    }
}
