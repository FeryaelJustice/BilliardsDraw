package com.billiardsdraw.billiardsdraw.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import coil.ImageLoader
import com.billiardsdraw.billiardsdraw.common.RoomConstants
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.coroutine.StandardDispatchers
import com.billiardsdraw.billiardsdraw.data.provider.local.LocalDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // ViewModelComponent::class if we want to attach lifecycle to sub nav graph with each view model
object AppModule {

    // STORAGE
    @Provides
    @Singleton // @ViewModelScoped (NEED ViewModelComponent InstallIn in object)
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, LocalDatabase::class.java, RoomConstants.LOCAL_ROOM).build()

    @Provides
    @Singleton
    fun provideUserDao(db: LocalDatabase) = db.getUserDao()

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            SharedPrefConstants.LOCAL_SHARED_PREF_SETTINGS,
            Context.MODE_PRIVATE
        )

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    // DISPATCHERS (Coroutines)
    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = StandardDispatchers()

    // Coil image loader
    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context): ImageLoader = ImageLoader(context)
}