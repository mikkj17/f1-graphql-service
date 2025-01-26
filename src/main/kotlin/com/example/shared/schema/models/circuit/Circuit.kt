package com.example.shared.schema.models.circuit

import com.example.client.schema.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class Circuit(
    @JsonProperty("circuitId") val id: String,
    val url: String,
    @JsonProperty("circuitName") val name: String,
    @JsonProperty("Location") val location: Location,
) : Model
