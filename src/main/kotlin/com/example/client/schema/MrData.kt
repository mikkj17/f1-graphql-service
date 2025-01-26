package com.example.client.schema

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MrData<T : Model>(
    val limit: Int,
    val offset: Int,
    val total: Int,
    @JsonProperty("DriverTable") val driverTable: Table<T>?,
    @JsonProperty("ConstructorTable") val constructorTable: Table<T>?,
    @JsonProperty("CircuitTable") val circuitTable: Table<T>?,
    @JsonProperty("SeasonTable") val seasonTable: Table<T>?,
    @JsonProperty("RaceTable") val raceTable: Table<T>?,
    @JsonProperty("StandingsTable") val standingsTable: Table<T>?,
)
