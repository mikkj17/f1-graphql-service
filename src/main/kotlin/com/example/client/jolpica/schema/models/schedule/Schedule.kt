package com.example.client.jolpica.schema.models.schedule

import com.example.client.jolpica.schema.models.JolpicaModel
import com.example.client.jolpica.schema.models.circuit.Circuit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val season: Int,
    val round: Int,
    val url: String? = null,
    val raceName: String,
    @SerialName("Circuit") val circuit: Circuit,
    val date: String,
    val time: String? = null,
    @SerialName("FirstPractice") val firstPractice: Session? = null,
    @SerialName("SecondPractice") val secondPractice: Session? = null,
    @SerialName("ThirdPractice") val thirdPractice: Session? = null,
    @SerialName("Qualifying") val qualifying: Session? = null,
    @SerialName("SprintQualifying") val sprintQualifying: Session? = null,
    @SerialName("Sprint") val sprint: Session? = null,
) : JolpicaModel
