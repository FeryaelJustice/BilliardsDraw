package com.billiardsdraw.billiardsdraw.data.provider.local

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSettings @Inject constructor(context: Context) {
    companion object {
        const val SETTINGS_KEY = "settings"
    }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SETTINGS_KEY, Context.MODE_PRIVATE)

    // GET
    fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    // SET
    fun setBoolean(key: String, value: Boolean) = sharedPreferences.edit().putBoolean(key, value).commit()

    fun clear() = sharedPreferences.edit().clear().commit()
}