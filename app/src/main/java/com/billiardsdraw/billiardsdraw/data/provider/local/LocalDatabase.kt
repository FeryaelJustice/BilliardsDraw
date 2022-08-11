package com.billiardsdraw.billiardsdraw.data.provider.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.billiardsdraw.billiardsdraw.data.model.user.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): java.util.Date = java.util.Date(value ?: 0)

    @TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long = date?.time ?: 0

    @TypeConverter
    fun fromString(value: String): Array<String> {
        val listType = object : TypeToken<Array<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: Array<String>): String = Gson().toJson(list)
}