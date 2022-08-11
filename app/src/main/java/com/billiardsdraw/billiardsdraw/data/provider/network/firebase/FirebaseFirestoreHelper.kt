package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

import com.billiardsdraw.billiardsdraw.common.FirebaseFirestoreConstants
import com.billiardsdraw.billiardsdraw.data.model.user.User
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FirebaseFirestoreHelper : BaseFirebaseFirestoreHelper {

    override suspend fun createUserInFirebaseFirestore(
        userId: String,
        user: MutableMap<String, Any>,
        callback: (Boolean) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(FirebaseFirestoreConstants.USERS_DIRECTORY)
            .document(userId)
            .set(user)
            .addOnCompleteListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
    }

    override suspend fun updateUserInFirebaseFirestore(
        userId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(FirebaseFirestoreConstants.USERS_DIRECTORY)
            .document(userId).update(data).addOnCompleteListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
    }

    override suspend fun getUser(
        id: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(FirebaseFirestoreConstants.USERS_DIRECTORY)
            .document(id).get()
            .addOnCompleteListener { docSnap ->
                val document = docSnap.result
                val userRetrieved = User(
                    uid = "null",
                    username =
                    document.getString("username")
                        .toString(),
                    nickname =
                    document.getString("nickname")
                        .toString(),
                    name =
                    document.getString("name")
                        .toString(),
                    surnames =
                    document.getString("surnames")
                        .toString(),
                    email = document.getString("email")
                        .toString(),
                    password =
                    document.getString("password")
                        .toString(),
                    age = document.getString("age")
                        ?.toInt() ?: 0,
                    birthdate = Date(),
                    country =
                    document.getString("country")
                        .toString(),
                    carambola_paints = arrayOf(),
                    pool_paints = arrayOf(),
                    role =
                    document.getString("role")
                        .toString(),
                    active = false,
                    deleted = false
                )
                callback(userRetrieved.toUser())
            }
    }
}