package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.season.Season as JolpicaSeason

class SeasonMappersTest {

    @Test
    fun `toSeason should map JolpicaSeason to server Season`() {
        val jolpicaSeason = JolpicaSeason(
            year = 2024,
            url = "https://test.com/2024"
        )

        val result = jolpicaSeason.toSeason()

        assertEquals(2024, result.year)
        assertEquals("https://test.com/2024", result.url)
    }
}
