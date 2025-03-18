package com.example.client.jolpica.schema.models.pitstop

import kotlinx.serialization.Serializable

@Serializable
data class PitStop(
    val driverId: String,
    val lap: Int,
    val stop: Int,
    val time: String,
    val duration: String,
)
