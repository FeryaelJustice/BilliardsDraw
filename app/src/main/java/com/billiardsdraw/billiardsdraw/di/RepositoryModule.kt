package com.billiardsdraw.billiardsdraw.di

import android.content.SharedPreferences
import com.billiardsdraw.billiardsdraw.data.provider.local.UserDao
import com.billiardsdraw.billiardsdraw.data.provider.network.api.BilliardsDrawAPIService
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseAuthenticator
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseFirestoreHelper
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseStorageHelper
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepositoryImp
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBilliardsDrawRepository(
        billiardsDrawAPIService: BilliardsDrawAPIService,
        userDao: UserDao,
        firebaseAuthenticator : BaseFirebaseAuthenticator,
        firebaseFirestore: BaseFirebaseFirestoreHelper,
        firebaseStorage: BaseFirebaseStorageHelper,
        appPreferences: SharedPreferences,
        gson: Gson
    ): BilliardsDrawRepository =
        BilliardsDrawRepositoryImp(
            billiardsDrawAPIService,
            userDao,
            firebaseAuthenticator,
            firebaseFirestore,
            firebaseStorage,
            appPreferences,
            gson
        )
}