package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.result.Sprint as JolpicaSprint

class SprintMappersTest {

    private val sampleJolpicaCircuit = JolpicaCircuit(
        id = "interlagos",
        url = "url",
        name = "Interlagos",
        location = JolpicaLocation(locality = "Sao Paulo", country = "Brazil")
    )

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
            results = emptyList()
        )

        val result = jolpicaSprint.toSprint()

        assertEquals(2023, result.season)
        assertEquals(21, result.round)
        assertEquals("Brazilian Grand Prix", result.name)
    }
}
