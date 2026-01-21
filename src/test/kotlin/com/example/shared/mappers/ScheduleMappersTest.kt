package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.schedule.Schedule as JolpicaSchedule
import com.example.client.jolpica.schema.models.schedule.Session as JolpicaSession

class ScheduleMappersTest {

    private val sampleJolpicaCircuit = JolpicaCircuit(
        id = "silverstone",
        url = "url",
        name = "Silverstone",
        location = JolpicaLocation(locality = "Silverstone", country = "UK")
    )

    @Test
    fun `toSchedule should map JolpicaSchedule to server Schedule`() {
        val jolpicaSchedule = JolpicaSchedule(
            season = 2023,
            round = 10,
            url = "url",
            raceName = "British Grand Prix",
            circuit = sampleJolpicaCircuit,
            date = "2023-07-09",
            time = "14:00:00Z",
            firstPractice = JolpicaSession(date = "2023-07-07", time = "11:30:00Z"),
            secondPractice = JolpicaSession(date = "2023-07-07", time = "15:00:00Z"),
            thirdPractice = JolpicaSession(date = "2023-07-08", time = "10:30:00Z"),
            qualifying = JolpicaSession(date = "2023-07-08", time = "14:00:00Z"),
            sprintQualifying = JolpicaSession(date = "2023-10-20", time = "17:30:00Z"),
            sprint = JolpicaSession(date = "2023-10-21", time = "18:00:00Z")
        )

        val result = jolpicaSchedule.toSchedule()

        assertEquals(2023, result.season)
        assertEquals(10, result.round)
        assertEquals("British Grand Prix", result.raceName)
        assertEquals("2023-07-09", result.date)
        assertEquals("14:00:00Z", result.time)
        assertEquals("2023-07-07", result.firstPractice?.date)
        assertEquals("11:30:00Z", result.firstPractice?.time)
        assertEquals("2023-07-07", result.secondPractice?.date)
        assertEquals("15:00:00Z", result.secondPractice?.time)
        assertEquals("2023-07-08", result.thirdPractice?.date)
        assertEquals("10:30:00Z", result.thirdPractice?.time)
        assertEquals("2023-07-08", result.qualifying?.date)
        assertEquals("14:00:00Z", result.qualifying?.time)
        assertEquals("2023-10-20", result.sprintQualifying?.date)
        assertEquals("17:30:00Z", result.sprintQualifying?.time)
        assertEquals("2023-10-21", result.sprint?.date)
        assertEquals("18:00:00Z", result.sprint?.time)
    }

    @Test
    fun `toSchedule should handle null sessions`() {
        val jolpicaSchedule = JolpicaSchedule(
            season = 2023,
            round = 10,
            raceName = "British Grand Prix",
            circuit = sampleJolpicaCircuit,
            date = "2023-07-09"
        )

        val result = jolpicaSchedule.toSchedule()

        assertEquals(2023, result.season)
        assertEquals(null, result.url)
        assertEquals(null, result.time)
        assertEquals(null, result.firstPractice)
        assertEquals(null, result.secondPractice)
        assertEquals(null, result.thirdPractice)
        assertEquals(null, result.qualifying)
        assertEquals(null, result.sprintQualifying)
        assertEquals(null, result.sprint)
    }
}
