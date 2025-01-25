package com.example.shared.schema.models.circuit

import com.fasterxml.jackson.annotation.JsonProperty

data class Location(
    @JsonProperty("lat") val latitude: Float,
    @JsonProperty("long") val longitude: Float,
    val locality: String,
    val country: String,
)
