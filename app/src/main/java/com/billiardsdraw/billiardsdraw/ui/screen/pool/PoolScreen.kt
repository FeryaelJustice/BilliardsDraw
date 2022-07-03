package com.billiardsdraw.billiardsdraw.ui.screen.pool

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.R
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@Composable
fun PoolScreen(navController: NavHostController){
    // Context
    val context = LocalContext.current
    // Color picker
    val wheelController = rememberColorPickerController()
    val wheelVisible = rememberSaveable { mutableStateOf(false) }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Card(
            elevation = 0.dp,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.pool_blue),
                contentDescription = "Fondo pool screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            // Lazy hace que librerias externas no se vean
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround) {
                PoolLeftMenu(
                    width = 140.dp,
                    navController = navController,
                    context = context,
                    wheelController = wheelController,
                    wheelVisible = wheelVisible.value,
                    onWheelVisibleClick = {
                        wheelVisible.value = !wheelVisible.value
                    })
                PoolCanvas()
                PoolRightMenu(width = 140.dp, navController = navController, context = context)
            }
        }
    }
}

@Preview(name = "Pool Screen")
@Composable
fun PoolScreenPreview(){
    PoolScreen(navController = rememberNavController())
}