package com.billiardsdraw.billiardsdraw.data.provider.network.billiardsdrawapi

import com.billiardsdraw.billiardsdraw.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface BilliardsDrawAPIClient {
    @GET("/")
    suspend fun get(): Response<List<User>>
}