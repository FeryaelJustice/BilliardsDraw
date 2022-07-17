package com.billiardsdraw.billiardsdraw.data.provider.network

import com.billiardsdraw.billiardsdraw.data.model.ApiResponse
import com.billiardsdraw.billiardsdraw.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface BilliardsDrawAPIClient {
    @GET("/")
    suspend fun get(): Response<ApiResponse>
    @GET("/users")
    suspend fun getUsers(): Response<List<User>>
}