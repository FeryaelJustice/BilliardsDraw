package com.billiardsdraw.billiardsdraw.ui.screen.user.profile

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
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
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
                    painter = painterResource(id = R.drawable.config),
                    contentDescription = "Configuration",
                    modifier = Modifier
                        .scale(2f)
                        .clickable {
                            // Navigate to config user screen
                            // De momento, vamos a la pantalla premium
                            navigateClearingAllBackstack(
                                navController,
                                Routes.UserPremiumScreen.route
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profileicon),
                            contentDescription = "Profile icon",
                            modifier = Modifier
                                .scale(2f)
                        )
                        Button(onClick = {}) {
                            Text(text = "COUNTRY", color = Color.White)
                        }
                    }
                    Text(text = "Username: ${appViewModel.user.value?.username}", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Nickname: ${appViewModel.user.value?.nickname}", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Full name: ${appViewModel.user.value?.name} ${appViewModel.user.value?.surnames}", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Email: ${appViewModel.user.value?.email}", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Birthday date ${appViewModel.user.value?.birthdate.toString()}", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Subscription state: ${appViewModel.user.value?.role}", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Link Google account", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Link Facebook account", color = Color.White)
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {
                        viewModel.signOut(navController)
                    }) {
                        Text(text = stringResource(id = R.string.signOut))
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        modifier = Modifier.scale(2f).clickable { viewModel.openContactForm(navController) },
                        painter = painterResource(id = R.drawable.contactform),
                        contentDescription = "Contact form"
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo"
                    )
                }
            }
        }
    }
}