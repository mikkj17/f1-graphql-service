package com.example.server.schema

import com.example.client.schema.Model
import com.expediagroup.graphql.server.operations.Query

abstract class CachedQueryService<T : Model> : Query {
    protected val cache = mutableMapOf<Pair<Int?, Int?>, List<T>>()
    protected val idCache = mutableMapOf<String, T>()
}
