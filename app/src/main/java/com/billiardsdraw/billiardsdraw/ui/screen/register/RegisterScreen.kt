package com.billiardsdraw.billiardsdraw.ui.screen.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R

@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Card(elevation = 4.dp, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.backgroundscreen),
                contentDescription = context.resources.getString(R.string.backgroundScreenDescription),
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
                        contentDescription = context.resources.getString(R.string.logo),
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
                        value = viewModel.email,
                        onValueChange = { viewModel.email = it },
                        label = {
                            Text(
                                stringResource(id = R.string.email),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.email_hint),
                                color = Color.Black
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.background(
                            Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.password,
                        onValueChange = { viewModel.password = it },
                        label = {
                            Text(
                                stringResource(id = R.string.password),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.password_hint),
                                color = Color.Black
                            )
                        },
                        visualTransformation = if (viewModel.passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.background(Color.White),
                        trailingIcon = {
                            val image = if (viewModel.passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description =
                                if (viewModel.passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {
                                viewModel.passwordVisible = !viewModel.passwordVisible
                            }) {
                                Icon(imageVector = image, description)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.repeatPassword,
                        onValueChange = { viewModel.repeatPassword = it },
                        label = {
                            Text(
                                stringResource(id = R.string.repeatpassword_hint),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.repeatpassword_hint),
                                color = Color.Black
                            )
                        },
                        visualTransformation = if (viewModel.repeatPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.background(Color.White),
                        trailingIcon = {
                            val image = if (viewModel.repeatPasswordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description =
                                if (viewModel.repeatPasswordVisible) "Hide password" else "Show password"

                            IconButton(onClick = {
                                viewModel.repeatPasswordVisible = !viewModel.repeatPasswordVisible
                            }) {
                                Icon(imageVector = image, description)
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Button(
                        onClick = {
                            // appViewModel.setLoading(true)
                            viewModel.signUp(
                                appViewModel,
                                context,
                                navController
                            )
                            // appViewModel.setLoading(false)
                        },
                        modifier = Modifier.width(160.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.signUp)
                        )
                    }
                }
            }
        }
    }
}