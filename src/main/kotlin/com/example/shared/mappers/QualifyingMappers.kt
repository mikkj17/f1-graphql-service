package com.example.shared.mappers

import com.example.server.schema.models.result.Qualifying
import com.example.server.schema.models.result.QualifyingResult
import com.example.client.jolpica.schema.models.result.Qualifying as JolpicaQualifying
import com.example.client.jolpica.schema.models.result.QualifyingResult as JolpicaQualifyingResult

fun List<JolpicaQualifying>.toQualifyings() = this.groupBy { it.season to it.round }
    .map { (_, qualifyings) ->
        val qualifying = qualifyings.first()
        Qualifying(
            season = qualifying.season,
            round = qualifying.round,
            url = qualifying.url,
            name = qualifying.name,
            circuit = qualifying.circuit.toCircuit(),
            date = qualifying.date,
            time = qualifying.time,
            results = qualifyings.flatMap { it.results.map { res -> res.toQualifyingResult() } }
        )
    }

fun JolpicaQualifying.toQualifying() = Qualifying(
    season = season,
    round = round,
    url = url,
    name = name,
    circuit = circuit.toCircuit(),
    date = date,
    time = time,
    results = results.map { it.toQualifyingResult() }
)

fun JolpicaQualifyingResult.toQualifyingResult() = QualifyingResult(
    number = number,
    position = position,
    driver = driver.toDriver(),
    constructor = constructor.toConstructor(),
    q1 = q1,
    q2 = q2,
    q3 = q3,
)
