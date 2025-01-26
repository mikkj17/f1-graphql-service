package com.example.server.schema.models

import com.example.shared.schema.models.circuit.Circuit
import com.example.shared.schema.models.lap.Lap

data class RaceLaps(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    val circuit: Circuit,
    val date: String,
    val time: String,
    val laps: List<Lap>
)
