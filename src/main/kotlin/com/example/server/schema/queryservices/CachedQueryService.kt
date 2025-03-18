package com.example.server.schema.queryservices

import com.example.server.schema.models.Model
import com.expediagroup.graphql.server.operations.Query

abstract class CachedQueryService<T : Model, S : Model> : Query {
    protected val cache = mutableMapOf<Pair<Int?, Int?>, List<T>>()
    protected val idCache = mutableMapOf<String, S?>()
}
