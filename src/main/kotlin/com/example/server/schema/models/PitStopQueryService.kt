package com.example.server.schema.models

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class PitStopQueryService : Query {
    suspend fun pitStops(year: Int, round: Int, driverId: String? = null, stop: Int? = null) = ApiClient()
        .getPitStops(year, round)
        .apply {
            pitStops = pitStops
                .filter { driverId == null || it.driverId == driverId }
                .filter { stop == null || it.stop == stop }
        }
}
