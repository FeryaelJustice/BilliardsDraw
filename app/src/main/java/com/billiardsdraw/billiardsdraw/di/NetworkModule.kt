package com.billiardsdraw.billiardsdraw.di

import com.billiardsdraw.billiardsdraw.data.provider.network.BilliardsDrawAPIClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    val BASEURL = "https://google.com"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun provideQuoteApiClient(retrofit: Retrofit): BilliardsDrawAPIClient {
        return retrofit.create(BilliardsDrawAPIClient::class.java)
    }
}