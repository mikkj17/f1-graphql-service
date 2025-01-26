package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class ConstructorQueryService : Query {
    suspend fun constructors(year: Int? = null, round: Int? = null) = ApiClient()
        .getConstructors(year, round)

    suspend fun constructor(constructorId: String) = ApiClient()
        .getConstructor(constructorId)
}
