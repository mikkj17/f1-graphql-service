package com.example.server.schema.models.driver

class DriverDetail(
    id: String,
    number: Int?,
    code: String?,
    url: String,
    givenName: String,
    familyName: String,
    dateOfBirth: String,
    nationality: String,
    val driverNumber: Int,
    val broadCastName: String,
    val countryCode: String,
    val fullName: String,
    val headshotUrl: String,
    val nameAcronym: String,
    val teamColour: String,
    val teamName: String,
) : Driver(id, number, code, url, givenName, familyName, dateOfBirth, nationality)
