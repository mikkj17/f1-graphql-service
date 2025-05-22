package com.example.client.openf1.schema.models.driver

import com.example.client.openf1.schema.models.OpenF1Model
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    val broadcastName: String,
    val countryCode: String?,
    val driverNumber: Int,
    val fullName: String,
    val headshotUrl: String,
    val nameAcronym: String,
    val teamColour: String,
    val teamName: String
) : OpenF1Model
