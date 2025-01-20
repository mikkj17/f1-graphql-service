package com.example.client.schema.models

import com.example.client.schema.MrData
import com.example.client.schema.Table
import com.example.server.schema.models.Constructor

data class Constructor(
    val constructorId: String,
    val url: String,
    val name: String,
    val nationality: String,
) : Model {
    companion object : StaticModel<com.example.client.schema.models.Constructor> {
        override val endpoint = "constructors"
        override fun <T : Model> getTable(data: MrData<T>): Table<T> = data.constructorTable!!
        override fun <T : Model> getModels(table: Table<T>): List<T> = table.constructors!!
    }

    fun toConstructor() = Constructor(
        id = constructorId,
        url = url,
        name = name,
        nationality = nationality,
    )
}
