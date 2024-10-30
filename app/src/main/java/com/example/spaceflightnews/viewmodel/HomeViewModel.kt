package com.example.spaceflightnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.spaceflightnews.model.Article
import com.example.spaceflightnews.model.ArticleResponse
import com.example.spaceflightnews.network.ApiClient
import com.example.spaceflightnews.network.ArticleApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val articleApiService: ArticleApiService = ApiClient.articleApiService

    private val articles = mutableListOf<Article>()
    private var nextUrl: String? = "${ApiClient.BASE_URL}articles/"
    private var isLoading = false

    val articlesLiveData = MutableLiveData<List<Article>>()

    fun fetchArticles() {
        if (isLoading || nextUrl == null) return
        isLoading = true

        articleApiService.getArticlesByUrl(nextUrl!!).enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(call: Call<ArticleResponse>, response: Response<ArticleResponse>) {
                response.body()?.let {
                    articles.addAll(it.results)
                    articlesLiveData.postValue(articles)
                    nextUrl = it.next
                }
                isLoading = false
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                isLoading = false
            }
        })
    }
}
