package com.billiardsdraw.billiardsdraw.ui.screen.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.BilliardsDrawFontSize

@Composable
fun ContactScreen(
    viewModel: ContactScreenViewModel,
    navController: NavHostController
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
                    contentDescription = context.resources.getString(R.string.back),
                    modifier = Modifier
                        .scale(2f)
                        .clickable {
                            navController.popBackStack()
                        })
                Image(
                    modifier = Modifier.scale(2f),
                    painter = painterResource(id = R.drawable.billiardsdraw),
                    contentDescription = context.resources.getString(R.string.app_name)
                )
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
                    Text(
                        text = context.resources.getString(R.string.contact_form).uppercase(),
                        color = Color.White,
                        // fontSize = BilliardsDrawFontSize.EXTRA_BIG.size
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    TextField(
                        value = viewModel.subject,
                        onValueChange = { viewModel.subject = it },
                        label = {
                            Text(
                                text =
                                context.resources.getString(R.string.subject)
                            )
                        },
                        placeholder = {
                            Text(
                                text =
                                context.resources.getString(R.string.subject)
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(
                            Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    TextField(
                        value = viewModel.text,
                        onValueChange = { viewModel.text = it },
                        label = {
                            Text(
                                text =
                                context.resources.getString(R.string.text)
                            )
                        },
                        placeholder = {
                            Text(
                                text =
                                context.resources.getString(R.string.text)
                            )
                        },
                        maxLines = 40,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        modifier = Modifier.background(
                            Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = { viewModel.send(navController, context) }) {
                        Text(text = context.resources.getString(R.string.send))
                    }
                }
            }
        }
    }
}