package com.example.server.schema.models.standings

import com.example.server.schema.models.constructor.Constructor
import com.example.server.schema.models.driver.Driver

data class DriverStanding(
    val position: Int?,
    val positionText: String,
    val points: Float,
    val wins: Int,
    val driver: Driver,
    val constructors: List<Constructor>,
)
