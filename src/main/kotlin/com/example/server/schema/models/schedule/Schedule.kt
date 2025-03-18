package com.example.server.schema.models.schedule

import com.example.server.schema.models.Model
import com.example.server.schema.models.circuit.Circuit

data class Schedule(
    val season: Int,
    val round: Int,
    val url: String?,
    val raceName: String,
    val circuit: Circuit,
    val date: String,
    val time: String?,
    val firstPractice: Session?,
    val secondPractice: Session?,
    val thirdPractice: Session?,
    val qualifying: Session?,
    val sprintQualifying: Session?,
    val sprint: Session?,
) : Model
