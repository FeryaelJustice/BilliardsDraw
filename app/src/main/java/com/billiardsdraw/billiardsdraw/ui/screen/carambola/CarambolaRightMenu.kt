package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun CarambolaRightMenu(modifier: Modifier, navController: NavHostController, context: Context) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Text(text = "Carambola Right Menu")
    }
}