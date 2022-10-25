package com.billiardsdraw.billiardsdraw.ui.screen.carambola.pba

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu.CarambolaMenuScreenViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun CarambolaPBAScreen(
    viewModel: CarambolaMenuScreenViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    appViewModel: BilliardsDrawViewModel
) {
    Text(text = "Carambola PBA Screen")
}