package com.example.client.jolpica.schema

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T : JolpicaModel>(
    @SerialName("MRData") val data: MrData<T>
)
