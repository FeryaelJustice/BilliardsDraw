package com.billiardsdraw.billiardsdraw.ui.screen.profile

import android.app.DatePickerDialog
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.billiardsdraw.billiardsdraw.ui.components.UserProfilePicture
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import java.util.*

@Composable
fun CompleteProfileScreen(
    viewModel: CompleteProfileScreenViewModel,
    navController: NavHostController,
    appViewModel: BilliardsDrawViewModel
) {

    // Check is Logged
    LaunchedEffect(key1 = "loginCheck", block = {
        if (!appViewModel.isLogged()) {
            navigateClearingAllBackstack(navController, Routes.GeneralApp.route)
        }
    })

    // Context
    val context = LocalContext.current

    // For date picker
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    mCalendar.time = Date()
    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, myYear: Int, myMonth: Int, myDayOfMonth: Int ->
            viewModel.birthdate = "$myDayOfMonth/${myMonth + 1}/$myYear"
        }, mYear, mMonth, mDay
    )

    // Select image profile picture
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.profilePicture = uri
    }

    // UI
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
                    UserProfilePicture(imageURL = viewModel.profilePicture, context) {
                        launcher.launch("image/*")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
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
                                stringResource(id = R.string.username) + "(can't edit afterwards)",
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.background(Color.White)
                    )
                    Spacer(modifier = Modifier.height(1.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                mDatePickerDialog.show()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58))
                        ) {
                            Text(
                                text = context.resources.getString(R.string.birthdate),
                                color = Color.White
                            )
                        }
                        Text(text = "" + viewModel.birthdate)
                    }
                    Spacer(modifier = Modifier.height(1.dp))
                    Button(
                        onClick = {
                            viewModel.completeProfile(
                                appViewModel,
                                context,
                                navController
                            )
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