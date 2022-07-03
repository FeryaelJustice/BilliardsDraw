package com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu

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
fun CarambolaMenuScreen(viewModel: BilliardsDrawViewModel, navController: NavHostController) {
    Button(onClick = {
        navigateClearingAllBackstack(
            navController,
            Routes.CarambolaScreen.route
        )
    }) {
        Text(text = "Ir a la screen de carambola")
    }
}