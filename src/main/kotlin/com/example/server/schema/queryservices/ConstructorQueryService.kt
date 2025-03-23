package com.example.server.schema.queryservices

import com.example.client.jolpica.JolpicaClient
import com.example.server.schema.models.constructor.Constructor
import com.example.shared.mappers.toConstructor
import com.expediagroup.graphql.server.operations.Query

class ConstructorQueryService(
    private val jolpicaClient: JolpicaClient
) : Query {
    private val constructorListCache = mutableMapOf<Triple<Int?, Int?, String?>, List<Constructor>>()

    suspend fun constructors(year: Int? = null, round: Int? = null, driverId: String? = null): List<Constructor> {
        require(!(driverId != null && (year != null || round != null))) {
            "driverId can't be used with year or round"
        }

        require(round == null || year != null) {
            "round can't be used without year"
        }

        return constructorListCache.getOrPut(Triple(year, round, driverId)) {
            val constructors = if (driverId != null)
                jolpicaClient.getConstructorsByDriver(driverId)
            else
                jolpicaClient.getConstructors(year, round)

            constructors.map { it.toConstructor() }
        }
    }
}
