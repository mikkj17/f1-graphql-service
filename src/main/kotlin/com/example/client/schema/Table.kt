package com.example.client.schema

import com.example.client.schema.models.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class Table<T : Model>(
    @JsonProperty("Drivers") val drivers: List<T>? = null,
    @JsonProperty("Constructors") val constructors: List<T>? = null
)
