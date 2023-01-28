package com.nandaiqbalh.newsapp.data.network.datasource

import com.nandaiqbalh.newsapp.data.network.models.news.NewsResponse
import com.nandaiqbalh.newsapp.data.network.service.ApiServiceNews
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import retrofit2.Response
import javax.inject.Inject

interface NewsRemoteDataSource {

	suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

	suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse>

}

class NewsRemoteDataSourceImpl @Inject constructor(private val apiServiceNews: ApiServiceNews): NewsRemoteDataSource{

	override suspend fun getBreakingNews(countryCode: String, pageNumber: Int):  Response<NewsResponse>{
		return apiServiceNews.getBreakingNews(countryCode, pageNumber)
	}

	override suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse> {
		return apiServiceNews.searchForNews(query, pageNumber)
	}
}