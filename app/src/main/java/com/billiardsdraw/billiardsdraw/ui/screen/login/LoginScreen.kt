package com.billiardsdraw.billiardsdraw.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.ads.CreateBanner
import com.billiardsdraw.billiardsdraw.common.ads.enableAds
import com.billiardsdraw.billiardsdraw.domain.model.SignInMethod
import com.billiardsdraw.billiardsdraw.ui.components.CustomGoogleButton
import com.billiardsdraw.billiardsdraw.ui.components.CustomSignInButton
import com.billiardsdraw.billiardsdraw.ui.components.EmailField
import com.billiardsdraw.billiardsdraw.ui.components.PasswordField
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import kotlinx.coroutines.CoroutineScope

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    appViewModel: BilliardsDrawViewModel,
    onSignIn: (
        signInMethod: SignInMethod,
        context: Context,
        navController: NavHostController,
        emailStr: String?,
        passwordStr: String?,
        keepSession: Boolean?
    ) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = "onCreate", block = {
        viewModel.onCreate()
    })

    // REMEMBER: Auto login with shared prefs, or with variables makes it crash, it repaints itself, search the way
    LaunchedEffect(key1 = "isLogged", block = {
        // Auto login
        if (appViewModel.isSignedIn()) {
            if (appViewModel.isKeepSession() || appViewModel.getSignInMethodSharedPrefs() == SignInMethod.Google) {
                onSignIn(
                    appViewModel.getSignInMethodSharedPrefs(),
                    context,
                    navController,
                    viewModel.email,
                    viewModel.password,
                    viewModel.keepSession
                )
            }
        }
    })

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
                    EmailField(
                        email = viewModel.email,
                        onTextFieldChanged = { viewModel.email = it },
                        context = context,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !appViewModel.isSignedIn()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    PasswordField(
                        password = viewModel.password,
                        onTextFieldChanged = { viewModel.password = it },
                        context = context,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !appViewModel.isSignedIn()
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    // START LOGIN
                    // Own sign in
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (!appViewModel.isSignedIn()) {
                            CustomSignInButton(
                                context = context,
                                modifier = Modifier.width(160.dp),
                                enabled = !appViewModel.isSignedIn()
                            ) {
                                appViewModel.setSignInMethodSharedPrefs(SignInMethod.Custom)
                                onSignIn(
                                    appViewModel.getSignInMethodSharedPrefs(),
                                    context,
                                    navController,
                                    viewModel.email,
                                    viewModel.password,
                                    viewModel.keepSession
                                )
                            }
                        } else {
                            Text(text = "Welcome, ${appViewModel.currentUser.value?.displayName}")
                        }
                    }
                    Spacer(modifier = Modifier.height(1.dp))
                    Row {
                        Checkbox(
                            checked = viewModel.keepSession,
                            onCheckedChange = {
                                viewModel.keepSession = it
                                Log.d("checkbox", viewModel.keepSession.toString())
                                Log.d("checkboxValue", it.toString())
                            }
                        )
                        Text(
                            text = stringResource(id = R.string.keepSession),
                            color = Color.White,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                    }
                    // Google sign in
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (!appViewModel.isSignedIn()) {
                            CustomGoogleButton(
                                context = LocalContext.current,
                                enabled = !appViewModel.isSignedIn(),
                                onClicked = {
                                    appViewModel.setSignInMethodSharedPrefs(SignInMethod.Google)
                                    onSignIn(
                                        appViewModel.getSignInMethodSharedPrefs(),
                                        context,
                                        navController,
                                        viewModel.email,
                                        viewModel.password,
                                        viewModel.keepSession
                                    )
                                }
                            )
                        } else {
                            Text(text = "Google welcomes you, ${appViewModel.currentUser.value?.displayName}")
                        }
                    }
                    if (appViewModel.isSignedIn()) {
                        Button(onClick = { appViewModel.signOut(navController) }) {
                            Text(text = context.resources.getString(R.string.signOut))
                        }
                    }
                    // END LOGIN
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