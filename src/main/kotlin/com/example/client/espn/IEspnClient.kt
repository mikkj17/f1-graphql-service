package com.example.client.espn

import com.example.client.espn.schema.models.EspnArticle

interface IEspnClient {
    suspend fun getNews(): List<EspnArticle>
}
