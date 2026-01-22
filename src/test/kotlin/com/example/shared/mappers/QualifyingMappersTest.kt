package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.constructor.Constructor as JolpicaConstructor
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver
import com.example.client.jolpica.schema.models.result.Qualifying as JolpicaQualifying
import com.example.client.jolpica.schema.models.result.QualifyingResult as JolpicaQualifyingResult

class QualifyingMappersTest {

    private val sampleJolpicaCircuit = JolpicaCircuit(
        id = "monaco",
        url = "url",
        name = "Monaco",
        location = JolpicaLocation(locality = "Monte-Carlo", country = "Monaco")
    )

    private val sampleJolpicaDriver = JolpicaDriver(id = "leclerc", givenName = "Charles", familyName = "Leclerc")
    private val sampleJolpicaConstructor = JolpicaConstructor(id = "ferrari", name = "Ferrari", nationality = "Italian")

    @Test
    fun `toQualifying should map JolpicaQualifying to internal Qualifying`() {
        val jolpicaQualifying = JolpicaQualifying(
            season = 2023,
            round = 6,
            url = "url",
            name = "Monaco Grand Prix",
            circuit = sampleJolpicaCircuit,
            date = "2023-05-27",
            time = "14:00:00Z",
            results = listOf(
                JolpicaQualifyingResult(
                    number = 16,
                    position = 3,
                    driver = sampleJolpicaDriver,
                    constructor = sampleJolpicaConstructor,
                    q1 = "1:12.000",
                    q2 = "1:11.500",
                    q3 = "1:11.400"
                )
            )
        )

        val result = jolpicaQualifying.toQualifying()

        assertEquals(2023, result.season)
        assertEquals(6, result.round)
        assertEquals(1, result.results.size)
        assertEquals("leclerc", result.results[0].driver.id)
        assertEquals("1:11.400", result.results[0].q3)
    }

    @Test
    fun `toQualifyings should group and map multiple JolpicaQualifying`() {
        val q1Part1 = JolpicaQualifying(
            season = 2023,
            round = 1,
            url = "url1",
            name = "Race 1",
            circuit = sampleJolpicaCircuit,
            date = "date",
            results = listOf(
                JolpicaQualifyingResult(
                    number = 1,
                    position = 1,
                    driver = sampleJolpicaDriver,
                    constructor = sampleJolpicaConstructor,
                    q1 = "time1"
                )
            )
        )
        val q1Part2 = JolpicaQualifying(
            season = 2023,
            round = 1,
            url = "url1",
            name = "Race 1",
            circuit = sampleJolpicaCircuit,
            date = "date",
            results = listOf(
                JolpicaQualifyingResult(
                    number = 2,
                    position = 2,
                    driver = sampleJolpicaDriver.copy(id = "perez", familyName = "Perez"),
                    constructor = sampleJolpicaConstructor,
                    q1 = "time2"
                )
            )
        )
        val q2 = JolpicaQualifying(
            season = 2023,
            round = 2,
            url = "url2",
            name = "Race 2",
            circuit = sampleJolpicaCircuit,
            date = "date2",
            results = listOf(
                JolpicaQualifyingResult(
                    number = 1,
                    position = 1,
                    driver = sampleJolpicaDriver,
                    constructor = sampleJolpicaConstructor,
                    q1 = "time3"
                )
            )
        )

        val result = listOf(q1Part1, q1Part2, q2).toQualifyings()

        assertEquals(2, result.size)
        val firstQualifying = result.find { it.round == 1 }!!
        val secondQualifying = result.find { it.round == 2 }!!

        assertEquals(2023, firstQualifying.season)
        assertEquals(2, firstQualifying.results.size)
        assertEquals("leclerc", firstQualifying.results[0].driver.id)
        assertEquals("perez", firstQualifying.results[1].driver.id)

        assertEquals(2023, secondQualifying.season)
        assertEquals(1, secondQualifying.results.size)
        assertEquals("leclerc", secondQualifying.results[0].driver.id)
    }
}
