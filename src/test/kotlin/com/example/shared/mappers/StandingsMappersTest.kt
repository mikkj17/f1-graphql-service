package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.constructor.Constructor as JolpicaConstructor
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver
import com.example.client.jolpica.schema.models.standings.ConstructorStanding as JolpicaConstructorStanding
import com.example.client.jolpica.schema.models.standings.ConstructorStandingList as JolpicaConstructorStandingList
import com.example.client.jolpica.schema.models.standings.DriverStanding as JolpicaDriverStanding
import com.example.client.jolpica.schema.models.standings.DriverStandingList as JolpicaDriverStandingList

class StandingsMappersTest {

    private val sampleJolpicaDriver = JolpicaDriver(
        id = "max_verstappen",
        givenName = "Max",
        familyName = "Verstappen"
    )

    private val sampleJolpicaConstructor = JolpicaConstructor(
        id = "red_bull",
        name = "Red Bull",
        nationality = "Austrian"
    )

    @Test
    fun `toDriverStandingList should map JolpicaDriverStandingList to internal DriverStandingList`() {
        val jolpicaStandings = JolpicaDriverStandingList(
            season = 2023,
            round = 22,
            standings = listOf(
                JolpicaDriverStanding(
                    position = 1,
                    positionText = "1",
                    points = 575f,
                    wins = 19,
                    driver = sampleJolpicaDriver,
                    constructors = listOf(sampleJolpicaConstructor)
                )
            )
        )

        val result = jolpicaStandings.toDriverStandingList()

        assertEquals(2023, result.season)
        assertEquals(22, result.round)
        assertEquals(1, result.standings.size)
        val standing = result.standings[0]
        assertEquals(1, standing.position)
        assertEquals("1", standing.positionText)
        assertEquals(575f, standing.points)
        assertEquals(19, standing.wins)
        assertEquals("max_verstappen", standing.driver.id)
        assertEquals(1, standing.constructors.size)
        assertEquals("red_bull", standing.constructors[0].id)
    }

    @Test
    fun `toConstructorStandingList should map JolpicaConstructorStandingList to internal ConstructorStandingList`() {
        val jolpicaStandings = JolpicaConstructorStandingList(
            season = 2023,
            round = 22,
            standings = listOf(
                JolpicaConstructorStanding(
                    position = 1,
                    positionText = "1",
                    points = 860f,
                    wins = 21,
                    constructor = sampleJolpicaConstructor
                )
            )
        )

        val result = jolpicaStandings.toConstructorStandingList()

        assertEquals(2023, result.season)
        assertEquals(22, result.round)
        assertEquals(1, result.standings.size)
        val standing = result.standings[0]
        assertEquals(1, standing.position)
        assertEquals("1", standing.positionText)
        assertEquals(860f, standing.points)
        assertEquals(21, standing.wins)
        assertEquals("red_bull", standing.constructor.id)
    }
}
