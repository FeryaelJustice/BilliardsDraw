package com.billiardsdraw.billiardsdraw.data.provider.network

import com.billiardsdraw.billiardsdraw.data.model.ApiResponse
import com.billiardsdraw.billiardsdraw.data.model.HttpResponseCode
import com.billiardsdraw.billiardsdraw.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BilliardsDrawAPIService @Inject constructor(private val api: BilliardsDrawAPIClient) {
    suspend fun get(): ApiResponse {
        return withContext(Dispatchers.IO){
            val response = api.get()
            response.body() ?: ApiResponse(0, listOf(),HttpResponseCode.ERROR_SERVER)
        }
    }
    suspend fun getUsers(): List<User> {
        return withContext(Dispatchers.IO){
            val response = api.getUsers()
            response.body() ?: emptyList()
        }
    }
}