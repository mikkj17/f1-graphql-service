package com.example.server.schema.models.lap

data class Lap(
    val number: Int,
    val timings: List<Timing>,
)
