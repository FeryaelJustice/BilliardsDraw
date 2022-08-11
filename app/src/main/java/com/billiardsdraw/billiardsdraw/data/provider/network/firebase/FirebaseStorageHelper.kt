package com.billiardsdraw.billiardsdraw.data.provider.network.firebase

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import coil.Coil
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.File

class FirebaseStorageHelper : BaseFirebaseStorageHelper {
    override suspend fun getUserProfilePicture(userId: String, callback: (File) -> Unit): Uri? {
        val storageRef = Firebase.storage.reference
        val profilePictureReference = storageRef.child("users").child("profile_pictures/$userId")

        // With byteArray
        /*
        var byteArrayPic: ByteArray? = null
        val tenMegaBytes: Long = 1024 * 1024 * 100 // 100MB profile pic maximum
        profilePictureReference.getBytes(tenMegaBytes).addOnSuccessListener {
            byteArrayPic = it
        }
        return byteArrayPic
        */

        // With temp file
        var uri: Uri? = null
        val localFile = File.createTempFile("profile_picture_user_$userId", "jpg")
        localFile.deleteOnExit()
        profilePictureReference.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
            Log.d("profile_picture", "Profile picture downloaded")
            uri = Uri.fromFile(localFile)
        }.addOnFailureListener {
            // Handle any errors
            Log.d("profile_picture", "Error downloading profile picture")
        }.await()
        Log.d("profile_picture", "Profile picture loaded: $uri")
        callback(localFile)
        return uri
    }

    override suspend fun uploadUserProfilePicture(userId: String, data: Uri?): Boolean {
        var success = false
        val storageRef = Firebase.storage.reference
        val profilePicturesReference = storageRef.child("users").child("profile_pictures/$userId")

        data?.let { uri ->
            profilePicturesReference.putFile(uri).addOnSuccessListener {
                success = true
            }.await()
        }

        return success
    }
}