package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

interface BaseFirebaseFirestoreHelper {
    suspend fun getUser(
        id: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    )

    suspend fun createUserInFirebaseFirestore(
        user: MutableMap<String, Any>,
        callback: (Boolean) -> Unit
    )
}