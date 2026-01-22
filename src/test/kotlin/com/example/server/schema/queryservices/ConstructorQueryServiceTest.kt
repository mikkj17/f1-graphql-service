package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import com.example.client.jolpica.schema.models.constructor.Constructor as JolpicaConstructor

class ConstructorQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val constructorQueryService = ConstructorQueryService(jolpicaClient)

    private val sampleJolpicaConstructor = JolpicaConstructor(
        id = "mclaren",
        url = "https://test.com/mclaren",
        name = "McLaren",
        nationality = "British"
    )

    @Test
    fun `constructors should return list of constructors for year and round`() {
        runBlocking {
            coEvery { jolpicaClient.getConstructors(2023, 1) } returns listOf(sampleJolpicaConstructor)

            val result = constructorQueryService.constructors(year = 2023, round = 1)

            assertEquals(1, result.size)
            assertEquals("mclaren", result[0].id)
            assertEquals("McLaren", result[0].name)
        }
    }

    @Test
    fun `constructors should return constructors by driverId`() {
        runBlocking {
            coEvery { jolpicaClient.getConstructorsByDriver("norris") } returns listOf(sampleJolpicaConstructor)

            val result = constructorQueryService.constructors(driverId = "norris")

            assertEquals(1, result.size)
            assertEquals("mclaren", result[0].id)
        }
    }

    @Test
    fun `constructors should throw error if driverId is used with year`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                constructorQueryService.constructors(year = 2023, driverId = "norris")
            }
        }
    }

    @Test
    fun `constructors should throw error if round is used without year`() {
        runBlocking {
            assertFailsWith<IllegalArgumentException> {
                constructorQueryService.constructors(round = 1)
            }
        }
    }
}
