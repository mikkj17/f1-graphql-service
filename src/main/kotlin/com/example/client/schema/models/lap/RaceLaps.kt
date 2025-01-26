package com.example.client.schema.models.lap

import com.example.client.schema.Model
import com.example.shared.schema.models.circuit.Circuit
import com.example.shared.schema.models.lap.Lap
import com.fasterxml.jackson.annotation.JsonProperty

data class RaceLaps(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    @JsonProperty("Circuit") val circuit: Circuit,
    val date: String,
    val time: String,
    @JsonProperty("Laps") val laps: List<Lap>
) : Model
