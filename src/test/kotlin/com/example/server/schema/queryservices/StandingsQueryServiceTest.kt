package com.example.server.schema.queryservices

import com.example.client.jolpica.IJolpicaClient
import io.ktor.server.plugins.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import com.example.client.jolpica.schema.models.standings.ConstructorStandingList as JolpicaConstructorStandingList
import com.example.client.jolpica.schema.models.standings.DriverStandingList as JolpicaDriverStandingList

class StandingsQueryServiceTest {

    private val jolpicaClient = mockk<IJolpicaClient>()
    private val standingsQueryService = StandingsQueryService(jolpicaClient)

    @Test
    fun `driverStandings should return driver standings`() {
        runBlocking {
            val mockStandings = JolpicaDriverStandingList(season = 2023, round = 1, standings = emptyList())
            coEvery { jolpicaClient.getDriverStandings(2023, 1) } returns mockStandings

            val result = standingsQueryService.driverStandings(2023, 1)

            assertEquals(2023, result.season)
        }
    }

    @Test
    fun `driverStandings should throw NotFoundException when null returned`() {
        runBlocking {
            coEvery { jolpicaClient.getDriverStandings(2023, 1) } returns null

            assertFailsWith<NotFoundException> {
                standingsQueryService.driverStandings(2023, 1)
            }
        }
    }

    @Test
    fun `constructorStandings should return constructor standings`() {
        runBlocking {
            val mockStandings = JolpicaConstructorStandingList(season = 2023, round = 1, standings = emptyList())
            coEvery { jolpicaClient.getConstructorStandings(2023, 1) } returns mockStandings

            val result = standingsQueryService.constructorStandings(2023, 1)

            assertEquals(2023, result.season)
        }
    }

    @Test
    fun `constructorStandings should throw NotFoundException when null returned`() {
        runBlocking {
            coEvery { jolpicaClient.getConstructorStandings(2023, 1) } returns null

            assertFailsWith<NotFoundException> {
                standingsQueryService.constructorStandings(2023, 1)
            }
        }
    }
}
