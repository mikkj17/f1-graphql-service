package com.example.client.schema.models

import com.example.client.schema.MrData
import com.example.client.schema.Table

interface Model

interface StaticModel<T : Model> {
    val endpoint: String
    fun <T : Model> getTable(data: MrData<T>): Table<T>
    fun <T : Model> getModels(table: Table<T>): List<T>
}
