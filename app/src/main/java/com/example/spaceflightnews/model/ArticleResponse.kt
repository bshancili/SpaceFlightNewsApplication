package com.example.spaceflightnews.model

data class ArticleResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Article>
)
