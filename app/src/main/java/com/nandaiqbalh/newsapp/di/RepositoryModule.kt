package com.nandaiqbalh.newsapp.di

import com.nandaiqbalh.newsapp.data.repository.NewsRepository
import com.nandaiqbalh.newsapp.data.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindsNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}