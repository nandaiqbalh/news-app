package com.nandaiqbalh.newsapp.data.network.models.news

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)