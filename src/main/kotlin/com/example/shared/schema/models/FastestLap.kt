package com.example.shared.schema.models

import com.fasterxml.jackson.annotation.JsonProperty

data class FastestLap(
    val rank: Int,
    val lap: Int,
    @JsonProperty("Time") val time: SimpleTime,
    @JsonProperty("AverageSpeed") val averageSpeed: AverageSpeed,
)
