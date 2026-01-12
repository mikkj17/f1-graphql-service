package com.example.shared.mappers

import com.example.server.schema.models.result.*
import com.example.client.jolpica.schema.models.result.AverageSpeed as JolpicaAverageSpeed
import com.example.client.jolpica.schema.models.result.FastestLap as JolpicaFastestLap
import com.example.client.jolpica.schema.models.result.Race as JolpicaRace
import com.example.client.jolpica.schema.models.result.Result as JolpicaRaceResult
import com.example.client.jolpica.schema.models.result.SimpleTime as JolpicaSimpleTime
import com.example.client.jolpica.schema.models.result.Time as JolpicaTime

fun List<JolpicaRace>.toRaces() = this.groupBy { it.season to it.round }
    .map { (_, races) ->
        val race = races.first()
        Race(
            season = race.season,
            round = race.round,
            url = race.url,
            name = race.name,
            circuit = race.circuit.toCircuit(),
            date = race.date,
            time = race.time,
            results = races.flatMap { it.results.map { res -> res.toResult() } }
        )
    }

fun JolpicaRace.toRace() = Race(
    season = season,
    round = round,
    url = url,
    name = name,
    circuit = circuit.toCircuit(),
    date = date,
    time = time,
    results = results.map { it.toResult() }
)

fun JolpicaRaceResult.toResult() = Result(
    number = number?.toIntOrNull(),
    position = position,
    positionText = positionText,
    points = points,
    driver = driver.toDriver(),
    constructor = constructor.toConstructor(),
    grid = grid,
    laps = laps,
    status = status,
    time = time?.toTime(),
    fastestLap = fastestLap?.toFastestLap(),
)

fun JolpicaSimpleTime.toSimpleTime() = SimpleTime(
    time = time,
)

fun JolpicaTime.toTime() = Time(
    milliseconds = milliseconds,
    time = time,
)

fun JolpicaAverageSpeed.toAverageSpeed() = AverageSpeed(
    unit = unit,
    speed = speed,
)

fun JolpicaFastestLap.toFastestLap() = FastestLap(
    rank = rank,
    lap = lap,
    time = time.toSimpleTime(),
    averageSpeed = averageSpeed?.toAverageSpeed(),
)
