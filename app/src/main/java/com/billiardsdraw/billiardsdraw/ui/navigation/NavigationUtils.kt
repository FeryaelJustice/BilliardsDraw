package com.billiardsdraw.billiardsdraw.ui.navigation

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun navigate(navController: NavHostController, route: String){
    navController.navigate(route)
}

fun navigateClearingAllBackstack(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}