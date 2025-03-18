package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.constructor.Constructor
import com.example.shared.mappers.toConstructor

class ConstructorQueryService : CachedQueryService<Constructor, Constructor>() {
    suspend fun constructors(year: Int? = null, round: Int? = null) = cache.getOrPut(year to round) {
        JolpicaClient()
            .getConstructors(year, round)
            .map { it.toConstructor() }
    }

    suspend fun constructor(constructorId: String) = idCache.getOrPut(constructorId) {
        JolpicaClient()
            .getConstructor(constructorId)
            .toConstructor()
    }
}
