package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

import com.google.firebase.auth.FirebaseUser

interface BaseFirebaseAuthenticator {
    suspend fun signUpWithEmailPassword(email: String, password: String): FirebaseUser?
    suspend fun signInWithEmailPassword(email: String, password: String): FirebaseUser?
    fun signOut(): FirebaseUser?
    fun getUser(): FirebaseUser?
    suspend fun sendPasswordReset(email: String)
}