package com.feryaeldev.billiardsdraw.ui.view.navigation

sealed class Routes(val route: String) {
    object MainScreen : Routes("MainScreen")
}
