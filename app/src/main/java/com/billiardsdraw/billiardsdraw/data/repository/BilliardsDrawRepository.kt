package com.billiardsdraw.billiardsdraw.data.repository

import com.billiardsdraw.billiardsdraw.data.model.ApiResponse
import com.billiardsdraw.billiardsdraw.data.provider.network.BilliardsDrawAPIService
import javax.inject.Inject

class BilliardsDrawRepository @Inject constructor(private val api: BilliardsDrawAPIService) {
    suspend fun get(): ApiResponse = api.get()
}