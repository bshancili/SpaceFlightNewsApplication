package com.example.spaceflightnews.network

import com.example.spaceflightnews.model.ArticleResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ArticleApiService {
    @GET
    fun getArticlesByUrl(@Url url: String): Call<ArticleResponse>
}
