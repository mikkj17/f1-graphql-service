package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.ktor.server.plugins.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver

class DriverQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val driverQueryService = DriverQueryService(jolpicaClient)

    private val sampleJolpicaDriver = JolpicaDriver(
        id = "alonso",
        number = "14",
        code = "ALO",
        url = "https://test.com/alonso",
        givenName = "Fernando",
        familyName = "Alonso",
        dateOfBirth = "1981-07-29",
        nationality = "Spanish"
    )

    @Test
    fun `drivers should return list of drivers`() {
        runBlocking {
            coEvery { jolpicaClient.getDrivers(2023, null) } returns listOf(sampleJolpicaDriver)

            val result = driverQueryService.drivers(year = 2023)

            assertEquals(1, result.size)
            assertEquals("alonso", result[0].id)
            assertEquals("Fernando", result[0].givenName)
        }
    }

    @Test
    fun `drivers should return drivers by constructor`() {
        runBlocking {
            coEvery { jolpicaClient.getDriversByConstructor("aston_martin") } returns listOf(sampleJolpicaDriver)

            val result = driverQueryService.drivers(constructorId = "aston_martin")

            assertEquals(1, result.size)
            assertEquals("alonso", result[0].id)
        }
    }

    @Test
    fun `drivers should throw error if constructorId is used with year`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                driverQueryService.drivers(year = 2023, constructorId = "aston_martin")
            }
        }
    }

    @Test
    fun `driver should return a single driver`() {
        runBlocking {
            coEvery { jolpicaClient.getDriver("alonso") } returns sampleJolpicaDriver

            val result = driverQueryService.driver("alonso")

            assertEquals("alonso", result.id)
            assertEquals("Alonso", result.familyName)
        }
    }

    @Test
    fun `driver should throw NotFoundException when driver not found`() {
        runBlocking {
            coEvery { jolpicaClient.getDriver("unknown") } returns null

            assertFailsWith<NotFoundException> {
                driverQueryService.driver("unknown")
            }
        }
    }
}
