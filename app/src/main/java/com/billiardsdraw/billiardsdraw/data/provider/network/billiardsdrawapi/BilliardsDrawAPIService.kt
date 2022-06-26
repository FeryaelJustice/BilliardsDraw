package com.billiardsdraw.billiardsdraw.data.provider.network.billiardsdrawapi

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BilliardsDrawAPIService (private val api: BilliardsDrawAPIClient) {
    suspend fun get(): Any {
        return withContext(Dispatchers.IO){
            val response = api.get()
            response.body() ?: emptyList()
        }
    }
}