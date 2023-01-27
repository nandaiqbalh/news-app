package com.nandaiqbalh.newsapp.data.repository

import com.nandaiqbalh.newsapp.data.network.datasource.NewsRemoteDataSource
import com.nandaiqbalh.newsapp.data.network.models.news.NewsResponse
import com.nandaiqbalh.newsapp.data.network.service.ApiServiceNews
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import retrofit2.Response
import javax.inject.Inject

interface NewsRepository {

	suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

}

class NewsRepositoryImpl @Inject constructor(
	private val newsRemoteDataSource: NewsRemoteDataSource,
	private val apiServiceNews: ApiServiceNews
) : NewsRepository {

	override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> =
		newsRemoteDataSource.getBreakingNews(countryCode, pageNumber)


}