package com.example.client.jolpica.schema.models.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FastestLap(
    val rank: Int,
    val lap: Int,
    @SerialName("Time") val time: SimpleTime,
    @SerialName("AverageSpeed") val averageSpeed: AverageSpeed? = null,
)
