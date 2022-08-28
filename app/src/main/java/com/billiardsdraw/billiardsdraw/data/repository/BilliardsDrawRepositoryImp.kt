package com.billiardsdraw.billiardsdraw.data.repository

import android.content.SharedPreferences
import android.net.Uri
import com.billiardsdraw.billiardsdraw.data.model.network.NetworkResponse
import com.billiardsdraw.billiardsdraw.data.model.user.User
import com.billiardsdraw.billiardsdraw.data.provider.local.UserDao
import com.billiardsdraw.billiardsdraw.data.provider.network.api.BilliardsDrawAPIService
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseAuthenticator
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseFirestoreHelper
import com.billiardsdraw.billiardsdraw.data.provider.network.firebase.BaseFirebaseStorageHelper
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.google.firebase.auth.FirebaseUser
import com.google.gson.Gson
import java.io.File
import javax.inject.Inject

/**
 * GENERAL API PURPOSES
 */

class BilliardsDrawRepositoryImp @Inject constructor(
    private val api: BilliardsDrawAPIService,
    private val userDao: UserDao,
    private val authenticator: BaseFirebaseAuthenticator,
    private val firebaseFirestoreHelper: BaseFirebaseFirestoreHelper,
    private val firebaseStorageHelper: BaseFirebaseStorageHelper,
    private val appPreferences: SharedPreferences,
    private val gson: Gson
) :
    BilliardsDrawRepository {

    // LOCAL
    override fun sharedPreferencesBoolean(key: String): Boolean =
        appPreferences.getBoolean(key, false)

    override fun setSharedPreferencesBoolean(key: String, value: Boolean) {
        appPreferences.edit().putBoolean(key, value).apply()
    }

    override fun sharedPreferencesString(key: String): String =
        appPreferences.getString(key, "").toString()

    override fun setSharedPreferencesString(key: String, value: String) {
        appPreferences.edit().putString(key, value).apply()
    }

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
    // Auth
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

    // Firestore
    override suspend fun createUserInFirebaseFirestore(
        userId: String,
        user: MutableMap<String, Any>
    ): Boolean = firebaseFirestoreHelper.createUserInFirebaseFirestore(userId, user)

    override suspend fun updateUserInFirebaseFirestore(
        userid: String,
        user: MutableMap<String, Any>
    ): Boolean = firebaseFirestoreHelper.updateUserInFirebaseFirestore(userid, user)

    override suspend fun getUserFromFirebaseFirestore(
        userId: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    ) {
        firebaseFirestoreHelper.getUser(userId) { user ->
            callback(user)
        }
    }

    // Storage
    override suspend fun getUserProfilePicture(userId: String, callback: (File) -> Unit): Uri? =
        firebaseStorageHelper.getUserProfilePicture(userId, callback)

    override suspend fun uploadUserProfilePicture(
        userId: String,
        data: Uri?
    ): Boolean = firebaseStorageHelper.uploadUserProfilePicture(userId, data)
}