package com.billiardsdraw.billiardsdraw.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.billiardsdraw.billiardsdraw.BuildConfig
import com.google.android.gms.ads.interstitial.InterstitialAd
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

// TYPES
enum class PROVIDER_TYPE {
    BASIC,
    GOOGLE,
    FACEBOOK
}

// CONSTANTS

const val BILLIARDSDRAW_CONTACT_EMAIL = "billiardsdraw@gmail.com"

// METHODS

// Extension functions
fun String.toDate(): Date? = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this)

// Getters
fun buildConfig() = BuildConfig.BUILD_TYPE
fun apiKey() = BuildConfig.API_KEY
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

// Security
fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

// COMPOSABLES

@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

// OBJECT UTILS

// Maps hex color string code to jetpack compose graphics Color object
object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#$colorString"))
    }
}

// Object constants
object SharedPrefConstants {
    const val LOCAL_SHARED_PREF_SETTINGS = "local_shared_pref_settings"
    const val USER_ID_KEY = "user_id"
    const val IS_LOGGED_KEY = "isLogged"
    const val EMAIL_KEY = "email"
    const val PASSWORD_KEY = "password"
}

object RoomConstants {
    const val LOCAL_ROOM = "billiardsdraw_db"
}

object FirebaseFirestoreConstants {
    const val USERS_DIRECTORY = "users"
}

object FirebaseStorageConstants {
    const val USERS = "users"
    const val USERS_IMAGES = "profile_images"
}