package com.example.server.schema.models.result

import com.example.server.schema.models.constructor.Constructor
import com.example.server.schema.models.driver.Driver

data class RaceResult(
    val number: Int?,
    val position: Int,
    val positionText: String,
    val points: Float,
    val driver: Driver,
    val constructor: Constructor,
    val grid: Int,
    val laps: Int,
    val status: String,
    val time: Time?,
    val fastestLap: FastestLap?,
)
