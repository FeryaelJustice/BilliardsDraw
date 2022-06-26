package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.ui.screen.CarambolaScreen

@Composable
fun CarambolaMenuScreen(navController: NavHostController){

}

@Preview(name = "Carambola Menu Screen")
@Composable
fun CarambolaMenuScreenPreview(){
    CarambolaScreen(navController = rememberNavController())
}