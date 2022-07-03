package com.billiardsdraw.billiardsdraw.ui.screen.pool

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PoolRightMenu(modifier: Modifier, navController: NavHostController, context: Context){
    Column(modifier = modifier,verticalArrangement = Arrangement.Center) {
        Text(text = "Pool Right Menu")
    }
}