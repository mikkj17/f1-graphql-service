package com.example.client.jolpica.schema.models.lap

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lap(
    val number: Int,
    @SerialName("Timings") val timings: List<Timing>,
)
