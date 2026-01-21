package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.constructor.Constructor as JolpicaConstructor
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver
import com.example.client.jolpica.schema.models.result.Result as JolpicaResult
import com.example.client.jolpica.schema.models.result.Sprint as JolpicaSprint

class SprintMappersTest {

    private val sampleJolpicaCircuit = JolpicaCircuit(
        id = "interlagos",
        url = "url",
        name = "Interlagos",
        location = JolpicaLocation(locality = "Sao Paulo", country = "Brazil")
    )

    private val sampleJolpicaDriver = JolpicaDriver(id = "verstappen", givenName = "Max", familyName = "Verstappen")
    private val sampleJolpicaConstructor =
        JolpicaConstructor(id = "red_bull", name = "Red Bull", nationality = "Austrian")

    @Test
    fun `toSprint should map JolpicaSprint to server Sprint`() {
        val jolpicaSprint = JolpicaSprint(
            season = 2023,
            round = 21,
            url = "url",
            name = "Brazilian Grand Prix",
            circuit = sampleJolpicaCircuit,
            date = "2023-11-04",
            time = "15:30:00Z",
            results = listOf(
                JolpicaResult(
                    number = "1",
                    position = 1,
                    positionText = "1",
                    points = 8f,
                    driver = sampleJolpicaDriver,
                    constructor = sampleJolpicaConstructor,
                    grid = 1,
                    laps = 24,
                    status = "Finished"
                )
            )
        )

        val result = jolpicaSprint.toSprint()

        assertEquals(2023, result.season)
        assertEquals(21, result.round)
        assertEquals("Brazilian Grand Prix", result.name)
        assertEquals(1, result.results.size)
        assertEquals("verstappen", result.results[0].driver.id)
    }

    @Test
    fun `toSprints should group and map multiple JolpicaSprint`() {
        val s1Part1 = JolpicaSprint(
            season = 2023,
            round = 4,
            url = "url1",
            name = "Sprint 1",
            circuit = sampleJolpicaCircuit,
            date = "date",
            results = listOf(
                JolpicaResult(
                    number = "1",
                    position = 1,
                    positionText = "1",
                    points = 8f,
                    driver = sampleJolpicaDriver,
                    constructor = sampleJolpicaConstructor,
                    grid = 1,
                    laps = 17,
                    status = "Finished"
                )
            )
        )
        val s1Part2 = JolpicaSprint(
            season = 2023,
            round = 4,
            url = "url1",
            name = "Sprint 1",
            circuit = sampleJolpicaCircuit,
            date = "date",
            results = listOf(
                JolpicaResult(
                    number = "11",
                    position = 2,
                    positionText = "2",
                    points = 7f,
                    driver = sampleJolpicaDriver.copy(id = "perez"),
                    constructor = sampleJolpicaConstructor,
                    grid = 2,
                    laps = 17,
                    status = "Finished"
                )
            )
        )
        val s2 = JolpicaSprint(
            season = 2023,
            round = 9,
            url = "url2",
            name = "Sprint 2",
            circuit = sampleJolpicaCircuit,
            date = "date2",
            results = listOf(
                JolpicaResult(
                    number = "1",
                    position = 1,
                    positionText = "1",
                    points = 8f,
                    driver = sampleJolpicaDriver,
                    constructor = sampleJolpicaConstructor,
                    grid = 1,
                    laps = 24,
                    status = "Finished"
                )
            )
        )

        val result = listOf(s1Part1, s1Part2, s2).toSprints()

        assertEquals(2, result.size)
        val firstSprint = result.find { it.round == 4 }!!
        val secondSprint = result.find { it.round == 9 }!!

        assertEquals(2, firstSprint.results.size)
        assertEquals("verstappen", firstSprint.results[0].driver.id)
        assertEquals("perez", firstSprint.results[1].driver.id)

        assertEquals(1, secondSprint.results.size)
    }
}
