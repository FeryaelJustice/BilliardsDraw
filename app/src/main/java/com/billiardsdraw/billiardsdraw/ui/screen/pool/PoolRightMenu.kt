package com.billiardsdraw.billiardsdraw.ui.screen.pool

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun PoolRightMenu(modifier: Modifier, navController: NavHostController, context: Context){
    Column(modifier = modifier,verticalArrangement = Arrangement.Center) {
        Text(text = "Pool Right Menu")
    }
}