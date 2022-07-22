package com.billiardsdraw.billiardsdraw.common

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.billiardsdraw.billiardsdraw.BuildConfig

const val TEST_AD_CODE = "ca-app-pub-3940256099942544/6300978111"
const val INTERSTITIAL_AD_CODE = "a-app-pub-8873908976357821/7203477994"
const val BANNER_AD_CODE = "ca-app-pub-8873908976357821/7746403479"

fun buildConfig() = BuildConfig.BUILD_TYPE

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

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

// Maps hex color string code to jetpack compose graphics Color object
object HexToJetpackColor {
    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor("#$colorString"))
    }
}