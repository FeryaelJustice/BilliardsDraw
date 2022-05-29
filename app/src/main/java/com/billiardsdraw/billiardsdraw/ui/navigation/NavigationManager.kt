package com.billiardsdraw.billiardsdraw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.billiardsdraw.billiardsdraw.ui.screen.MainScreen
import com.billiardsdraw.billiardsdraw.ui.screen.SplashScreen

@Composable
fun NavigationManager(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route){
        composable(Routes.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(Routes.MainScreen.route){
            MainScreen(navController)
        }
    }
}