package com.example.client.schema

import com.example.shared.schema.models.Model
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Table<T : Model>(
    @JsonProperty("Drivers") val drivers: List<T>?,
    @JsonProperty("Constructors") val constructors: List<T>?,
    @JsonProperty("Circuits") val circuits: List<T>?,
    @JsonProperty("Seasons") val seasons: List<T>?,
    @JsonProperty("Races") val races: List<T>?,
    @JsonProperty("StandingsLists") val standingsLists: List<T>?,
)
