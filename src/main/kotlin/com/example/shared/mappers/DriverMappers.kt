package com.example.shared.mappers

import com.example.server.schema.models.driver.Driver
import com.example.server.schema.models.driver.DriverDetail
import com.example.server.schema.models.driver.DriverDetails
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver
import com.example.client.openf1.schema.models.driver.Driver as OpenF1Driver

fun JolpicaDriver.toDriver() = Driver(
    id = id,
    number = number?.toIntOrNull(),
    code = code,
    url = url,
    givenName = givenName,
    familyName = familyName,
    dateOfBirth = dateOfBirth,
    nationality = nationality,
)

fun OpenF1Driver.toDriverDetails() = DriverDetails(
    driverNumber = driverNumber,
    broadCastName = broadcastName,
    countryCode = countryCode,
    fullName = fullName,
    headshotUrl = headshotUrl.substringBefore(".transform"),
    nameAcronym = nameAcronym,
    teamColour = teamColour,
    teamName = teamName
)

fun JolpicaDriver.toDetailedDriver(openF1Driver: OpenF1Driver?) = DriverDetail(
    id = id,
    number = number?.toIntOrNull(),
    code = code,
    url = url,
    givenName = givenName,
    familyName = familyName,
    dateOfBirth = dateOfBirth,
    nationality = nationality,
    details = openF1Driver?.toDriverDetails(),
)
