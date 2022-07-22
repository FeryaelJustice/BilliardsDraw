package com.billiardsdraw.billiardsdraw

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.size
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.billingclient.api.*
import com.billiardsdraw.billiardsdraw.common.APP_API_KEY
import com.billiardsdraw.billiardsdraw.ui.navigation.BilliardsDrawTopBar
import com.billiardsdraw.billiardsdraw.ui.navigation.NavigationManager
import com.billiardsdraw.billiardsdraw.ui.theme.BilliardsDrawTheme
import com.billiardsdraw.billiardsdraw.ui.util.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class BilliardsDraw : ComponentActivity() {

    private val model: BilliardsDrawViewModel by viewModels()
    private var enableAds: Boolean = false
    var mInterstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("api_key", APP_API_KEY)

        // Check build version
        val buildConfig = buildConfig()
        enableAds = buildConfig == "debug" // cambiar a release al subirlo a tienda en prod

        if (enableAds) {
            // ADS
            // Si hay que obtener consentimiento para mostrar anuncios, hacerlo antes del initialize
            MobileAds.initialize(this) {}

            Log.d("a", BANNER_AD_CODE) // Luego quitar
            Log.d("b", INTERSTITIAL_AD_CODE) // Luego quitar

            // Interstitial ad
            val adRequest = AdRequest.Builder().build()
            InterstitialAd.load(
                this,
                TEST_AD_CODE, // reemplazar por interstitial code en prod
                adRequest,
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(p0: LoadAdError) {
                        mInterstitialAd = null
                    }

                    override fun onAdLoaded(ad: InterstitialAd) {
                        mInterstitialAd = ad
                        mInterstitialAd?.show(this@BilliardsDraw)
                    }
                })
        }

        // APP
        setContent {
            val navController = rememberNavController()
            BilliardsDrawTheme {
                BilliardsDrawApp(model, navController)
            }
        }
    }

    @Composable
    fun BilliardsDrawApp(model: BilliardsDrawViewModel, navController: NavHostController) {
        // This locks orientation in all app, to lock individually just put this line in each screen composable
        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // App
        val context = LocalContext.current

        // Google Play Services (pay)
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
                TODO("Not yet implemented")

            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    TODO("Not yet implemented")
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
            // process returned productDetailsList
        }

        // Billiards Draw App UI Structure (here starts the UI)
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                BilliardsDrawTopBar(
                    viewModel = model,
                    navController = navController
                )
            }, bottomBar = {
                // Force a crash
                Button(onClick = { throw RuntimeException("Test Crash") }) {
                    Text(text = "Crash")
                }
                // Banner ad (Provoca crash)
                /*
                if (enableAds) {
                    AndroidView(factory = {
                        AdView(it).apply {
                            adSize = AdSize.BANNER
                            adUnitId = BANNER_AD_CODE
                            adListener = object : AdListener() {
                                override fun onAdClicked() {
                                    super.onAdClicked()
                                }

                                override fun onAdClosed() {
                                    super.onAdClosed()
                                }

                                override fun onAdFailedToLoad(p0: LoadAdError) {
                                    super.onAdFailedToLoad(p0)
                                }

                                override fun onAdImpression() {
                                    super.onAdImpression()
                                }

                                override fun onAdLoaded() {
                                    super.onAdLoaded()
                                }

                                override fun onAdOpened() {
                                    super.onAdOpened()
                                }
                            }
                            loadAd(AdRequest.Builder().build())
                        }
                    })
                }
                */
            }, content =
            { innerPadding ->
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationManager(viewModel = model, navController = navController)
                }
            })
    }
}