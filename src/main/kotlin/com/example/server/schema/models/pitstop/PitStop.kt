package com.example.server.schema.models.pitstop

data class PitStop(
    val driverId: String,
    val lap: Int,
    val stop: Int,
    val time: String,
    val duration: String,
)
