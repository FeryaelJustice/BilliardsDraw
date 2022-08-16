package com.billiardsdraw.billiardsdraw.ui.screen.user.premium

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserPremiumScreen(
    viewModel: UserPremiumScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {
    // Check is Logged
    LaunchedEffect(key1 = "loginCheck", block = {
        if (!appViewModel.isLogged()) {
            navigateClearingAllBackstack(navController, Routes.GeneralApp.route)
        }
    })

    val context = LocalContext.current

    if (appViewModel.user.value?.role == "premium") {
        Box(modifier = Modifier.fillMaxSize()) {
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
                    Image(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = context.resources.getString(R.string.configuration),
                        modifier = Modifier
                            .scale(2f)
                            .clickable {
                                // Navigate to user prfile screen
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.UserProfileScreen.route
                                )
                            })
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
                        Text(text = "SUBSCRIBE NOW", color = Color.White)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Become premium function
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = "BECOME", color = Color.White)
                            Text(text = " PREMIUM", color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = " * ENJOY POSITIONS AND WEEKLY DRILLS", color = Color.White)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = " * UNLOCK THE CUSTOMIZE TABLE FEATURE", color = Color.White)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = " * UNLOCK THE EXCLUSIVE BALL GAMES", color = Color.White)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = " * FORGET ABOUT ADS", color = Color.White)
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "AND A LOT MORE!", color = Color.White)
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(text = "FOR ONLY...", color = Color.White)
                        Text(text = "1,49€/MONTH", color = Color.White)
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    // Become premium function
                                },
                            painter = painterResource(id = R.drawable.button_joinnow),
                            contentDescription = context.resources.getString(R.string.contact_form),
                        )
                        Button(onClick = {
                            viewModel.signOut(navController)
                        }) {
                            Text(text = stringResource(id = R.string.signOut))
                        }
                    }
                }
            }
        }
    } else {
        navigateClearingAllBackstack(navController, Routes.LoggedApp.route)
        showToastShort(context, stringResource(id = R.string.user_is_not_premium))
    }
}