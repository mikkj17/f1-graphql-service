package com.example.shared.schema.models.lap

import com.fasterxml.jackson.annotation.JsonProperty

data class Lap(
    val number: Int,
    @JsonProperty("Timings") val timings: List<Timing>,
)
