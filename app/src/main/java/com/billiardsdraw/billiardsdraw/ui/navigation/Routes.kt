package com.billiardsdraw.billiardsdraw.ui.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("SplashScreen")
    object LoginScreen : Routes("LoginScreen")
    object CarambolaScreen : Routes("CarambolaScreen")
    object PoolScreen : Routes("PoolScreen")
    object CarambolaMenuScreen : Routes("CarambolaMenuScreen")
    object PoolMenuScreen : Routes("PoolMenuScreen")
}
