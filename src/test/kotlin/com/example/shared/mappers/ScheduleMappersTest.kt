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
            qualifying = JolpicaSession(date = "2023-07-08", time = "14:00:00Z")
        )

        val result = jolpicaSchedule.toSchedule()

        assertEquals(2023, result.season)
        assertEquals(10, result.round)
        assertEquals("2023-07-07", result.firstPractice?.date)
        assertEquals("2023-07-08", result.qualifying?.date)
    }
}
