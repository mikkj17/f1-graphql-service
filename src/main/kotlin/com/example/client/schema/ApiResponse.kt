package com.example.client.schema

import com.fasterxml.jackson.annotation.JsonProperty

data class ApiResponse<T : Model>(
    @JsonProperty("MRData") val data: MrData<T>
)
