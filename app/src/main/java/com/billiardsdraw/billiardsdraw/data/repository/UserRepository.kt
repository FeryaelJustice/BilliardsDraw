package com.billiardsdraw.billiardsdraw.data.repository

import com.billiardsdraw.billiardsdraw.data.model.User
import com.billiardsdraw.billiardsdraw.data.provider.local.UserDao
import com.billiardsdraw.billiardsdraw.data.provider.network.BilliardsDrawAPIService
import javax.inject.Inject

/***
 * USERS API
 */

class UserRepository @Inject constructor(private val api: BilliardsDrawAPIService, private val userDao: UserDao) {
    suspend fun getUsers(): List<User> = api.getUsers()
    suspend fun getUsersFromDB() = userDao.getUsers()
}