package com.billiardsdraw.billiardsdraw.ui.screen.carambola

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarambolaScreenViewModel @Inject constructor(): ViewModel(), DefaultLifecycleObserver,
    LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}