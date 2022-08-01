package com.billiardsdraw.billiardsdraw.data.provider.network.api

import com.billiardsdraw.billiardsdraw.common.apiKey
import com.billiardsdraw.billiardsdraw.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class BilliardsDrawAPIService @Inject constructor(private val api: BilliardsDrawAPIClient) {
    suspend fun getUser(): User {
        return withContext(Dispatchers.IO) {
            val response = api.getUser(apiKey())
            response.body() ?: User(
                0,
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                0,
                Date(),
                "",
                arrayOf(),
                arrayOf(),
                "",
                active = false,
                deleted = false
            )
        }
    }
}