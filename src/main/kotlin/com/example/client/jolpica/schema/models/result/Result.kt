package com.example.client.jolpica.schema.models.result

import com.example.client.jolpica.schema.models.constructor.Constructor
import com.example.client.jolpica.schema.models.driver.Driver
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val number: Int,
    val position: Int,
    val positionText: String,
    val points: Float,
    @SerialName("Driver") val driver: Driver,
    @SerialName("Constructor") val constructor: Constructor,
    val grid: Int,
    val laps: Int,
    val status: String,
    @SerialName("Time") val time: Time? = null,
    @SerialName("FastestLap") val fastestLap: FastestLap? = null,
)
