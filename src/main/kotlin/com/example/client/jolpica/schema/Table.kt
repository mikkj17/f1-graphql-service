package com.example.client.jolpica.schema

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Table<T : JolpicaModel>(
    @SerialName("Drivers") val drivers: List<T>? = null,
    @SerialName("Constructors") val constructors: List<T>? = null,
    @SerialName("Circuits") val circuits: List<T>? = null,
    @SerialName("Seasons") val seasons: List<T>? = null,
    @SerialName("Races") val races: List<T>? = null,
    @SerialName("StandingsLists") val standingsLists: List<T>? = null,
)
