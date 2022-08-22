package com.billiardsdraw.billiardsdraw.di

import com.billiardsdraw.billiardsdraw.data.provider.network.api.BilliardsDrawAPIClient
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.*
import com.google.firebase.firestore.FirebaseFirestore
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

    // OWN API
    @Singleton
    const val BASEURL = "https://google.com"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): BilliardsDrawAPIClient {
        return retrofit.create(BilliardsDrawAPIClient::class.java)
    }

    // FIREBASE
    @Singleton
    @Provides
    fun provideAuthenticator(): BaseFirebaseAuthenticator = FirebaseAuthenticator()

    @Singleton
    @Provides
    fun provideFirestoreHelper(): BaseFirebaseFirestoreHelper = FirebaseFirestoreHelper()

    @Singleton
    @Provides
    fun provideFirebaseStorageHelper(): BaseFirebaseStorageHelper = FirebaseStorageHelper()

    /*
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()
    */

    /*
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
    */

}