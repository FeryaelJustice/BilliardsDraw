package com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun CarambolaMenuScreen(navController: NavHostController){

}

@Preview(name = "Carambola Menu Screen")
@Composable
fun CarambolaMenuScreenPreview(){
    CarambolaMenuScreen(navController = rememberNavController())
}