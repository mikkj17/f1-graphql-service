package com.example.client.jolpica.schema.models.circuit

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Circuit(
    @SerialName("circuitId") val id: String,
    val url: String,
    @SerialName("circuitName") val name: String,
    @SerialName("Location") val location: Location,
) : JolpicaModel
