package com.billiardsdraw.billiardsdraw.ui.screen.pool.menu

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack

@Composable
fun PoolMenuScreen(viewModel: BilliardsDrawViewModel, navController: NavHostController) {
    Button(onClick = { navigateClearingAllBackstack(navController, Routes.PoolScreen.route) }) {
        Text(text = "Ir a la screen de pool")
    }
}