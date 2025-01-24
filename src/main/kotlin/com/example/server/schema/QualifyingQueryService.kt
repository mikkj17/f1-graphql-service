package com.example.server.schema

import com.example.client.ApiClient
import com.expediagroup.graphql.server.operations.Query

class QualifyingQueryService : Query {
    suspend fun qualifying(year: Int, round: Int) = ApiClient()
        .getQualifying(year, round)
}
