package com.example.shared.schema.models.pitStop

data class PitStop(
    val driverId: String,
    val lap: Int,
    val stop: Int,
    val time: String,
    val duration: String,
)
