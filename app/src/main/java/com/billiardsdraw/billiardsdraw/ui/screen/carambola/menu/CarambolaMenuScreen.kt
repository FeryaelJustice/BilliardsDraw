package com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong

@Composable
fun CarambolaMenuScreen(viewModel: BilliardsDrawViewModel, navController: NavHostController) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Card(elevation = 4.dp, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.carambola_menu),
                contentDescription = "Fondo carambola menu",
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
                        contentDescription = "Back",
                        modifier = Modifier
                            .scale(2f)
                            .clickable {
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.MenuScreen.route
                                )
                            })
                    Image(
                        modifier = Modifier.scale(2f),
                        painter = painterResource(id = R.drawable.billiardsdraw),
                        contentDescription = "Billiards Draw"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.profileicon),
                        contentDescription = "Profile icon",
                        modifier = Modifier
                            .scale(2f)
                            .clickable {
                                // Navigate to profile screen
                                navigateClearingAllBackstack(navController, Routes.UserProfileScreen.route)
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
                        Image(
                            painter = painterResource(id = R.drawable.button_draw),
                            contentDescription = "Draw",
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    navigateClearingAllBackstack(
                                        navController,
                                        Routes.CarambolaScreen.route
                                    )
                                }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.button_mypositions),
                            contentDescription = "My positions",
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    showToastLong(context, "My positions")
                                }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(
                            painter = painterResource(id = R.drawable.button_weeklyposition),
                            contentDescription = "Weekly positions",
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    showToastLong(context, "Weekly positions")
                                }
                        )
                    }
                }
            }
        }
    }
}