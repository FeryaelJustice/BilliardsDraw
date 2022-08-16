package com.billiardsdraw.billiardsdraw.ui.screen.user.premium

import android.util.Log
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserPremiumScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    fun signOut(navController: NavHostController) {
        repository.signOut()
        repository.setSharedPreferencesBoolean(SharedPrefConstants.IS_LOGGED_KEY, false)
        navigateClearingAllBackstack(navController, Routes.GeneralApp.route)
        viewModelScope.launch(dispatchers.io) {
            repository.setSharedPreferencesBoolean(SharedPrefConstants.IS_LOGGED_KEY, false)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}