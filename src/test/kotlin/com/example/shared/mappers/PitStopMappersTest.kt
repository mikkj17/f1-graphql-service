package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.pitstop.PitStop as JolpicaPitStop
import com.example.client.jolpica.schema.models.pitstop.RacePitStop as JolpicaRacePitStop

class PitStopMappersTest {

    private val sampleCircuit = JolpicaCircuit(
        id = "monaco",
        url = "url",
        name = "Monaco",
        location = JolpicaLocation(locality = "Monte Carlo", country = "Monaco")
    )

    private val sampleRacePitStops = JolpicaRacePitStop(
        season = 2023,
        round = 6,
        url = "url",
        raceName = "Monaco Grand Prix",
        circuit = sampleCircuit,
        date = "2023-05-28",
        time = "15:00:00Z",
        stops = listOf(
            JolpicaPitStop(driverId = "max_verstappen", lap = 20, stop = 1, time = "15:30:00", duration = "24.500"),
            JolpicaPitStop(driverId = "alonso", lap = 22, stop = 1, time = "15:32:00", duration = "23.800")
        )
    )

    @Test
    fun `toRacePitStop should map all pit stops when no filter is applied`() {
        val result = sampleRacePitStops.toRacePitStop(null)

        assertEquals(2023, result.season)
        assertEquals(2, result.stops.size)
        assertEquals("max_verstappen", result.stops[0].driverId)
        assertEquals(20, result.stops[0].lap)
    }

    @Test
    fun `toRacePitStop should filter pit stops by driverId`() {
        val result = sampleRacePitStops.toRacePitStop(listOf("max_verstappen"))

        assertEquals(1, result.stops.size)
        assertEquals("max_verstappen", result.stops[0].driverId)
    }
}
