package com.billiardsdraw.billiardsdraw.di

import android.content.Context
import androidx.room.Room
import com.billiardsdraw.billiardsdraw.data.provider.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,LocalDatabase::class.java,"billiardsdraw_db").build()

    @Singleton
    @Provides
    fun provideUserDao(db: LocalDatabase) = db.getUserDao()
}