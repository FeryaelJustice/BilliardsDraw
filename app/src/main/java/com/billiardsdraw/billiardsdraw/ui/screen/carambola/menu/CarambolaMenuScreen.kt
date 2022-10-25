package com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.ads.createInterstitialAd
import com.billiardsdraw.billiardsdraw.common.ads.enableAds
import com.billiardsdraw.billiardsdraw.common.buildConfigFlavor
import com.billiardsdraw.billiardsdraw.ui.components.UserProfilePicture
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import kotlinx.coroutines.CoroutineScope

@Composable
fun CarambolaMenuScreen(
    viewModel: CarambolaMenuScreenViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    appViewModel: BilliardsDrawViewModel
) {
    // Check is Logged
    LaunchedEffect(key1 = "loginCheck", block = {
        if (!appViewModel.isSignedIn()) {
            navigateClearingAllBackstack(navController, Routes.GeneralApp.route)
        } else {
            viewModel.onCreate(appViewModel)
        }
    })
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Card(elevation = 4.dp, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.carambola_menu),
                contentDescription = stringResource(id = R.string.backgroundScreenDescription),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(1.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = context.resources.getString(R.string.back),
                        modifier = Modifier
                            .scale(2f)
                            .clickable {
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.LoggedApp.route
                                )
                            })
                    Image(
                        modifier = Modifier.scale(2f),
                        painter = painterResource(id = R.drawable.billiardsdraw),
                        contentDescription = context.resources.getString(R.string.app_name)
                    )
                    UserProfilePicture(
                        isLoading = false,
                        imageURL = viewModel.profilePicture,
                        context
                    ) {
                        // Navigate to profile screen
                        try {
                            val back: NavBackStackEntry =
                                navController.getBackStackEntry(Routes.UserProfileScreen.route)
                            Log.d("in_back_stack", back.destination.label.toString())
                            navigate(
                                navController,
                                Routes.UserProfileScreen.route
                            )
                        } catch (ex: IllegalArgumentException) {
                            Log.d("in_back_stack", "no_entry")
                            navigate(
                                navController,
                                Routes.UserProfileScreen.route
                            )
                        }
                    }
                    /*
                    Image(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = context.resources.getString(R.string.profileicon),
                        modifier = Modifier
                            .scale(2f)
                            .clickable {
                                // Navigate to profile screen
                                try {
                                    val back: NavBackStackEntry =
                                        navController.getBackStackEntry(Routes.UserProfileScreen.route)
                                    Log.d("in_back_stack", back.destination.label.toString())
                                    navigate(
                                        navController,
                                        Routes.UserProfileScreen.route
                                    )
                                } catch (ex: IllegalArgumentException) {
                                    Log.d("in_back_stack", "no_entry")
                                    navigate(
                                        navController,
                                        Routes.UserProfileScreen.route
                                    )
                                }
                            })
                    */
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(1.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.button_draw),
                            contentDescription = context.resources.getString(R.string.draw),
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    if (enableAds && (buildConfigFlavor() != "premium")) {
                                        createInterstitialAd(context)
                                    }
                                    navigate(
                                        navController,
                                        Routes.CarambolaScreen.route
                                    )
                                }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.button_mypositions),
                            contentDescription = context.resources.getString(R.string.my_positions),
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    if (enableAds && (buildConfigFlavor() != "premium")) {
                                        createInterstitialAd(context)
                                    }
                                    navigate(
                                        navController,
                                        Routes.CarambolaMyPositionsScreen.route
                                    )
                                }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.button_weeklyposition),
                            contentDescription = context.resources.getString(R.string.weekly_positions),
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    if (enableAds && (buildConfigFlavor() != "premium")) {
                                        createInterstitialAd(context)
                                    }
                                    navigate(
                                        navController,
                                        Routes.CarambolaWeeklyPositionScreen.route
                                    )
                                }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "SALIDA PBA",
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                if (buildConfigFlavor() == "premium") {
                                    navigate(
                                        navController,
                                        Routes.CarambolaPBAScreen.route
                                    )
                                } else {
                                    if (enableAds) {
                                        createInterstitialAd(context)
                                    }
                                    showToastLong(
                                        context.applicationContext,
                                        "You need to be premium to unlock this feature."
                                    )
                                }
                            })
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "ENTRENAMIENTO",
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                if (buildConfigFlavor() == "premium") {
                                    navigate(
                                        navController,
                                        Routes.CarambolaTrainingScreen.route
                                    )
                                } else {
                                    if (enableAds) {
                                        createInterstitialAd(context)
                                    }
                                    showToastLong(
                                        context.applicationContext,
                                        "You need to be premium to unlock this feature."
                                    )
                                }
                            })
                    }
                }
            }
        }
    }
}