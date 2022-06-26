package com.billiardsdraw.billiardsdraw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.billiardsdraw.billiardsdraw.ui.screen.CarambolaScreen
import com.billiardsdraw.billiardsdraw.ui.screen.MainScreen
import com.billiardsdraw.billiardsdraw.ui.screen.SplashScreen
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.CarambolaMenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.PoolMenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.PoolScreen

@Composable
fun NavigationManager(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route){
        composable(Routes.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(Routes.MainScreen.route){
            MainScreen(navController)
        }
        composable(Routes.CarambolaScreen.route) {
            CarambolaScreen(navController)
        }
        composable(Routes.PoolScreen.route) {
            PoolScreen(navController)
        }
        composable(Routes.CarambolaMenuScreen.route) {
            CarambolaMenuScreen(navController)
        }
        composable(Routes.PoolMenuScreen.route) {
            PoolMenuScreen(navController)
        }
    }
}