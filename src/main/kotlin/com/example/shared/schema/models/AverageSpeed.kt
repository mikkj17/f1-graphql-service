package com.example.shared.schema.models

import com.fasterxml.jackson.annotation.JsonProperty

data class AverageSpeed(
    @JsonProperty("units") val unit: String,
    val speed: Float,
)
