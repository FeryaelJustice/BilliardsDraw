package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

import com.billiardsdraw.billiardsdraw.data.model.User
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.*

class FirebaseFirestoreHelper : BaseFirebaseFirestoreHelper {

    override suspend fun createUserInFirebaseFirestore(
        user: MutableMap<String, Any>,
        callback: (Boolean) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection("users").add(user)
            .addOnCompleteListener {
                callback(true)
            }.addOnFailureListener {
                callback(false)
            }
    }

    override suspend fun getUser(
        collection: String,
        id: String,
        callback: (com.billiardsdraw.billiardsdraw.domain.model.User) -> Unit
    ) {
        FirebaseFirestore.getInstance().collection(collection).document(id).get()
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
                    age = document.getLong("age")
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