package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation

class CircuitMappersTest {

    @Test
    fun `toCircuit should map JolpicaCircuit to internal Circuit`() {
        val jolpicaLocation = JolpicaLocation(
            latitude = 41.57f,
            longitude = 2.26111f,
            locality = "Montmeló",
            country = "Spain"
        )
        val jolpicaCircuit = JolpicaCircuit(
            id = "catalunya",
            url = "https://en.wikipedia.org/wiki/Circuit_de_Barcelona-Catalunya",
            name = "Circuit de Barcelona-Catalunya",
            location = jolpicaLocation
        )

        val result = jolpicaCircuit.toCircuit()

        assertEquals(jolpicaCircuit.id, result.id)
        assertEquals(jolpicaCircuit.url, result.url)
        assertEquals(jolpicaCircuit.name, result.name)
        assertEquals(jolpicaLocation.latitude, result.location.latitude)
        assertEquals(jolpicaLocation.longitude, result.location.longitude)
        assertEquals(jolpicaLocation.locality, result.location.locality)
        assertEquals(jolpicaLocation.country, result.location.country)
    }
}
