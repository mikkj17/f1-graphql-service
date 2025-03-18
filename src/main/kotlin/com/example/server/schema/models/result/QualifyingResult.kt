package com.example.server.schema.models.result

import com.example.server.schema.models.constructor.Constructor
import com.example.server.schema.models.driver.Driver

data class QualifyingResult(
    val number: Int,
    val position: Int,
    val driver: Driver,
    val constructor: Constructor,
    val q1: String?,
    val q2: String?,
    val q3: String?,
)
