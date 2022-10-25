package com.billiardsdraw.billiardsdraw.common

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.billiardsdraw.billiardsdraw.BuildConfig
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

// SYSTEM
data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class WindowType {
        object Compact : WindowType()
        object Medium : WindowType()
        object Expanded : WindowType()
    }
}

// For screen size responsiveness
@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenHeightDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidth = configuration.screenWidthDp.dp,
        screenHeight = configuration.screenHeightDp.dp
    )
}

// CONSTANTS

const val BILLIARDSDRAW_CONTACT_EMAIL = "billiardsdraw@gmail.com"

// METHODS

// Extension functions
fun String.toDate(): Date? = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(this)
fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

// convert an object of type I to type O
inline fun <I, reified O> I.convert(): O {
    val gson = Gson()
    val json = gson.toJson(this)
    return gson.fromJson(json, object : TypeToken<O>() {}.type)
}

// convert a data class to a map
fun <T> T.serializeToMap(): MutableMap<String, Any> {
    return convert()
}

// convert a map to a data class
inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

// Getters
fun buildConfig() = BuildConfig.BUILD_TYPE
fun buildConfigFlavor() = BuildConfig.FLAVOR
fun apiKey() = BuildConfig.API_KEY
fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val filename = "IMG_${System.currentTimeMillis()}.jpg"
        var fos: OutputStream?
        var imageUri: Uri?
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.Video.Media.IS_PENDING, 1)
        }
        //use application context to get contentResolver
        val contentResolver = context.contentResolver
        contentResolver.also { resolver ->
            imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
        fos?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        contentValues.clear()
        contentValues.put(MediaStore.Video.Media.IS_PENDING, 0)
        imageUri?.let { contentResolver.update(it, contentValues, null, null) }

        return imageUri
    } else {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path.toString())
    }

}

// Play Billing
fun initGooglePlayBilling(context: Context) {
    val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            // To be implemented in a later section.
            Log.d("billingresultcode", billingResult.responseCode.toString())
            Log.d("purchasessize", purchases?.size.toString())
        }
    val billingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases()
        .build()
    billingClient.startConnection(object : BillingClientStateListener {
        override fun onBillingServiceDisconnected() {
            Log.d("billingresultcode", "Disconnected")
        }

        override fun onBillingSetupFinished(billingResult: BillingResult) {
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                Log.d("billingresultcode", billingResult.responseCode.toString())
            }
        }
    })
    val queryProductDetailsParams =
        QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("product_id_example")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                )
            )
            .build()
    billingClient.queryProductDetailsAsync(
        queryProductDetailsParams
    ) { billingResult, productDetailsList ->
        // check billingResult
        Log.d("billingResult", billingResult.responseCode.toString())
        // process returned productDetailsList
        Log.d("productDetailsList", productDetailsList.size.toString())
    }
}

// Security
fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
}

// COMPOSABLE

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
    const val GOOGLE_AUTH_FIREBASE_ID_KEY = "googleAuthFirebaseIdKey"
    const val IS_LOGGED_KEY = "isLogged"
    const val KEEP_SESSION_KEY = "keepSession"
    const val EMAIL_KEY = "email"
    const val PASSWORD_KEY = "password"
    const val SIGN_IN_METHOD_KEY = "signInMethod"
}

object RoomConstants {
    const val LOCAL_ROOM = "billiardsdraw_db"
}

object FirebaseFirestoreConstants {
    const val USERS_DIRECTORY = "users"
}

object FirebaseStorageConstants {
    const val USERS = "users"
    const val USERS_PROFILE_PICTURES = "profile_pictures"
}