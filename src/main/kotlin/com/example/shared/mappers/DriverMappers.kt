package com.example.shared.mappers

import com.example.server.schema.models.driver.Driver
import com.example.server.schema.models.driver.DriverDetail
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver
import com.example.client.openf1.schema.models.driver.Driver as OpenF1Driver

fun JolpicaDriver.toDriver() = Driver(
    id = id,
    number = number,
    code = code,
    url = url,
    givenName = givenName,
    familyName = familyName,
    dateOfBirth = dateOfBirth,
    nationality = nationality,
)

fun JolpicaDriver.toDetailedDriver(openF1Driver: OpenF1Driver) = DriverDetail(
    id = id,
    number = number,
    code = code,
    url = url,
    givenName = givenName,
    familyName = familyName,
    dateOfBirth = dateOfBirth,
    nationality = nationality,
    driverNumber = openF1Driver.driverNumber,
    broadCastName = openF1Driver.broadcastName,
    countryCode = openF1Driver.countryCode,
    fullName = openF1Driver.fullName,
    headshotUrl = openF1Driver.headshotUrl.substringBefore(".transform"),
    nameAcronym = openF1Driver.nameAcronym,
    teamColour = openF1Driver.teamColour,
    teamName = openF1Driver.teamName
)
