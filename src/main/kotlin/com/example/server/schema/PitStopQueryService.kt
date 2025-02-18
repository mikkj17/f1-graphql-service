package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.pitStop.RacePitStop
import com.expediagroup.graphql.server.operations.Query

private data class CacheKey(
    val year: Int,
    val round: Int,
    val driverId: String?,
    val stop: Int?,
)

class PitStopQueryService : Query {
    private val _cache = mutableMapOf<CacheKey, RacePitStop>()

    suspend fun pitStops(year: Int, round: Int, driverId: String? = null, stop: Int? = null): RacePitStop {
        return _cache.getOrPut(CacheKey(year, round, driverId, stop)) {
            ApiClient()
                .getPitStops(year, round)
                .apply {
                    pitStops = pitStops
                        .filter { driverId == null || it.driverId == driverId }
                        .filter { stop == null || it.stop == stop }
                }
        }
    }
}
