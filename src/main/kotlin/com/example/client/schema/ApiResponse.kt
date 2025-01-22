package com.example.client.schema

import com.example.shared.schema.models.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class ApiResponse<T : Model>(
    @JsonProperty("MRData") val data: MrData<T>
)
