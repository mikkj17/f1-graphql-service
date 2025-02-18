package com.example.server.schema

import com.example.client.ApiClient
import com.example.shared.schema.models.constructor.Constructor

class ConstructorQueryService : CachedQueryService<Constructor>() {
    suspend fun constructors(year: Int? = null, round: Int? = null) = cache.getOrPut(year to round) {
        ApiClient().getConstructors(year, round)
    }

    suspend fun constructor(constructorId: String) = idCache.getOrPut(constructorId) {
        ApiClient().getConstructor(constructorId)
    }
}
