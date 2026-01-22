package com.example.shared.mappers

import kotlin.test.Test
import kotlin.test.assertEquals
import com.example.client.jolpica.schema.models.circuit.Circuit as JolpicaCircuit
import com.example.client.jolpica.schema.models.circuit.Location as JolpicaLocation
import com.example.client.jolpica.schema.models.lap.Lap as JolpicaLap
import com.example.client.jolpica.schema.models.lap.RaceLaps as JolpicaRaceLaps
import com.example.client.jolpica.schema.models.lap.Timing as JolpicaTiming

class LapMappersTest {

    private val sampleCircuit = JolpicaCircuit(
        id = "monaco",
        url = "url",
        name = "Monaco",
        location = JolpicaLocation(locality = "Monte Carlo", country = "Monaco")
    )

    private val sampleRaceLaps = JolpicaRaceLaps(
        season = 2023,
        round = 6,
        url = "url",
        raceName = "Monaco Grand Prix",
        circuit = sampleCircuit,
        date = "2023-05-28",
        laps = listOf(
            JolpicaLap(
                number = 1,
                timings = listOf(
                    JolpicaTiming(driverId = "max_verstappen", position = 1, time = "1:15.000"),
                    JolpicaTiming(driverId = "alonso", position = 2, time = "1:16.000")
                )
            ),
            JolpicaLap(
                number = 2,
                timings = listOf(
                    JolpicaTiming(driverId = "max_verstappen", position = 1, time = "1:14.500"),
                    JolpicaTiming(driverId = "alonso", position = 2, time = "1:15.500")
                )
            )
        )
    )

    @Test
    fun `toRaceLaps should map all laps and timings when no filter is applied`() {
        val result = listOf(sampleRaceLaps).toRaceLaps(null)

        assertEquals(2023, result.season)
        assertEquals(2, result.laps.size)
        assertEquals(1, result.laps[0].number)
        assertEquals(2, result.laps[0].timings.size)
        assertEquals("max_verstappen", result.laps[0].timings[0].driverId)
    }

    @Test
    fun `toRaceLaps should filter timings by driverId`() {
        val result = listOf(sampleRaceLaps).toRaceLaps(listOf("max_verstappen"))

        assertEquals(2, result.laps.size)
        assertEquals(1, result.laps[0].timings.size)
        assertEquals("max_verstappen", result.laps[0].timings[0].driverId)
        assertEquals(1, result.laps[1].timings.size)
        assertEquals("max_verstappen", result.laps[1].timings[0].driverId)
    }

    @Test
    fun `toRaceLaps should combine multiple JolpicaRaceLaps chunks`() {
        val chunk2 = sampleRaceLaps.copy(
            laps = listOf(
                JolpicaLap(
                    number = 3,
                    timings = listOf(
                        JolpicaTiming(driverId = "max_verstappen", position = 1, time = "1:14.000")
                    )
                )
            )
        )

        val result = listOf(sampleRaceLaps, chunk2).toRaceLaps(null)

        assertEquals(3, result.laps.size)
        assertEquals(1, result.laps[0].number)
        assertEquals(2, result.laps[1].number)
        assertEquals(3, result.laps[2].number)
    }
}
