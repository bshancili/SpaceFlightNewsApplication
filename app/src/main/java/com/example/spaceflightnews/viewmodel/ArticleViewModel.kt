package com.example.spaceflightnews.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.spaceflightnews.model.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    fun toggleArticleBookmarkState(article: Article): Boolean {
        val bookmarkedArticles = getBookmarkedArticles().toMutableList()
        val isAlreadyBookmarked = bookmarkedArticles.any { it.id == article.id }

        if (isAlreadyBookmarked) {
            bookmarkedArticles.removeAll { it.id == article.id }
        } else {
            bookmarkedArticles.add(0, article)
        }

        val sharedPreferences = getApplication<Application>().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(bookmarkedArticles)
        editor.putString("bookmarked_articles", json)
        editor.apply()

        return !isAlreadyBookmarked
    }

    fun getBookmarkedArticles(): List<Article> {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("bookmarked_articles", null)
        val type = object : TypeToken<List<Article>>() {}.type
        return if (json != null) {
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun isArticleBookmarked(article: Article): Boolean {
        val bookmarkedArticles = getBookmarkedArticles()
        return bookmarkedArticles.any { it.id == article.id }
    }
}
