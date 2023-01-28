package com.nandaiqbalh.newsapp.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nandaiqbalh.newsapp.data.network.models.news.Article
import com.nandaiqbalh.newsapp.data.network.models.news.NewsResponse
import com.nandaiqbalh.newsapp.data.repository.NewsRepository
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
	private val newsRepository: NewsRepository
) : ViewModel() {

	val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
	var breakingNewsPage = 1
	private var breakingNewsResponse: NewsResponse? = null

	init {
		getBreakingNews("id")
	}

	fun getBreakingNews(countryCode: String) = viewModelScope.launch {
		breakingNews.postValue(Resource.Loading())

		try {
			val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)

			if (response.isSuccessful) {
				viewModelScope.launch(Dispatchers.Main) {
					breakingNews.postValue(handleBreakingNewsResponse(response))
				}
			} else {
				breakingNews.postValue(Resource.Error(response.message(), null))
			}
		} catch (e: Exception) {
			viewModelScope.launch(Dispatchers.Main) {
				breakingNews.postValue(Resource.Error(e.toString()))
			}
		}

	}

	private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
		if(response.isSuccessful) {
			response.body()?.let { resultResponse ->
				breakingNewsPage++
				if(breakingNewsResponse == null) {
					breakingNewsResponse = resultResponse
				} else {
					val oldArticles = breakingNewsResponse?.articles
					val newArticles = resultResponse.articles
					oldArticles?.addAll(newArticles)
				}
				return Resource.Success(breakingNewsResponse ?: resultResponse)
			}
		}
		return Resource.Error(response.message())
	}

	val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
	var searchNewsPage = 1
	private var searchNewsResponse: NewsResponse? = null

	fun searchNews(searchQuery: String) = viewModelScope.launch {
		searchNews.postValue(Resource.Loading())

		try {
			val response = newsRepository.searchNews(searchQuery, searchNewsPage)

			if (response.isSuccessful) {
				viewModelScope.launch(Dispatchers.Main) {
					searchNews.postValue(handleSearchNewsResponse(response))
				}
			} else {
				searchNews.postValue(Resource.Error(response.message(), null))
			}
		} catch (e: Exception) {
			viewModelScope.launch(Dispatchers.Main) {
				searchNews.postValue(Resource.Error(e.toString()))
			}
		}

	}

	private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse> {
		if(response.isSuccessful) {
			response.body()?.let { resultResponse ->
				searchNewsPage++
				if(searchNewsResponse == null) {
					searchNewsResponse = resultResponse
				} else {
					val oldArticles = searchNewsResponse?.articles
					val newArticles = resultResponse.articles
					oldArticles?.addAll(newArticles)
				}
				return Resource.Success(searchNewsResponse ?: resultResponse)
			}
		}
		return Resource.Error(response.message())
	}

	fun saveArticle(article: Article) = viewModelScope.launch {
		newsRepository.upsert(article)
	}

	fun getSavedNews() = newsRepository.getSavedNews()
	fun deleteArticle(article: Article) = viewModelScope.launch {
		newsRepository.deleteArticle(article)
	}
}