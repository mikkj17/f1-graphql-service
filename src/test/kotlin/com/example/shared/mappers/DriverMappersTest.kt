package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver

class DriverMappersTest {

    @Test
    fun `toDriver should map JolpicaDriver to internal Driver`() {
        val jolpicaDriver = JolpicaDriver(
            id = "alonso",
            number = "14",
            code = "ALO",
            url = "https://en.wikipedia.org/wiki/Fernando_Alonso",
            givenName = "Fernando",
            familyName = "Alonso",
            dateOfBirth = "1981-07-29",
            nationality = "Spanish"
        )

        val result = jolpicaDriver.toDriver()

        assertEquals(jolpicaDriver.id, result.id)
        assertEquals(14, result.number)
        assertEquals(jolpicaDriver.code, result.code)
        assertEquals(jolpicaDriver.url, result.url)
        assertEquals(jolpicaDriver.givenName, result.givenName)
        assertEquals(jolpicaDriver.familyName, result.familyName)
        assertEquals(jolpicaDriver.dateOfBirth, result.dateOfBirth)
        assertEquals(jolpicaDriver.nationality, result.nationality)
    }

    @Test
    fun `toDriver should handle null number`() {
        val jolpicaDriver = JolpicaDriver(
            id = "alonso",
            number = null,
            code = "ALO",
            url = "https://en.wikipedia.org/wiki/Fernando_Alonso",
            givenName = "Fernando",
            familyName = "Alonso",
            dateOfBirth = "1981-07-29",
            nationality = "Spanish"
        )

        val result = jolpicaDriver.toDriver()

        assertEquals(null, result.number)
    }

    @Test
    fun `toDriver should handle number which cannot be parsed to Int`() {
        val jolpicaDriver = JolpicaDriver(
            id = "alonso",
            number = "abc",
            code = "ALO",
            url = "https://en.wikipedia.org/wiki/Fernando_Alonso",
            givenName = "Fernando",
            familyName = "Alonso",
            dateOfBirth = "1981-07-29",
            nationality = "Spanish"
        )

        val result = jolpicaDriver.toDriver()

        assertEquals(null, result.number)
    }
}
