package com.billiardsdraw.billiardsdraw.ui.screen.carambola.mypositions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu.CarambolaMenuScreenViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun CarambolaMyPositionsScreen(
    viewModel: CarambolaMenuScreenViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    appViewModel: BilliardsDrawViewModel
) {
    Text(text = "Carambola My Positions Screen")
}