package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

import com.google.firebase.auth.FirebaseUser

interface BaseFirebaseFirestoreHelper {
    suspend fun getUser(
        collection: String,
        id: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    )

    suspend fun createUserInFirebaseFirestore(
        user: MutableMap<String, Any>,
        callback: (Boolean) -> Unit
    )
}