package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.constructor.Constructor as JolpicaConstructor

class ConstructorMappersTest {

    @Test
    fun `toConstructor should map JolpicaConstructor to internal Constructor`() {
        val jolpicaConstructor = JolpicaConstructor(
            id = "red_bull",
            url = "https://test.com/redbull",
            name = "Red Bull",
            nationality = "Austrian"
        )

        val result = jolpicaConstructor.toConstructor()

        assertEquals("red_bull", result.id)
        assertEquals("https://test.com/redbull", result.url)
        assertEquals("Red Bull", result.name)
        assertEquals("Austrian", result.nationality)
    }

    @Test
    fun `toConstructor should handle null url`() {
        val jolpicaConstructor = JolpicaConstructor(
            id = "red_bull",
            url = null,
            name = "Red Bull",
            nationality = "Austrian"
        )

        val result = jolpicaConstructor.toConstructor()

        assertEquals(null, result.url)
    }
}
