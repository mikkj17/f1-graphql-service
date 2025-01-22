package com.example.client.schema

import com.example.shared.schema.models.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class Table<T : Model>(
    @JsonProperty("Drivers") val drivers: List<T>? = null,
    @JsonProperty("Constructors") val constructors: List<T>? = null,
    @JsonProperty("Circuits") val circuits: List<T>? = null,
    @JsonProperty("Seasons") val seasons: List<T>? = null,
)
