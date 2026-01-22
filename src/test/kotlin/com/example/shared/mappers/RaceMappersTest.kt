package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.constructor.Constructor as JolpicaConstructor
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver
import com.example.client.jolpica.schema.models.result.AverageSpeed as JolpicaAverageSpeed
import com.example.client.jolpica.schema.models.result.FastestLap as JolpicaFastestLap
import com.example.client.jolpica.schema.models.result.Race as JolpicaRace
import com.example.client.jolpica.schema.models.result.Result as JolpicaRaceResult
import com.example.client.jolpica.schema.models.result.SimpleTime as JolpicaSimpleTime
import com.example.client.jolpica.schema.models.result.Time as JolpicaTime

class RaceMappersTest {

    private val sampleJolpicaCircuit = JolpicaCircuit(
        id = "monaco",
        url = "https://test.com/monaco",
        name = "Circuit de Monaco",
        location = JolpicaLocation(
            latitude = 43.7347f,
            longitude = 7.42056f,
            locality = "Monte-Carlo",
            country = "Monaco"
        )
    )

    private val sampleJolpicaDriver = JolpicaDriver(
        id = "verstappen",
        number = "33",
        code = "VER",
        url = "https://test.com/verstappen",
        givenName = "Max",
        familyName = "Verstappen",
        dateOfBirth = "1997-09-30",
        nationality = "Dutch"
    )

    private val sampleJolpicaConstructor = JolpicaConstructor(
        id = "red_bull",
        url = "https://test.com/red_bull",
        name = "Red Bull",
        nationality = "Austrian"
    )

    private val sampleJolpicaResult = JolpicaRaceResult(
        number = "33",
        position = 1,
        positionText = "1",
        points = 25f,
        driver = sampleJolpicaDriver,
        constructor = sampleJolpicaConstructor,
        grid = 1,
        laps = 78,
        status = "Finished",
        time = JolpicaTime(milliseconds = 5400000, time = "1:30:00.000"),
        fastestLap = JolpicaFastestLap(
            rank = 1,
            lap = 70,
            time = JolpicaSimpleTime(time = "1:15.000"),
            averageSpeed = JolpicaAverageSpeed(unit = "kph", speed = 160.0f)
        )
    )

    private val sampleJolpicaRace = JolpicaRace(
        season = 2023,
        round = 6,
        url = "https://test.com/race",
        name = "Monaco Grand Prix",
        circuit = sampleJolpicaCircuit,
        date = "2023-05-28",
        time = "15:00:00Z",
        results = listOf(sampleJolpicaResult)
    )

    @Test
    fun `toRace should map JolpicaRace to internal Race`() {
        val result = sampleJolpicaRace.toRace()

        assertEquals(sampleJolpicaRace.season, result.season)
        assertEquals(sampleJolpicaRace.round, result.round)
        assertEquals(sampleJolpicaRace.url, result.url)
        assertEquals(sampleJolpicaRace.name, result.name)
        assertEquals(sampleJolpicaRace.circuit.id, result.circuit.id)
        assertEquals(sampleJolpicaRace.date, result.date)
        assertEquals(sampleJolpicaRace.time, result.time)
        assertEquals(1, result.results.size)

        val raceResult = result.results[0]
        assertEquals(33, raceResult.number)
        assertEquals(1, raceResult.position)
        assertEquals("verstappen", raceResult.driver.id)
        assertEquals("red_bull", raceResult.constructor.id)
        assertNotNull(raceResult.time)
        assertEquals("1:30:00.000", raceResult.time.time)
        assertNotNull(raceResult.fastestLap)
        assertEquals("1:15.000", raceResult.fastestLap.time.time)
    }

    @Test
    fun `toRaces should group JolpicaRaces and map them`() {
        val race2 = sampleJolpicaRace.copy(
            results = listOf(sampleJolpicaResult.copy(position = 2, positionText = "2", points = 18f))
        )

        val jolpicaRaces = listOf(sampleJolpicaRace, race2)

        val result = jolpicaRaces.toRaces()

        assertEquals(1, result.size)
        assertEquals(2, result[0].results.size)
        assertEquals(1, result[0].results[0].position)
        assertEquals(2, result[0].results[1].position)
    }

    @Test
    fun `toResult should handle null values in JolpicaRaceResult`() {
        val minimalJolpicaResult = JolpicaRaceResult(
            number = null,
            position = 20,
            positionText = "R",
            points = 0f,
            driver = sampleJolpicaDriver,
            constructor = sampleJolpicaConstructor,
            grid = 20,
            laps = 5,
            status = "Engine",
            time = null,
            fastestLap = null
        )

        val result = minimalJolpicaResult.toResult()

        assertEquals(null, result.number)
        assertEquals(null, result.time)
        assertEquals(null, result.fastestLap)
        assertEquals("Engine", result.status)
    }

    @Test
    fun `toAverageSpeed should map JolpicaAverageSpeed correctly`() {
        val jolpicaAverageSpeed = JolpicaAverageSpeed(unit = "mph", speed = 100.5f)
        val result = jolpicaAverageSpeed.toAverageSpeed()

        assertEquals("mph", result.unit)
        assertEquals(100.5f, result.speed)
    }

    @Test
    fun `toTime should map JolpicaTime correctly`() {
        val jolpicaTime = JolpicaTime(milliseconds = 12345, time = "12.345")
        val result = jolpicaTime.toTime()

        assertEquals(12345, result.milliseconds)
        assertEquals("12.345", result.time)
    }
}
