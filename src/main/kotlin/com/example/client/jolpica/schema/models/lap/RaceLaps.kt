package com.example.client.jolpica.schema.models.lap

import com.example.client.jolpica.schema.models.JolpicaModel
import com.example.client.jolpica.schema.models.circuit.Circuit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RaceLaps(
    val season: Int,
    val round: Int,
    val url: String,
    val raceName: String,
    @SerialName("Circuit") val circuit: Circuit,
    val date: String,
    val time: String? = null,
    @SerialName("Laps") val laps: List<Lap>
) : JolpicaModel
