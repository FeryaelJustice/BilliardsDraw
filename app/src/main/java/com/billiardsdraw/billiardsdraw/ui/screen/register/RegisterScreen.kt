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
import com.billiardsdraw.billiardsdraw.domain.model.SignInMethod
import com.billiardsdraw.billiardsdraw.ui.components.CustomSignInButton
import com.billiardsdraw.billiardsdraw.ui.components.CustomSignUpButton
import com.billiardsdraw.billiardsdraw.ui.components.EmailField
import com.billiardsdraw.billiardsdraw.ui.components.PasswordField
import kotlinx.coroutines.CoroutineScope

@Composable
fun RegisterScreen(
    viewModel: RegisterScreenViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
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
                    Spacer(modifier = Modifier.height(4.dp))
                    PasswordField(
                        password = viewModel.repeatPassword,
                        onTextFieldChanged = { viewModel.repeatPassword = it },
                        context = context,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !appViewModel.isSignedIn(),
                        isRepeatPassword = true
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    CustomSignUpButton(
                        context = context,
                        modifier = Modifier.width(160.dp),
                        enabled = true,
                    ) {
                        viewModel.signUp(
                            appViewModel,
                            context,
                            navController
                        )
                    }
                }
            }
        }
    }
}