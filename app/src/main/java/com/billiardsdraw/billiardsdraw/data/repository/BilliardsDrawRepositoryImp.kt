package com.billiardsdraw.billiardsdraw.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.billiardsdraw.billiardsdraw.data.model.network.NetworkResponse
import com.billiardsdraw.billiardsdraw.data.model.User
import com.billiardsdraw.billiardsdraw.data.provider.local.UserDao
import com.billiardsdraw.billiardsdraw.data.provider.network.api.BilliardsDrawAPIService
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseAuthenticator
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseFirestoreHelper
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import java.util.*
import javax.inject.Inject

/**
 * GENERAL API PURPOSES
 */

class BilliardsDrawRepositoryImp @Inject constructor(
    private val api: BilliardsDrawAPIService,
    private val userDao: UserDao,
    private val authenticator: BaseFirebaseAuthenticator,
    private val firebaseFirestoreHelper: BaseFirebaseFirestoreHelper,
    private val appPreferences: SharedPreferences,
    private val gson: Gson
) :
    BilliardsDrawRepository {

    // LOCAL
    override suspend fun getUsersFromLocalDB(): List<User> = userDao.getUsers()

    // API
    override suspend fun getUserFromApi(): NetworkResponse<Any> {
        val responseUser = api.getUser()
        var response: NetworkResponse<Any> = NetworkResponse.Success("", null)
        if (responseUser.username == "") {
            response = NetworkResponse.Error("Error: No user found", "", null)
        } else {
            response.user = responseUser.toUser()
        }
        return response
    }

    // FIREBASE
    override suspend fun signInWithEmailPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            authenticator.signInWithEmailPassword(email, password)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun signUpWithEmailPassword(
        email: String,
        password: String
    ): FirebaseUser? {
        return try {
            authenticator.signUpWithEmailPassword(email, password)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun createUserInFirebaseFirestore(
        user: MutableMap<String, Any>,
        callback: (Boolean) -> Unit
    ) {
        firebaseFirestoreHelper.createUserInFirebaseFirestore(user) {
            callback(it)
        }
    }

    override fun signOut(): FirebaseUser? = authenticator.signOut()
    override fun getCurrentUser(): FirebaseUser? = authenticator.getUser()
    override suspend fun sendResetPassword(email: String): Boolean {
        return try {
            authenticator.sendPasswordReset(email)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getUserFromFirebaseFirestore(
        userId: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    ) {
        firebaseFirestoreHelper.getUser("users", userId) { user ->
            callback(user)
        }
    }
}