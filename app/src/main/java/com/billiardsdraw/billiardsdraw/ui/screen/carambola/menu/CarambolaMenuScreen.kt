package com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes

@Composable
fun CarambolaMenuScreen(navController: NavHostController){
    Button(onClick = { navController.navigate(Routes.CarambolaScreen.route) }) {
        Text(text = "Ir a la screen de carambola")
    }
}

@Preview(name = "Carambola Menu Screen")
@Composable
fun CarambolaMenuScreenPreview(){
    CarambolaMenuScreen(navController = rememberNavController())
}