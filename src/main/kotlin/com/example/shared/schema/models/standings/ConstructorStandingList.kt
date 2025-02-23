package com.example.shared.schema.models.standings

import com.example.client.schema.Model
import com.fasterxml.jackson.annotation.JsonProperty

data class ConstructorStandingList(
    val season: Int,
    val round: Int,
    @JsonProperty("ConstructorStandings") val standings: List<ConstructorStanding>
) : Model
