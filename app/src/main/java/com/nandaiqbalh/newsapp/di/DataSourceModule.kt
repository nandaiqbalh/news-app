package com.nandaiqbalh.newsapp.di

import com.nandaiqbalh.newsapp.data.network.datasource.NewsRemoteDataSource
import com.nandaiqbalh.newsapp.data.network.datasource.NewsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

	@Binds
	abstract fun provideNewsRemoteDataSource(newsRemoteDataSource: NewsRemoteDataSourceImpl): NewsRemoteDataSource

}