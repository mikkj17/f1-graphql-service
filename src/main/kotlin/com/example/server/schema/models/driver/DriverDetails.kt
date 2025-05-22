package com.example.server.schema.models.driver

data class DriverDetails(
    val driverNumber: Int,
    val broadCastName: String,
    val countryCode: String?,
    val fullName: String,
    val headshotUrl: String,
    val nameAcronym: String,
    val teamColour: String,
    val teamName: String,
)
