package com.billiardsdraw.billiardsdraw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.ui.screen.splash.SplashScreen
import com.billiardsdraw.billiardsdraw.ui.screen.login.LoginScreen
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.CarambolaScreen
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu.CarambolaMenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.menu.MenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.PoolScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.menu.PoolMenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.recoveraccount.RecoverAccountScreen
import com.billiardsdraw.billiardsdraw.ui.screen.register.RegisterScreen
import com.billiardsdraw.billiardsdraw.ui.screen.user.premium.UserPremiumScreen
import com.billiardsdraw.billiardsdraw.ui.screen.user.profile.UserProfileScreen

@Composable
fun NavigationManager(viewModel: BilliardsDrawViewModel,navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route){
        composable(Routes.SplashScreen.route){
            SplashScreen(viewModel,navController)
        }
        composable(Routes.LoginScreen.route){
            LoginScreen(viewModel,navController)
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(viewModel,navController)
        }
        composable(Routes.RecoverAccountScreen.route) {
            RecoverAccountScreen(viewModel, navController)
        }
        composable(Routes.MenuScreen.route){
            MenuScreen(viewModel,navController)
        }
        composable(Routes.CarambolaScreen.route) {
            CarambolaScreen(viewModel,navController)
        }
        composable(Routes.PoolScreen.route) {
            PoolScreen(viewModel,navController)
        }
        composable(Routes.CarambolaMenuScreen.route) {
            CarambolaMenuScreen(viewModel,navController)
        }
        composable(Routes.PoolMenuScreen.route) {
            PoolMenuScreen(viewModel,navController)
        }
        composable(Routes.UserProfileScreen.route) {
            UserProfileScreen(viewModel, navController)
        }
        composable(Routes.UserPremiumScreen.route) {
            UserPremiumScreen(viewModel,navController)
        }
    }
}