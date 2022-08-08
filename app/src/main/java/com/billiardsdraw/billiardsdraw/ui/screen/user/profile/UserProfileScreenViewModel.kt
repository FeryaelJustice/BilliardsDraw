package com.billiardsdraw.billiardsdraw.ui.screen.user.profile

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigate
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) :
    ViewModel(), LifecycleObserver {

    fun openContactForm(navController: NavHostController) {
        navigate(navController, Routes.ContactScreen.route)
    }

    fun signOut(navController: NavHostController) {
        repository.signOut()
        navigateClearingAllBackstack(navController, Routes.GeneralApp.route)
        viewModelScope.launch(dispatchers.io) {
            repository.setSharedPreferencesBoolean(SharedPrefConstants.IS_LOGGED_KEY, false)
            repository.setSharedPreferencesString(SharedPrefConstants.EMAIL_KEY, "")
            repository.setSharedPreferencesString(SharedPrefConstants.PASSWORD_KEY, "")
            repository.setSharedPreferencesString(SharedPrefConstants.USER_ID_KEY, "")
        }
    }
}