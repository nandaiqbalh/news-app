package com.nandaiqbalh.newsapp.di

import com.nandaiqbalh.newsapp.BuildConfig
import com.nandaiqbalh.newsapp.data.network.service.ApiServiceNews
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

	@Singleton
	@Provides
	fun provideRetrofit(): Retrofit {
		val loggingInterceptor = HttpLoggingInterceptor()
			.setLevel(HttpLoggingInterceptor.Level.BODY)

		val client = OkHttpClient.Builder()
			.addInterceptor(loggingInterceptor)
			.addInterceptor { chain ->
				val url = chain
					.request()
					.url
					.newBuilder()
					.addQueryParameter("apiKey", BuildConfig.API_KEY)
					.build()
				chain.proceed(chain.request().newBuilder().url(url).build())
			}
			.build()

		return Retrofit.Builder()
			.baseUrl(BuildConfig.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()
	}

	@Singleton
	@Provides
	fun provideApiServiceCity(retrofit: Retrofit): ApiServiceNews =
		retrofit.create(ApiServiceNews::class.java)


}