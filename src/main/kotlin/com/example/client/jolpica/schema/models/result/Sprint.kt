package com.example.client.jolpica.schema.models.result

import com.example.client.jolpica.schema.models.JolpicaModel
import com.example.client.jolpica.schema.models.circuit.Circuit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sprint(
    val season: Int,
    val round: Int,
    val url: String,
    @SerialName("raceName") val name: String,
    @SerialName("Circuit") val circuit: Circuit,
    val date: String,
    val time: String? = null,
    @SerialName("SprintResults") val results: List<Result>
) : JolpicaModel
