package com.billiardsdraw.billiardsdraw.ui.screen.pool.menu

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes

@Composable
fun PoolMenuScreen(navController: NavHostController){

    Button(onClick = { navController.navigate(Routes.PoolScreen.route) }) {
        Text(text = "Ir a la screen de pool")
    }
}

@Preview(name = "Pool Menu Screen")
@Composable
fun PoolMenuScreenPreview(){
    PoolMenuScreen(navController = rememberNavController())
}