package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

interface BaseFirebaseFirestoreHelper {
    suspend fun getUser(
        id: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    )

    suspend fun createUserInFirebaseFirestore(
        userId: String,
        user: MutableMap<String, Any>
    ): Boolean

    suspend fun updateUserInFirebaseFirestore(
        userId: String,
        data: MutableMap<String, Any>
    ): Boolean
}