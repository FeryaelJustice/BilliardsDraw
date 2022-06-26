package com.billiardsdraw.billiardsdraw.ui.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("SplashScreen")
    object LoginScreen : Routes("LoginScreen")
    object MenuScreen : Routes("MenuScreen")
    object CarambolaScreen : Routes("CarambolaScreen")
    object CarambolaMenuScreen : Routes("CarambolaMenuScreen")
    object PoolScreen : Routes("PoolScreen")
    object PoolMenuScreen : Routes("PoolMenuScreen")
}
