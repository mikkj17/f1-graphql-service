package com.example.client.jolpica.schema.models.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AverageSpeed(
    @SerialName("units") val unit: String,
    val speed: Float,
)
