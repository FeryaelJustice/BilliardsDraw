package com.billiardsdraw.billiardsdraw.ui.navigation

import androidx.navigation.NavHostController

fun navigate(navController: NavHostController, route: String) {
    navController.navigate(route)
}

fun navigateClearingAllBackstack(navController: NavHostController, route: String) {
    navController.navigate(route) {
        // navController.graph.findStartDestination().id
        popUpTo(Routes.LoginScreen.route) {
            inclusive = true
        }
    }
}