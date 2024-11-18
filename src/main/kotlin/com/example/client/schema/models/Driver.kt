package com.example.client.schema.models

import com.example.server.schema.models.Driver

data class Driver(
    val driverId: String,
    val permanentNumber: Int,
    val code: String?,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String,
) : Model {
    fun toDriver() = Driver(
        id = driverId,
        number = permanentNumber,
        code = code,
        url = url,
        givenName = givenName,
        familyName = familyName,
        dateOfBirth = dateOfBirth,
        nationality = nationality,
    )
}
