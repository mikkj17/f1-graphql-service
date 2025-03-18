package com.example.shared.mappers

import com.example.server.schema.models.lap.Lap
import com.example.server.schema.models.lap.RaceLaps
import com.example.server.schema.models.lap.Timing
import com.example.client.jolpica.schema.models.lap.RaceLaps as JolpicaRaceLaps
import com.example.client.jolpica.schema.models.lap.Timing as JolpicaTiming

fun List<JolpicaRaceLaps>.toRaceLaps(drivers: List<String>?): RaceLaps {
    val race = this.first()

    val laps = this
        .flatMap { it.laps }
        .groupBy { it.number }
        .map { (number, laps) ->
            Lap(
                number = number,
                timings = laps
                    .flatMap { it.timings }
                    .filter { drivers == null || it.driverId in drivers }
                    .map { it.toTiming() }
            )
        }

    return RaceLaps(
        season = race.season,
        round = race.round,
        url = race.url,
        raceName = race.raceName,
        circuit = race.circuit.toCircuit(),
        date = race.date,
        time = race.time,
        laps = laps,
    )
}

fun JolpicaTiming.toTiming() = Timing(
    driverId = driverId,
    position = position,
    time = time,
)
