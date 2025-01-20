package com.example.client.schema.models

import com.example.client.schema.MrData
import com.example.client.schema.Table
import com.example.server.schema.models.Circuit
import com.fasterxml.jackson.annotation.JsonProperty

data class Circuit(
    val circuitId: String,
    val url: String,
    val circuitName: String,
    @JsonProperty("Location") val location: Location,
) : Model {
    companion object : StaticModel<com.example.client.schema.models.Circuit> {
        override val endpoint = "circuits"
        override fun <T : Model> getTable(data: MrData<T>): Table<T> = data.circuitTable!!
        override fun <T : Model> getModels(table: Table<T>): List<T> = table.circuits!!
    }

    fun toCircuit() = Circuit(
        id = circuitId,
        url = url,
        name = circuitName,
        location = location.toLocation(),
    )
}
