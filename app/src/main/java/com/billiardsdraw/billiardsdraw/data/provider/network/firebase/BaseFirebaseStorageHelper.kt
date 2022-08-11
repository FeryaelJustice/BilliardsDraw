package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

import android.net.Uri
import java.io.File

interface BaseFirebaseStorageHelper {
    suspend fun getUserProfilePicture(userId: String, callback: (File) -> Unit): Uri?
    suspend fun uploadUserProfilePicture(userId: String, data: Uri?): Boolean
}