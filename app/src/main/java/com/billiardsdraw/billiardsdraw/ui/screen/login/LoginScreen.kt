package com.billiardsdraw.billiardsdraw.ui.screen.login

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.ads.CreateBanner
import com.billiardsdraw.billiardsdraw.common.ads.enableAds
import com.billiardsdraw.billiardsdraw.ui.components.CustomGoogleButton
import com.billiardsdraw.billiardsdraw.ui.components.login.CustomFacebookButton
import com.billiardsdraw.billiardsdraw.ui.components.login.FacebookUtil
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {
    if (appViewModel.isLoading.value == true) {
        CircularProgressIndicator()
    } else {
        viewModel.onCreate()

        val context = LocalContext.current

        // RECUERDA: El autologin con sharedprefs, o con variables hace que pete, se repinta, averiguar manera
        LaunchedEffect(key1 = "isLogged", block = {
            if (appViewModel.isLogged()) {
                appViewModel.setLoading(true)
                viewModel.signIn(appViewModel, context, navController, true)
                appViewModel.setLoading(false)
            }
        })

        // Google sign in (still have to implement)
        /*
        val launcherGoogle = rememberLauncherForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode != Activity.RESULT_OK) {
                // The user cancelled the login, was it due to an Exception?
                if (result.data?.action == ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST) {
                    val exception: Exception? = result.data?.getSerializableExtra(
                        ActivityResultContracts.StartIntentSenderForResult.EXTRA_SEND_INTENT_EXCEPTION
                    ) as Exception?
                    Log.e("LOG", "Couldn't start One Tap UI: ${exception?.localizedMessage}")
                }
                return@rememberLauncherForActivityResult
            }
            val oneTapClient = Identity.getSignInClient(context)
            val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken
            if (idToken != null) {
                // Got an ID token from Google. Use it to authenticate
                // with your backend.
                showToastShort(context, "Google Auth ID: ${idToken.toString()}")
                Log.d("LOG", idToken)
            } else {
                Log.d("LOG", "Null Token")
            }
        }
        */

        // Facebook sign in (still have to implement, this is not the way, upwards the google way is correct)
        /*
        val launcherFacebook = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    if (result.data?.action == ActivityResultContracts.StartIntentSenderForResult.ACTION_INTENT_SENDER_REQUEST) {
                        val exception: Exception? = result.data?.getSerializableExtra(
                            ActivityResultContracts.StartIntentSenderForResult.EXTRA_SEND_INTENT_EXCEPTION
                        ) as Exception?
                        Log.e("LOG", "Couldn't start One Tap UI: ${exception?.localizedMessage}")
                        return@rememberLauncherForActivityResult
                    }
                }
                val credential = FacebookUtil.callbackManager
                if (credential != null) {
                    Log.d("LOG", credential.toString())
                } else {
                    Log.d("LOG", "Null Token")
                }
            }
        )
        */

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
                            enabled = !appViewModel.isLogged(),
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
                            enabled = !appViewModel.isLogged(),
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
                        Spacer(modifier = Modifier.height(1.dp))
                        Button(
                            onClick = {
                                // appViewModel.setLoading(true)
                                viewModel.signIn(
                                    appViewModel,
                                    context,
                                    navController,
                                    false
                                )
                                // appViewModel.setLoading(false)
                            },
                            modifier = Modifier.width(160.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.signIn)
                            )
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                        /*
                        // Create a scope that is automatically cancelled
                        // if the user closes your app while async work is
                        // happening
                        val scope = rememberCoroutineScope()
                        CustomGoogleButton(
                            onClicked = {
                                scope.launch {
                                    viewModel.handleGoogleSignIn(
                                        context = context,
                                        launcher = launcherGoogle
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(1.dp))
                        CustomFacebookButton(
                            onSuccess = {
                                viewModel.handleFacebookAccessToken(
                                    it.accessToken,
                                    context,
                                    appViewModel,
                                    navController
                                )
                            },
                            onCancel = {},
                            onError = {
                                if (it != null) {
                                    Log.d("facebook", "facebook error: ${it.message}")
                                }
                            })
                        Spacer(modifier = Modifier.height(1.dp))
                        */
                        Row {
                            Checkbox(
                                checked = viewModel.keepSession,
                                onCheckedChange = { viewModel.keepSession = it })
                            Text(
                                text = stringResource(id = R.string.keepSession),
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                        }
                        Spacer(modifier = Modifier.height(1.dp))
                        Text(
                            text = stringResource(id = R.string.forgotPassword),
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
                            text = stringResource(id = R.string.justArrived),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.button_joinnow),
                            contentDescription = context.resources.getString(R.string.joinnow),
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

                    // Banner ad
                    if (enableAds) {
                        CreateBanner()
                    }
                }
            }
        }
    }
}