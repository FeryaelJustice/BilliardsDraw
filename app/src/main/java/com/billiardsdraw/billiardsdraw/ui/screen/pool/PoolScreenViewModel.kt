package com.billiardsdraw.billiardsdraw.ui.screen.pool

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PoolScreenViewModel @Inject constructor(): ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}