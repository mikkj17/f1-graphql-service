package com.example.client.jolpica.schema

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MrData<T : JolpicaModel>(
    val limit: Int,
    val offset: Int,
    val total: Int,
    @SerialName("DriverTable") val driverTable: Table<T>? = null,
    @SerialName("ConstructorTable") val constructorTable: Table<T>? = null,
    @SerialName("CircuitTable") val circuitTable: Table<T>? = null,
    @SerialName("SeasonTable") val seasonTable: Table<T>? = null,
    @SerialName("RaceTable") val raceTable: Table<T>? = null,
    @SerialName("StandingsTable") val standingsTable: Table<T>? = null,
)
