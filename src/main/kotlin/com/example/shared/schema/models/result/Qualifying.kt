package com.example.shared.schema.models.result

import com.example.client.schema.Model
import com.example.shared.schema.models.circuit.Circuit
import com.fasterxml.jackson.annotation.JsonProperty

data class Qualifying(
    val season: Int,
    val round: Int,
    val url: String,
    @JsonProperty("raceName") val name: String,
    @JsonProperty("Circuit") val circuit: Circuit,
    val date: String,
    val time: String,
    @JsonProperty("QualifyingResults") val results: List<QualifyingResult>
) : Model
