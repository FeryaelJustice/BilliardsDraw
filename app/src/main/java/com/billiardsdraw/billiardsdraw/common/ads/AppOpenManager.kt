package com.billiardsdraw.billiardsdraw.common.ads

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.billiardsdraw.BilliardsDrawApp
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.*


// ANUNCIOS DE INICIO APP
/** Prefetches App Open Ads.  */
class AppOpenManager(myApplication: BilliardsDrawApp) : Application.ActivityLifecycleCallbacks, LifecycleObserver {
    private var appOpenAd: AppOpenAd? = null
    private var loadCallback: AppOpenAdLoadCallback? = null
    private val myApplication: BilliardsDrawApp
    private var currentActivity: Activity? = null
    private var loadTime: Long = 0

    companion object {
        private const val LOG_TAG = "AppOpenManager"
        private const val AD_UNIT_ID = TEST_AD_CODE_APPLOAD
        private var isShowingAd = false
    }

    /** Constructor  */
    init {
        this.myApplication = myApplication
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().lifecycle.addObserver(this);
    }

    /** Creates and returns ad request.  */
    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()

    /** Utility method that checks if ad exists and can be shown.  */
    private fun isAdAvailable(): Boolean = appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4)

    /** Request an ad  */
    fun fetchAd() {
        if (isAdAvailable()) {
            return;
        }
        loadCallback = object : AppOpenAdLoadCallback() {
            /**
             * Called when an app open ad has loaded.
             *
             * @param ad the loaded app open ad.
             */
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                loadTime = Date().time
            }

            /**
             * Called when an app open ad has failed to load.
             *
             * @param loadAdError the error.
             */
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // Handle the error.
            }
        }
        val request: AdRequest = adRequest
        AppOpenAd.load(
            myApplication, AD_UNIT_ID, request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback as AppOpenAdLoadCallback
        )
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity;
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity;
    }

    override fun onActivityPaused(p0: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivityStopped(p0: Activity) {
        TODO("Not yet implemented")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        TODO("Not yet implemented")
    }

    override fun onActivityDestroyed(p0: Activity) {
        currentActivity = null;
    }

    /** Shows the ad if one isn't already showing.  */
    private fun showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {
            val fullScreenContentCallback: FullScreenContentCallback =
                object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // Set the reference to null so isAdAvailable() returns false.
                        appOpenAd = null
                        isShowingAd = false
                        fetchAd()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {}
                    override fun onAdShowedFullScreenContent() {
                        isShowingAd = true
                    }
                }
            appOpenAd!!.fullScreenContentCallback = fullScreenContentCallback
            appOpenAd!!.show(currentActivity!!)
        } else {
            fetchAd()
        }
    }

    /** Utility method to check if ad was loaded more than n hours ago.  */
    private fun wasLoadTimeLessThanNHoursAgo(numHours: Long): Boolean {
        val dateDifference: Long = Date().time - this.loadTime
        val numMilliSecondsPerHour: Long = 3600000
        return dateDifference < numMilliSecondsPerHour * numHours
    }


    fun onStart() {
        showAdIfAvailable()
    }
}