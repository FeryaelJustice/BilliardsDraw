package com.billiardsdraw.billiardsdraw.data.provider.network.api

import com.billiardsdraw.billiardsdraw.data.model.user.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface BilliardsDrawAPIClient {
    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )
    @GET("/user")
    suspend fun getUser(@Query("apiKey") apiKey: String): Response<User>
}