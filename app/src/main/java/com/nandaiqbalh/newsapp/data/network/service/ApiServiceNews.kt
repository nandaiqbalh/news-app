package com.nandaiqbalh.newsapp.data.network.service

import com.nandaiqbalh.newsapp.BuildConfig.API_KEY
import com.nandaiqbalh.newsapp.data.network.models.news.NewsResponse
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceNews {

	@GET("top-headlines")
	suspend fun getBreakingNews(
		@Query("country")
		countryCode: String = "id",
		@Query("page")
		pageNumber: Int = 1,
	): Response<NewsResponse>

	@GET("everything")
	suspend fun searchForNews(
		@Query("q")
		searchQuery: String,
		@Query("page")
		pageNumber: Int = 1,
	): Response<NewsResponse>
}