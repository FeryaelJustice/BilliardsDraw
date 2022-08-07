package com.billiardsdraw.billiardsdraw.ui.screen.user.profile

import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfileScreen(
    viewModel: UserProfileScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {
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
                    painter = painterResource(id = R.drawable.config),
                    contentDescription = context.resources.getString(R.string.configuration),
                    modifier = Modifier
                        .scale(2f)
                        .clickable {
                            // Navigate to config user screen
                            // De momento, vamos a la pantalla premium
                            try {
                                val back: NavBackStackEntry =
                                    navController.getBackStackEntry(Routes.UserPremiumScreen.route)
                                Log.d("in_back_stack", back.destination.label.toString())
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.UserPremiumScreen.route
                                )
                            } catch (ex: IllegalArgumentException) {
                                Log.d("in_back_stack", "no_entry")
                                navigate(
                                    navController,
                                    Routes.UserPremiumScreen.route
                                )
                            }
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profileicon),
                            contentDescription = context.resources.getString(R.string.profileicon),
                            modifier = Modifier
                                .scale(2f)
                        )
                        Button(onClick = {}) {
                            Text(text = appViewModel.user.value?.country!!, color = Color.White)
                        }
                    }
                    Row {
                        Text(
                            text = "Username: ",
                            color = Color.White
                        )
                        Text(
                            text = appViewModel.user.value?.username!!,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Text(
                            text = "Nickname: ",
                            color = Color.White
                        )
                        Text(
                            text = appViewModel.user.value?.nickname!!,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Text(
                            text = "Full name: ",
                            color = Color.White
                        )
                        Text(
                            text = appViewModel.user.value?.name!! + " " + appViewModel.user.value?.surnames!!,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Text(
                            text = "Email: ",
                            color = Color.White
                        )
                        Text(
                            text = appViewModel.user.value?.email!!,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Text(
                            text = "Birthday date: ",
                            color = Color.White
                        )
                        Text(
                            text = appViewModel.user.value?.birthdate.toString(),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Text(
                            text = "Subscription state: ",
                            color = Color.White
                        )
                        Text(
                            text = appViewModel.user.value?.role!!,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = stringResource(R.string.linkGoogle), color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = stringResource(R.string.linkFacebook), color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        viewModel.signOut(navController)
                    }) {
                        Text(text = stringResource(id = R.string.signOut))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        modifier = Modifier
                            .scale(2f)
                            .clickable { viewModel.openContactForm(navController) },
                        painter = painterResource(id = R.drawable.contactform),
                        contentDescription = context.resources.getString(R.string.contact_form),
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = context.resources.getString(R.string.logo)
                    )
                }
            }
        }
    }
}