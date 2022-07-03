package com.billiardsdraw.billiardsdraw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class BilliardsDrawViewModel : ViewModel(){
    // Carambola Canvas
    var selectedCarambolaCanvasColor by mutableStateOf("")
    // Pool Canvas
    var selectedPoolCanvasColor by mutableStateOf("")
}