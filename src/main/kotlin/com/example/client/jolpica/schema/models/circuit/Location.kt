package com.example.client.jolpica.schema.models.circuit

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("lat") val latitude: Float,
    @SerialName("long") val longitude: Float,
    val locality: String,
    val country: String,
)
