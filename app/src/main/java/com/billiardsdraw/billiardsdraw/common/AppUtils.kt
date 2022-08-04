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

// VARIABLES
var mInterstitialAd: InterstitialAd? = null
var enableAds: Boolean = false

// CONSTANTS

const val BILLIARDSDRAW_CONTACT_EMAIL = "billiardsdraw@gmail.com"
const val TEST_AD_CODE_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
const val TEST_AD_CODE_BANNER= "ca-app-pub-3940256099942544/6300978111"
const val INTERSTITIAL_AD_CODE_LAUNCH = "ca-app-pub-8873908976357821/2958573660"
const val BANNER_AD_CODE_LAUNCH = "ca-app-pub-8873908976357821/6221378862"

// METHODS

// Getters
fun buildConfig() = BuildConfig.BUILD_TYPE
fun apiKey() = BuildConfig.API_KEY
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

// Security
fun md5(input:String): String {
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
    const val IS_LOGGED_KEY = "isLogged"
}
object RoomConstants {
    const val LOCAL_ROOM = "billiardsdraw_db"
}
object FirebaseFirestoreConstants{
    const val USERS_DIRECTORY = "users"
}
object FirebaseStorageConstants {
    const val USERS = "users"
    const val USERS_IMAGES = "profile_images"
}