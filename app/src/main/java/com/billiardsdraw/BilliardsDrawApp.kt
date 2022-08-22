package com.billiardsdraw

import android.app.Application
import androidx.core.content.PackageManagerCompat.LOG_TAG
import com.billiardsdraw.billiardsdraw.common.ads.AppOpenManager
import com.billiardsdraw.billiardsdraw.common.ads.enableAds
import com.billiardsdraw.billiardsdraw.common.buildConfig
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BilliardsDrawApp : Application() {
    // late init var appOpenManager: AppOpenManager
    override fun onCreate() {
        super.onCreate()
        // Check build version
        enableAds = buildConfig() == "debug" // change to release on uploading to store in prod

        // ADS
        if (enableAds) {
            // If we need to obtain consent for showing show ads, do it before initialize
            MobileAds.initialize(this)
            // appOpenManager = AppOpenManager(this)
        }

        // Facebook
        AppEventsLogger.activateApp(this)

        // Firebase
        Firebase.initialize(this)
    }

}