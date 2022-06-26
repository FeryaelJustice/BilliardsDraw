package com.billiardsdraw.billiardsdraw.ui.screen.pool

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun PoolRightMenu(navController: NavHostController){

}

@Preview(name = "Pool Right Menu")
@Composable
fun PoolRightMenuPreview(){
    PoolRightMenu(navController = rememberNavController())
}