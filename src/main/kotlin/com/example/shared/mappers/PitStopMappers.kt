package com.example.shared.mappers

import com.example.server.schema.models.pitstop.PitStop
import com.example.server.schema.models.pitstop.RacePitStop
import com.example.client.jolpica.schema.models.pitstop.PitStop as JolpicaPitStop
import com.example.client.jolpica.schema.models.pitstop.RacePitStop as JolpicaRacePitStop

fun JolpicaRacePitStop.toRacePitStop(drivers: List<String>?) = RacePitStop(
    season = season,
    round = round,
    url = url,
    raceName = raceName,
    circuit = circuit.toCircuit(),
    date = date,
    time = time,
    pitStops = pitStops
        .filter { drivers == null || it.driverId in drivers }
        .map { it.toPitStop() }
)

fun JolpicaPitStop.toPitStop() = PitStop(
    driverId = driverId,
    lap = lap,
    stop = stop,
    time = time,
    duration = duration,
)
