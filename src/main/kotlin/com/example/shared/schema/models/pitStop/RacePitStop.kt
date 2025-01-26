package com.example.shared.schema.models.pitStop

import com.example.client.schema.Model
import com.example.shared.schema.models.circuit.Circuit
import com.fasterxml.jackson.annotation.JsonProperty

data class RacePitStop(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    @JsonProperty("Circuit") val circuit: Circuit,
    val date: String,
    val time: String,
    @JsonProperty("PitStops") var pitStops: List<PitStop>
) : Model
