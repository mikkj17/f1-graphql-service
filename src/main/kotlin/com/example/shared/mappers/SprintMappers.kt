package com.example.shared.mappers

import com.example.server.schema.models.result.Sprint
import com.example.client.jolpica.schema.models.result.Sprint as JolpicaSprint

fun JolpicaSprint.toSprint() = Sprint(
    season = season,
    round = round,
    url = url,
    name = name,
    circuit = circuit.toCircuit(),
    date = date,
    time = time,
    results = results.map { it.toResult() }
)

fun List<JolpicaSprint>.toSprints() = this.groupBy { it.season to it.round }
    .map { (_, sprints) ->
        val sprint = sprints.first()
        Sprint(
            season = sprint.season,
            round = sprint.round,
            url = sprint.url,
            name = sprint.name,
            circuit = sprint.circuit.toCircuit(),
            date = sprint.date,
            time = sprint.time,
            results = sprints.flatMap { it.results.map { res -> res.toResult() } }
        )
    }
