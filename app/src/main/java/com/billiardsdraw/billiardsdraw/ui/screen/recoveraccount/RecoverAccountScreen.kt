package com.billiardsdraw.billiardsdraw.ui.screen.recoveraccount

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong

@Composable
fun RecoverAccountScreen(viewModel: BilliardsDrawViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val isEmailSent = rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var emailCode by rememberSaveable { mutableStateOf("0") }
    var password by rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Card(elevation = 4.dp, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.fondoinicio),
                contentDescription = "Fondo inicio",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
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
                    text = stringResource(id = R.string.app_name),
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
                if (isEmailSent.value) {
                    TextField(
                        value = emailCode,
                        onValueChange = { emailCode = it },
                        label = {
                            Text(
                                "Enter the email code we sent to your email",
                                color = Color.Black
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Enter password", color = Color.Black) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Button(
                        onClick = {
                            // Comprobar codigo
                            if (checkRecoverAccountCode(emailCode)) {
                                showToastLong(context, "Account password restored")
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.LoginScreen.route
                                )
                            }else{
                                showToastLong(context,"Error recovering account")
                            }
                        },
                        modifier = Modifier.width(160.dp)
                    ) {
                        Text(
                            text = "Change password"
                        )
                    }
                } else {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Enter email", color = Color.Black) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.background(
                            Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Button(
                        onClick = {
                            // Enviar email
                            sendEmail(email)
                            showToastLong(context,"Email sent")
                            isEmailSent.value = true
                        },
                        modifier = Modifier.width(160.dp)
                    ) {
                        Text(
                            text = "Enviar email"
                        )
                    }
                }
            }
        }
    }
}

private fun sendEmail(email: String) {
    // E-mail

}

private fun checkRecoverAccountCode(emailCode: String): Boolean {
    val result = true
    return false
}