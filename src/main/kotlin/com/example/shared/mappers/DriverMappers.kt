package com.example.shared.mappers

import com.example.server.schema.models.driver.Driver
import com.example.client.jolpica.schema.models.driver.Driver as JolpicaDriver

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
