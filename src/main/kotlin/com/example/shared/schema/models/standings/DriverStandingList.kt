package com.example.shared.schema.models.standings

import com.example.shared.schema.models.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class DriverStandingList(
    val season: Int,
    val round: Int,
    @JsonProperty("DriverStandings") val standings: List<DriverStanding>
) : Model
