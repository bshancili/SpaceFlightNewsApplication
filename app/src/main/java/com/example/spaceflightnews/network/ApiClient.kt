package com.example.spaceflightnews.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    const val BASE_URL = "https://api.spaceflightnewsapi.net/v4/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val articleApiService: ArticleApiService = retrofit.create(ArticleApiService::class.java)
}
