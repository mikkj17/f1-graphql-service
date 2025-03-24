package com.example.server.schema.models.driver

import com.example.server.schema.models.Model

data class DriverDetail(
    val id: String,
    val number: Int?,
    val code: String?,
    val url: String,
    val givenName: String,
    val familyName: String,
    val dateOfBirth: String,
    val nationality: String,
    val details: DriverDetails?,
) : Model
