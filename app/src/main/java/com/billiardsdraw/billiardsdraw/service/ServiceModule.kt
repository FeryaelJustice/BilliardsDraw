package com.billiardsdraw.billiardsdraw.service

import com.billiardsdraw.billiardsdraw.data.provider.local.LocalDatabase
import com.billiardsdraw.billiardsdraw.data.provider.local.LocalDatabase_Impl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/*
@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideStorageService(impl: LocalDatabase_Impl): LocalDatabase
}
*/