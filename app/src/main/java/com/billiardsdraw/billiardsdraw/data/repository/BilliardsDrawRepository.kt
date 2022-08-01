package com.billiardsdraw.billiardsdraw.data.repository

import com.billiardsdraw.billiardsdraw.data.model.network.NetworkResponse
import com.billiardsdraw.billiardsdraw.data.model.User
import com.google.firebase.auth.FirebaseUser

interface BilliardsDrawRepository {
    // LOCAL
    fun sharedPreferencesBoolean(key:String): Boolean
    fun setSharedPreferencesBoolean(key:String, value: Boolean)
    suspend fun getUsersFromLocalDB(): List<User>

    // API
    suspend fun getUserFromApi(): NetworkResponse<Any>

    // FIREBASE
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    fun signOut(): FirebaseUser?
    fun getCurrentUser(): FirebaseUser?
    suspend fun sendResetPassword(email: String): Boolean
    suspend fun createUserInFirebaseFirestore(
        user: MutableMap<String, Any>,
        callback: (Boolean) -> Unit
    )

    suspend fun getUserFromFirebaseFirestore(
        userId: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    )
}