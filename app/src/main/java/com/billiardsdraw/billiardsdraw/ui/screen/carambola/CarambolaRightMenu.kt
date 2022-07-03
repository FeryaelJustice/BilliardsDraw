package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun CarambolaRightMenu(modifier: Modifier, navController: NavHostController, context: Context) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Text(text = "Carambola Right Menu")
    }
}