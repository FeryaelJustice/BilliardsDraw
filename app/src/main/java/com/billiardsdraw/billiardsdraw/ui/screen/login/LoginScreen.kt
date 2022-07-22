package com.billiardsdraw.billiardsdraw.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.TextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {
    if (appViewModel.isLoading.value == true) {
        CircularProgressIndicator()
    } else {
        val context = LocalContext.current

        if (appViewModel.isLogged.value == true) {
            navigateClearingAllBackstack(navController, Routes.MenuScreen.route)
        }

        val email: String? by viewModel.email.observeAsState(null)
        val password: String? by viewModel.password.observeAsState(null)
        val keepSession: Boolean? by viewModel.keepSession.observeAsState(null)
        Box(modifier = Modifier.fillMaxSize()) {
            Card(elevation = 4.dp, modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.fondoinicio),
                    contentDescription = "Fondo inicio",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(id = R.string.welcome) + " " + stringResource(id = R.string.app_name),
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            value = email ?: "",
                            onValueChange = { viewModel.setEmail(it) },
                            label = { Text("Enter email", color = Color.Black) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier.background(
                                Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        TextField(
                            value = password ?: "",
                            onValueChange = { viewModel.setPassword(it) },
                            label = { Text("Enter password", color = Color.Black) },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier.background(Color.White)
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        Button(
                            onClick = {
                                appViewModel.setLoading(true)
                                if (viewModel.login()) {
                                    appViewModel.setLogged(true)
                                    navigateClearingAllBackstack(
                                        navController,
                                        Routes.MenuScreen.route
                                    )
                                    showToastLong(context = context, "Welcome to Billiards Draw!")
                                } else {
                                    showToastLong(context = context, "Can't login!")
                                }
                                appViewModel.setLoading(false)
                            },
                            modifier = Modifier.width(160.dp)
                        ) {
                            Text(
                                text = "Iniciar sesión"
                            )
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                        Row {
                            Checkbox(
                                checked = keepSession ?: false,
                                onCheckedChange = { viewModel.setKeepSession(it) })
                            Text(
                                text = "Mantener sesión iniciada",
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = "¿Has olvidado tu contraseña?",
                            Modifier
                                .clickable {
                                    navigate(
                                        navController,
                                        Routes.RecoverAccountScreen.route
                                    )
                                }
                                .fillMaxWidth()
                                .align(Alignment.Start),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "¿Acabas de llegar?",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.button_joinnow),
                            contentDescription = "Join now",
                            modifier = Modifier
                                .scale(2f)
                                .clickable {
                                    navigate(
                                        navController,
                                        Routes.RegisterScreen.route
                                    )
                                }
                        )
                    }
                }
            }
        }
    }
}