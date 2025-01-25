package com.example.shared.schema.models.standings

import com.example.shared.schema.models.constructor.Constructor
import com.fasterxml.jackson.annotation.JsonProperty

data class ConstructorStanding(
    val position: Int,
    val positionText: String,
    val points: Float,
    val wins: Int,
    @JsonProperty("Constructor") val constructor: Constructor,
)
