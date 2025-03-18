package com.example.server.schema.models.result

import com.example.server.schema.models.circuit.Circuit

data class Race(
    val season: Int,
    val round: Int,
    val url: String,
    val name: String,
    val circuit: Circuit,
    val date: String,
    val time: String,
    val results: List<RaceResult>,
)
