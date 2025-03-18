package com.example.server.schema.models.pitstop

import com.example.server.schema.models.Model
import com.example.server.schema.models.circuit.Circuit

data class RacePitStop(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    val circuit: Circuit,
    val date: String,
    val time: String,
    val pitStops: List<PitStop>
) : Model
