package com.example.client.jolpica.schema.models.lap

import kotlinx.serialization.Serializable

@Serializable
data class Timing(
    val driverId: String,
    val position: Int,
    val time: String,
)
