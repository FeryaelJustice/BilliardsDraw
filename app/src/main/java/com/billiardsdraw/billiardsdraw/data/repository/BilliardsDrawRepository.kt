package com.billiardsdraw.billiardsdraw.data.repository

import android.net.Uri
import com.billiardsdraw.billiardsdraw.data.model.network.NetworkResponse
import com.billiardsdraw.billiardsdraw.data.model.user.User
import com.google.firebase.auth.FirebaseUser
import java.io.File

interface BilliardsDrawRepository {

    // LOCAL
    fun sharedPreferencesBoolean(key: String): Boolean
    fun setSharedPreferencesBoolean(key: String, value: Boolean)
    fun sharedPreferencesString(key: String): String
    fun setSharedPreferencesString(key: String, value: String)
    suspend fun getUsersFromLocalDB(): List<User>

    // API
    suspend fun getUserFromApi(): NetworkResponse<Any>

    // FIREBASE
    // Auth
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    fun signOut(): FirebaseUser?
    fun getCurrentUser(): FirebaseUser?
    suspend fun sendResetPassword(email: String): Boolean

    // Firestore
    suspend fun createUserInFirebaseFirestore(
        userId: String,
        user: MutableMap<String, Any>
    ): Boolean

    suspend fun updateUserInFirebaseFirestore(
        userid: String,
        user: MutableMap<String, Any>
    ): Boolean

    suspend fun getUserFromFirebaseFirestore(
        userId: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    )

    // Storage
    suspend fun getUserProfilePicture(userId: String, callback: (File) -> Unit): Uri?
    suspend fun uploadUserProfilePicture(userId: String, data: Uri?): Boolean
}