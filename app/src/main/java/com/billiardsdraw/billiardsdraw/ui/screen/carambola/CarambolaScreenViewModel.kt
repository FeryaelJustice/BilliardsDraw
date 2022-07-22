package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarambolaScreenViewModel @Inject constructor(): ViewModel(), LifecycleObserver {

    // Carambola Canvas
    var selectedCarambolaCanvasColor by mutableStateOf("")
}