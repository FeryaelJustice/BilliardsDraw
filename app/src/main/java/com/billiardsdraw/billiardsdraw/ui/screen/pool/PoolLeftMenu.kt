package com.billiardsdraw.billiardsdraw.ui.screen.pool

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PoolLeftMenu(navController: NavHostController){

}

@Preview(name = "Pool Left Menu")
@Composable
fun PoolLeftMenuPreview(){
    PoolLeftMenu(navController = rememberNavController())
}