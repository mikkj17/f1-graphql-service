package com.example.shared.schema.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Location(
    @JsonProperty("lat") val latitude: Double,
    @JsonProperty("long") val longitude: Double,
    val locality: String,
    val country: String,
)
