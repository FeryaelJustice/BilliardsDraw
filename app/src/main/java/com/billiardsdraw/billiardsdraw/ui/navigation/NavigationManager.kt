package com.billiardsdraw.billiardsdraw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.billiardsdraw.billiardsdraw.ui.screen.splash.SplashScreen
import com.billiardsdraw.billiardsdraw.ui.screen.login.LoginScreen
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.CarambolaScreen
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu.CarambolaMenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.menu.MenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.PoolScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.menu.PoolMenuScreen

@Composable
fun NavigationManager(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route){
        composable(Routes.SplashScreen.route){
            SplashScreen(navController)
        }
        composable(Routes.LoginScreen.route){
            LoginScreen(navController)
        }
        composable(Routes.MenuScreen.route){
            MenuScreen(navController)
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