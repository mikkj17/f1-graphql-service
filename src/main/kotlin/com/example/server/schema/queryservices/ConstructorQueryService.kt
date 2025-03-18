package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.constructor.Constructor
import com.example.shared.mappers.toConstructor
import com.expediagroup.graphql.server.operations.Query

class ConstructorQueryService : Query {
    private val constructorListCache = mutableMapOf<Triple<Int?, Int?, String?>, List<Constructor>>()

    suspend fun constructors(year: Int? = null, round: Int? = null, driverId: String? = null) = constructorListCache
        .getOrPut(Triple(year, round, driverId)) {
            if (driverId != null && (year != null || round != null)) {
                throw AssertionError("driverId can't be used with year or round")
            }

            val client = JolpicaClient()
            val constructors = if (driverId != null)
                client.getConstructorsByDriver(driverId)
            else
                client.getConstructors(year, round)

            constructors.map { it.toConstructor() }
        }
}
