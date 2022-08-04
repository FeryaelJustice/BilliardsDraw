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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.android.billingclient.api.*
import com.billiardsdraw.billiardsdraw.common.*
import com.billiardsdraw.billiardsdraw.ui.navigation.BilliardsDrawTopBar
import com.billiardsdraw.billiardsdraw.ui.navigation.NavigationManager
import com.billiardsdraw.billiardsdraw.ui.theme.BilliardsDrawTheme
import com.google.android.gms.ads.*
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class BilliardsDraw : ComponentActivity() {

    private val model: BilliardsDrawViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel
        model.onCreate()

        // Google Play Services (pay)
        val purchasesUpdatedListener =
            PurchasesUpdatedListener { billingResult, purchases ->
                // To be implemented in a later section.
                Log.d("billingresultcode", billingResult.responseCode.toString())
                Log.d("purchasessize", purchases?.size.toString())
            }

        val billingClient = BillingClient.newBuilder(applicationContext)
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
            Log.d("billingResult", billingResult.responseCode.toString())
            // process returned productDetailsList
            Log.d("productDetailsList", productDetailsList.size.toString())
        }

        model.setLoading(false)

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
        val context = LocalContext.current

        // Billiards Draw App UI Structure (here starts the UI)
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                BilliardsDrawTopBar(
                    viewModel = model,
                    navController = navController
                )
            }, bottomBar = {
                // Force a crash
                /*
                Button(onClick = { throw RuntimeException("Test Crash") }) {
                    Text(text = "Crash")
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