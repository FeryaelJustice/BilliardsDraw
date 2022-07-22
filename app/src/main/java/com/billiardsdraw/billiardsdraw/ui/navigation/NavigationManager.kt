package com.billiardsdraw.billiardsdraw.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
fun NavigationManager(viewModel: BilliardsDrawViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Routes.LoginScreen.route) {
            LoginScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.RecoverAccountScreen.route) {
            RecoverAccountScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.MenuScreen.route) {
            MenuScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.CarambolaScreen.route) {
            CarambolaScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.PoolScreen.route) {
            PoolScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.CarambolaMenuScreen.route) {
            CarambolaMenuScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.PoolMenuScreen.route) {
            PoolMenuScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.UserProfileScreen.route) {
            UserProfileScreen(hiltViewModel(), navController, viewModel)
        }
        composable(Routes.UserPremiumScreen.route) {
            UserPremiumScreen(hiltViewModel(), navController, viewModel)
        }
    }
}