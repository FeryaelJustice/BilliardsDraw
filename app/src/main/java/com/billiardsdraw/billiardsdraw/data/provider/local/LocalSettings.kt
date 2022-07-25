package com.billiardsdraw.billiardsdraw.data.provider.local

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalSettings @Inject constructor() {

    // SHARED PREFS
    companion object {
        const val SETTINGS_KEY = "settings"
    }

    @Singleton
    @Provides
    fun provideSharedPreferences (@ApplicationContext context: Context) = context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)
}