package com.billiardsdraw.billiardsdraw.ui.util

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.billiardsdraw.billiardsdraw.common.TEST_AD_CODE_BANNER
import com.billiardsdraw.billiardsdraw.common.TEST_AD_CODE_INTERSTITIAL
import com.billiardsdraw.billiardsdraw.common.mInterstitialAd
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

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