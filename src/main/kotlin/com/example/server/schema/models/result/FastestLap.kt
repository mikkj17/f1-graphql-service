package com.example.server.schema.models.result

data class FastestLap(
    val rank: Int,
    val lap: Int,
    val time: SimpleTime,
    val averageSpeed: AverageSpeed?,
)
