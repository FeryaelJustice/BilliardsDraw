package com.billiardsdraw.billiardsdraw.ui.screen.profile

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R

@Composable
fun CompleteProfileScreen(
    viewModel: CompleteProfileScreenViewModel,
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
                    TextField(
                        value = viewModel.username,
                        onValueChange = { viewModel.username = it },
                        label = {
                            Text(
                                stringResource(id = R.string.username),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.username) + "(can't edit this afterwards)",
                                color = Color.Black
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(
                            Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.nickname,
                        onValueChange = { viewModel.nickname = it },
                        label = {
                            Text(
                                stringResource(id = R.string.nickname),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.nickname),
                                color = Color.Black
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.name,
                        onValueChange = { viewModel.name = it },
                        label = {
                            Text(
                                stringResource(id = R.string.name),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.name),
                                color = Color.Black
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.surnames,
                        onValueChange = { viewModel.surnames = it },
                        label = {
                            Text(
                                stringResource(id = R.string.surnames),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.surnames),
                                color = Color.Black
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.country,
                        onValueChange = { viewModel.country = it },
                        label = {
                            Text(
                                stringResource(id = R.string.country),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.country),
                                color = Color.Black
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.age,
                        onValueChange = { viewModel.age = it },
                        label = {
                            Text(
                                stringResource(id = R.string.age),
                                color = Color.Black
                            )
                        },
                        placeholder = {
                            Text(
                                stringResource(id = R.string.age),
                                color = Color.Black
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Button(
                        onClick = {
                            // appViewModel.setLoading(true)
                            viewModel.completeProfile(
                                appViewModel,
                                context,
                                navController
                            )
                            // appViewModel.setLoading(false)
                        },
                        modifier = Modifier.width(160.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.complete_profile)
                        )
                    }
                }
            }
        }
    }
}