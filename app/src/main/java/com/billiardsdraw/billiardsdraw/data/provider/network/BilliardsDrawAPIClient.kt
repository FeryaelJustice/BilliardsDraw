package com.billiardsdraw.billiardsdraw.data.provider.network

import com.billiardsdraw.billiardsdraw.data.model.ApiResponse
import com.billiardsdraw.billiardsdraw.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BilliardsDrawAPIClient {
    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )
    @GET("/")
    suspend fun get(@Query("apiKey") apiKey: String): Response<ApiResponse>

    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )
    @GET("/users")
    suspend fun getUsers(@Query("apiKey") apiKey: String): Response<List<User>>
}