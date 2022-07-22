package com.billiardsdraw.billiardsdraw.data.provider.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.billiardsdraw.billiardsdraw.data.model.User
import com.billiardsdraw.billiardsdraw.data.model.UserDao

@Database(entities = [User::class], version = 1, exportSchema = true)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}