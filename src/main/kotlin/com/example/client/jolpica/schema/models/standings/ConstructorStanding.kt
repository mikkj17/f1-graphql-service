package com.example.client.jolpica.schema.models.standings

import com.example.client.jolpica.schema.models.constructor.Constructor
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConstructorStanding(
    val position: Int,
    val positionText: String,
    val points: Float,
    val wins: Int,
    @SerialName("Constructor") val constructor: Constructor,
)
