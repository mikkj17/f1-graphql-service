package com.example.client.jolpica.schema.models.standings

import com.example.client.jolpica.schema.models.constructor.Constructor
import com.example.client.jolpica.schema.models.driver.Driver
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverStanding(
    val position: Int? = null,
    val positionText: String,
    val points: Float,
    val wins: Int,
    @SerialName("Driver") val driver: Driver,
    @SerialName("Constructors") val constructors: List<Constructor>,
)
