package com.billiardsdraw.billiardsdraw.ui.screen.recoveraccount

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecoverAccountScreenViewModel @Inject constructor() : ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {
    var email: String by mutableStateOf("")
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}