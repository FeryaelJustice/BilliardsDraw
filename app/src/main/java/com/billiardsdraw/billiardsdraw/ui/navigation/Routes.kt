package com.billiardsdraw.billiardsdraw.ui.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("SplashScreen")
    object MainScreen : Routes("MainScreen")
    object BoardScreen : Routes("BoardScreen")
}
