package com.billiardsdraw.billiardsdraw.ui.screen.user.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.toDate
import com.billiardsdraw.billiardsdraw.domain.model.SignInMethod
import com.billiardsdraw.billiardsdraw.ui.components.UserProfilePicture
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong

@Composable
fun UserProfileScreen(
    viewModel: UserProfileScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel,
    onSignOut: (navController: NavHostController) -> Unit
) {
    val currentUser = appViewModel.currentUser

    // Check is Logged
    LaunchedEffect(key1 = "loginCheck", block = {
        if (!appViewModel.isLogged()) {
            navigateClearingAllBackstack(navController, Routes.GeneralApp.route)
        }
    })

    // Context
    val context = LocalContext.current

    // To execute it one time, if not, it's executed infinite times
    LaunchedEffect(Unit) {
        viewModel.onCreate(appViewModel)
    }

    // Select image profile picture
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.profilePicture = uri
    }

    // UI
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        ) {
            item {
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
                                // For the moment, we navigate to premium user screen
                                try {
                                    val back: NavBackStackEntry =
                                        navController.getBackStackEntry(Routes.UserPremiumScreen.route)
                                    Log.d("in_back_stack", back.destination.label.toString())
                                    navigate(
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
                            UserProfilePicture(imageURL = viewModel.profilePicture, context) {
                                launcher.launch("image/*")
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Button(onClick = {}) {
                                Text(
                                    text = "" + appViewModel.user.value?.country,
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row {
                            Text(
                                text = stringResource(id = R.string.username) + ": ",
                                color = Color.White
                            )
                            if (!viewModel.isEditing) {
                                Text(
                                    text = "" + appViewModel.user.value?.username,
                                    color = Color.White
                                )
                            } else {
                                TextField(
                                    value = viewModel.user.username,
                                    onValueChange = {
                                        viewModel.user = viewModel.user.copy(username = it)
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = Modifier
                                        .background(
                                            Color.White
                                        )
                                        .fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Text(
                                text = stringResource(id = R.string.nickname) + ": ",
                                color = Color.White
                            )
                            if (!viewModel.isEditing) {
                                Text(
                                    text = "" + appViewModel.user.value?.nickname,
                                    color = Color.White
                                )
                            } else {
                                TextField(
                                    value = viewModel.user.nickname,
                                    onValueChange = {
                                        viewModel.user = viewModel.user.copy(nickname = it)
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = Modifier
                                        .background(
                                            Color.White
                                        )
                                        .fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Text(
                                text = stringResource(id = R.string.name) + " " + stringResource(id = R.string.surnames) + ": ",
                                color = Color.White
                            )
                            if (!viewModel.isEditing) {
                                Text(
                                    text = "" + appViewModel.user.value?.name + " " + appViewModel.user.value?.surnames,
                                    color = Color.White
                                )
                            } else {
                                Column {
                                    TextField(
                                        value = viewModel.user.name,
                                        onValueChange = {
                                            viewModel.user = viewModel.user.copy(name = it)
                                        },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                        modifier = Modifier
                                            .background(
                                                Color.White
                                            )
                                            .fillMaxWidth()
                                    )
                                    TextField(
                                        value = viewModel.user.surnames,
                                        onValueChange = {
                                            viewModel.user = viewModel.user.copy(surnames = it)
                                        },
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                        modifier = Modifier
                                            .background(
                                                Color.White
                                            )
                                            .fillMaxWidth()
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Text(
                                text = stringResource(id = R.string.email) + ": ",
                                color = Color.White
                            )
                            if (!viewModel.isEditing) {
                                Text(
                                    text = "" + appViewModel.user.value?.email,
                                    color = Color.White
                                )
                            } else {
                                TextField(
                                    value = viewModel.user.email,
                                    onValueChange = {
                                        viewModel.user = viewModel.user.copy(email = it)
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = Modifier
                                        .background(
                                            Color.White
                                        )
                                        .fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Text(
                                text = stringResource(id = R.string.birthdate) + ": ",
                                color = Color.White
                            )
                            if (!viewModel.isEditing) {
                                Text(
                                    text = "" + appViewModel.user.value?.birthdate.toString(),
                                    color = Color.White
                                )
                            } else {
                                TextField(
                                    value = viewModel.user.birthdate.toString(),
                                    onValueChange = {
                                        viewModel.user =
                                            viewModel.user.copy(birthdate = it.toDate()!!)
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = Modifier
                                        .background(
                                            Color.White
                                        )
                                        .fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            Text(
                                text = stringResource(id = R.string.subscription_state) + ": ",
                                color = Color.White
                            )
                            if (!viewModel.isEditing) {
                                Text(
                                    text = "" + appViewModel.user.value?.role,
                                    color = Color.White
                                )
                            } else {
                                TextField(
                                    value = viewModel.user.role,
                                    onValueChange = {
                                        viewModel.user = viewModel.user.copy(role = it)
                                    },
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    modifier = Modifier
                                        .background(
                                            Color.White
                                        )
                                        .fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = stringResource(R.string.linkGoogle),
                            color = Color.White,
                            modifier = Modifier.clickable {
                                showToastLong(
                                    context,
                                    context.resources.getString(R.string.coming_soon)
                                )
                            })
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = stringResource(R.string.linkFacebook),
                            color = Color.White,
                            modifier = Modifier.clickable {
                                showToastLong(
                                    context,
                                    context.resources.getString(R.string.coming_soon)
                                )
                            })
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {
                            // START SIGN OUT
                            when (appViewModel.getSignInMethodSharedPrefs()) {
                                SignInMethod.Custom -> {
                                    // Own sign out
                                    Button(onClick = {
                                        onSignOut(navController)
                                    }) {
                                        Text(text = stringResource(id = R.string.signOut))
                                    }
                                }
                                SignInMethod.Google -> {
                                    // Google sign out
                                    if (currentUser.value != null) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Button(onClick = { onSignOut(navController) }) {
                                                androidx.compose.material3.Text(text = "Sign out")
                                            }
                                        }
                                    }
                                }
                            }
                            // END SIGN OUT
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(
                                    checked = viewModel.isEditing,
                                    onCheckedChange = { viewModel.isEditing = it },
                                    colors = CheckboxDefaults.colors(Color.Blue),
                                )
                                Text(
                                    text = context.resources.getString(R.string.askForEditing),
                                    color = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(onClick = { viewModel.saveEdit(context, appViewModel) }) {
                            Text(text = context.resources.getString(R.string.save))
                        }
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
}