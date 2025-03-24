package com.example.client.jolpica.schema.models.pitstop

import com.example.client.jolpica.schema.models.JolpicaModel
import com.example.client.jolpica.schema.models.circuit.Circuit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RacePitStop(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    @SerialName("Circuit") val circuit: Circuit,
    val date: String,
    val time: String,
    @SerialName("PitStops") val stops: List<PitStop>
) : JolpicaModel
