package com.billiardsdraw.billiardsdraw.ui.screen.pool.menu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PoolMenuScreen(navController: NavHostController){

}

@Preview(name = "Pool Menu Screen")
@Composable
fun PoolMenuScreenPreview(){
    PoolMenuScreen(navController = rememberNavController())
}