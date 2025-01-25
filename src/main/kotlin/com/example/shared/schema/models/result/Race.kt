package com.example.shared.schema.models.result

import com.example.shared.schema.models.Model
import com.example.shared.schema.models.circuit.Circuit
import com.fasterxml.jackson.annotation.JsonProperty

data class Race(
    val season: Int,
    val round: Int,
    val url: String,
    @JsonProperty("raceName") val name: String,
    @JsonProperty("Circuit") val circuit: Circuit,
    val date: String,
    val time: String,
    @JsonProperty("Results") val results: List<Result>
) : Model
