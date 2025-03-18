package com.example.server.schema.models.lap

import com.example.server.schema.models.Model
import com.example.server.schema.models.circuit.Circuit

data class RaceLaps(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    val circuit: Circuit,
    val date: String,
    val time: String?,
    val laps: List<Lap>,
) : Model
