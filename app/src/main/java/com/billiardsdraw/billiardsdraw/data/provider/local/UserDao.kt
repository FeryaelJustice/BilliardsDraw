package com.billiardsdraw.billiardsdraw.data.provider.local

import androidx.room.Dao
import androidx.room.Query
import com.billiardsdraw.billiardsdraw.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>
}