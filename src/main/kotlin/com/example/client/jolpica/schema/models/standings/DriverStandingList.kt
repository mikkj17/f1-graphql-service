package com.example.client.jolpica.schema.models.standings

import com.example.client.jolpica.schema.models.JolpicaModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverStandingList(
    val season: Int,
    val round: Int,
    @SerialName("DriverStandings") val standings: List<DriverStanding>
) : JolpicaModel
