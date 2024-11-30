package com.example.client.schema.models

import com.example.client.schema.MrData
import com.example.client.schema.Table
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
    companion object : StaticModel<com.example.client.schema.models.Driver> {
        override val endpoint = "drivers"
        override fun <T : Model> getTable(data: MrData<T>): Table<T> = data.driverTable!!
        override fun <T : Model> getModels(table: Table<T>): List<T> = table.drivers!!
    }

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
