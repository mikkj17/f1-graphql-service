package com.example.shared.schema.models.schedule

import com.example.shared.schema.models.Model
import com.example.shared.schema.models.circuit.Circuit
import com.fasterxml.jackson.annotation.JsonProperty

data class Schedule(
    val season: Int,
    val round: Int,
    val url: String?,
    val raceName: String,
    @JsonProperty("Circuit") val circuit: Circuit,
    val date: String,
    val time: String?,
    @JsonProperty("FirstPractice") val firstPractice: Session?,
    @JsonProperty("SecondPractice") val secondPractice: Session?,
    @JsonProperty("ThirdPractice") val thirdPractice: Session?,
    @JsonProperty("Qualifying") val qualifying: Session?,
    @JsonProperty("SprintQualifying") val sprintQualifying: Session?,
    @JsonProperty("Sprint") val sprint: Session?,
) : Model
