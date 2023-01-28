package com.nandaiqbalh.newsapp.di

import android.content.Context
import com.nandaiqbalh.newsapp.data.local.database.ArticleDatabase
import com.nandaiqbalh.newsapp.data.local.database.dao.ArticleDao
import com.nandaiqbalh.newsapp.data.local.datastore.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager =
        DataStoreManager(context)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ArticleDatabase =
        ArticleDatabase(context)

    @Provides
    fun provideArticleDao(@ApplicationContext context: Context): ArticleDao {
        return provideAppDatabase(context).getArticleDao()
    }

}