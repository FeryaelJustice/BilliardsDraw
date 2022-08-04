package com.billiardsdraw.billiardsdraw.common.ads

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

// CONSTANTS
const val TEST_AD_CODE_APPLOAD = "ca-app-pub-3940256099942544/3419835294"
const val TEST_AD_CODE_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
const val TEST_AD_CODE_BANNER = "ca-app-pub-3940256099942544/6300978111"
const val APPLOAD_AD_CODE = "ca-app-pub-8873908976357821/6385864544"
const val INTERSTITIAL_AD_CODE_LAUNCH = "ca-app-pub-8873908976357821/2958573660"
const val BANNER_AD_CODE_LAUNCH = "ca-app-pub-8873908976357821/6221378862"

// VARIABLES
var mInterstitialAd: InterstitialAd? = null
var enableAds: Boolean = false

// METHODS
fun createInterstitialAd(context: Context) {
    // Interstitial ad
    val adRequest = AdRequest.Builder().build()
    InterstitialAd.load(
        context,
        TEST_AD_CODE_INTERSTITIAL, // reemplazar por interstitial code launch ad en prod
        adRequest,
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                mInterstitialAd = ad
                mInterstitialAd?.show(context as Activity)
            }
        })
}

// COMPOSABLE
@Composable
fun CreateBanner(context: Context) {
    AndroidView(factory = {
        AdView(it).apply {
            setAdSize(AdSize.BANNER)
            adUnitId = TEST_AD_CODE_BANNER
            loadAd(AdRequest.Builder().build())
        }
    })
}