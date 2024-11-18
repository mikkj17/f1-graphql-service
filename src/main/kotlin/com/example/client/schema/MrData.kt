package com.example.client.schema

import com.example.client.schema.models.Model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MrData<T : Model>(
    @JsonProperty("DriverTable") val driverTable: Table<T>? = null,
    @JsonProperty("ConstructorTable") val constructorTable: Table<T>? = null
)