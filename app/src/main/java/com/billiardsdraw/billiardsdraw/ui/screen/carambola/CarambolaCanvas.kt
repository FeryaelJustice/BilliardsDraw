package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CarambolaCanvas(){
    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
        Text(text = "Canvas")
        Canvas(modifier = Modifier.height(100.dp), onDraw = {

        })
    }
}