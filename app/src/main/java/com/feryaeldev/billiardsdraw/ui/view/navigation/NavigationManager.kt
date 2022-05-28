package com.feryaeldev.billiardsdraw.ui.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.feryaeldev.billiardsdraw.ui.view.screens.MainScreen

@Composable
fun NavigationManager(navController: NavHostController){
    NavHost(navController = navController, startDestination = Routes.MainScreen.route){
        composable(Routes.MainScreen.route){
            MainScreen(navController)
        }
    }
}