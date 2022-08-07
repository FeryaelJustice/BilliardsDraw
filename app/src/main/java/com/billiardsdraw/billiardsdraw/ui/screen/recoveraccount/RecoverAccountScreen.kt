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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RecoverAccountScreen(
    viewModel: RecoverAccountScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Card(elevation = 4.dp, modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.backgroundscreen),
                contentDescription = stringResource(id = R.string.backgroundScreenDescription),
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
                    contentDescription = context.resources.getString(R.string.logo),
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
                Spacer(modifier = Modifier.height(1.dp))
                val sendEmailText = stringResource(id = R.string.sendEmail)
                val sendEmailFailText = stringResource(id = R.string.sendEmailFail)
                Button(
                    onClick = {
                        // Enviar email
                        if (sendEmail(viewModel.email)) {
                            showToastLong(context, sendEmailText)
                            navigate(navController, Routes.LoginScreen.route)
                        } else {
                            showToastLong(context, sendEmailFailText)
                        }
                    },
                    modifier = Modifier.width(160.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.sendEmail)
                    )
                }
            }
        }
    }
}

private fun sendEmail(email: String): Boolean {
    // E-mail
    /*
    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {
        success = it.isSuccessful
    }
    */
    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener {}
    return true
}