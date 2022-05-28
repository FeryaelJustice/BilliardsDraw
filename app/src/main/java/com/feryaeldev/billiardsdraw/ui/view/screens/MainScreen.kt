package com.feryaeldev.billiardsdraw.ui.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.feryaeldev.billiardsdraw.R

@Composable
fun MainScreen(navController: NavHostController) {
    Text(text = stringResource(id = R.string.app_name))
}