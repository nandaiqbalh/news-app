package com.nandaiqbalh.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.nandaiqbalh.newsapp.data.local.database.ArticleDatabase
import com.nandaiqbalh.newsapp.data.network.datasource.NewsRemoteDataSource
import com.nandaiqbalh.newsapp.data.network.models.news.Article
import com.nandaiqbalh.newsapp.data.network.models.news.NewsResponse
import retrofit2.Response
import javax.inject.Inject

interface NewsRepository {

	suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse>

	suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse>

	suspend fun upsert(article: Article)

	fun getSavedNews() :LiveData<List<Article>>

	suspend fun deleteArticle(article: Article)
}

class NewsRepositoryImpl @Inject constructor(
	private val newsRemoteDataSource: NewsRemoteDataSource,
	private val articleDatabase: ArticleDatabase,
) : NewsRepository {

	override suspend fun getBreakingNews(countryCode: String, pageNumber: Int): Response<NewsResponse> =
		newsRemoteDataSource.getBreakingNews(countryCode, pageNumber)

	override suspend fun searchNews(query: String, pageNumber: Int): Response<NewsResponse> {
		return newsRemoteDataSource.searchNews(query, pageNumber)
	}

	override suspend fun upsert(article: Article) {
		articleDatabase.getArticleDao().upsert(article)
	}

	override fun getSavedNews(): LiveData<List<Article>> = articleDatabase.getArticleDao().getAllArticles()

	override suspend fun deleteArticle(article: Article) {
		articleDatabase.getArticleDao().deleteArticle(article)
	}
}