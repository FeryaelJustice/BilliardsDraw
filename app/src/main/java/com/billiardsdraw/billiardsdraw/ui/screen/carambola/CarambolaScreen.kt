package com.billiardsdraw.billiardsdraw.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.CarambolaCanvas
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.CarambolaLeftMenu
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.CarambolaRightMenu
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarambolaScreen(navController: NavHostController) {
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
                painter = painterResource(id = R.drawable.carambola_azul),
                contentDescription = "Fondo carambola screen",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            // Lazy hace que librerias externas no se vean
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceAround) {
                CarambolaLeftMenu(
                    width = 140.dp,
                    navController = navController,
                    context = context,
                    wheelController = wheelController,
                    wheelVisible = wheelVisible.value,
                    onWheelVisibleClick = {
                        wheelVisible.value = !wheelVisible.value
                    })
                CarambolaCanvas()
                CarambolaRightMenu(width = 140.dp, navController = navController, context = context)
            }
        }
    }
}

@Preview(name = "Carambola Screen")
@Composable
fun CarambolaScreenPreview() {
    CarambolaScreen(navController = rememberNavController())
}