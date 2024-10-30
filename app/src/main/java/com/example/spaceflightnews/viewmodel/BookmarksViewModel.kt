package com.example.spaceflightnews.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spaceflightnews.model.Article

class BookmarksViewModel(application: Application) : AndroidViewModel(application) {
    private val articleViewModel: ArticleViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(
        ArticleViewModel::class.java)

    fun getBookmarkedArticles(): List<Article> {
        return articleViewModel.getBookmarkedArticles()
    }
}
