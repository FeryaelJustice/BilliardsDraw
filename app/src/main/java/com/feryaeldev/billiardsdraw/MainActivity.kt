package com.feryaeldev.billiardsdraw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.feryaeldev.billiardsdraw.ui.theme.BilliardsDrawTheme
import com.feryaeldev.billiardsdraw.ui.view.navigation.BilliardsDrawTopBar
import com.feryaeldev.billiardsdraw.ui.view.navigation.NavigationManager

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            BilliardsDrawTheme {
                BilliardsDrawApp(navController)
            }
        }
    }

    @Composable
    fun BilliardsDrawApp(navController: NavHostController) {
        Scaffold(modifier = Modifier.fillMaxSize(), {
            BilliardsDrawTopBar(
                navController = navController
            )
        }) { innerPadding ->
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                NavigationManager(navController = navController)
            }
        }
    }
}