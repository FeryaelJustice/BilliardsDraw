package com.billiardsdraw

import android.app.Application
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.lifecycle.OnLifecycleEvent
import com.billiardsdraw.billiardsdraw.common.ads.AppOpenManager
import com.billiardsdraw.billiardsdraw.common.ads.enableAds
import com.billiardsdraw.billiardsdraw.common.buildConfig
import com.google.android.gms.ads.MobileAds
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BilliardsDrawApp : Application() {
    // lateinit var appOpenManager: AppOpenManager
    override fun onCreate() {
        super.onCreate()
        // Check build version
        enableAds = buildConfig() == "debug" // cambiar a release al subirlo a tienda en prod

        // ADS
        if (enableAds) {
            // Si hay que obtener consentimiento para mostrar anuncios, hacerlo antes del initialize
            MobileAds.initialize(this)
            // appOpenManager = AppOpenManager(this)
        }

        // Firebase
        Firebase.initialize(this)
    }
}