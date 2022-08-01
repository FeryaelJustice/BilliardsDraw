package com.billiardsdraw.billiardsdraw.ui.screen.user.premium

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserPremiumScreen(
    viewModel: UserPremiumScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {
    if (appViewModel.user.value?.role == "premium") {
        val context = LocalContext.current
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
                        contentDescription = "Back",
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
                        contentDescription = "Billiards Draw"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = "Configuration",
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
                            contentDescription = "Contact form"
                        )
                        Button(onClick = {
                            viewModel.signOut(navController)
                        }) {
                            Text(text = "Cerrar sesión")
                        }
                    }
                }
            }
        }
    } else {
        navController.popBackStack()
    }
}