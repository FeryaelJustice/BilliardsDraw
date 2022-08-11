package com.billiardsdraw.billiardsdraw.data.provider.network.api

import com.billiardsdraw.billiardsdraw.common.apiKey
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.model.user.User
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class BilliardsDrawAPIService @Inject constructor(
    private val api: BilliardsDrawAPIClient,
    private val dispatchers: DispatcherProvider
) {
    suspend fun getUser(): User {
        return withContext(dispatchers.io) {
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