package com.billiardsdraw.billiardsdraw.ui.screen.user.premium

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserPremiumScreenViewModel @Inject constructor(private val repository: BilliardsDrawRepository): ViewModel(), LifecycleObserver {

    fun signOut(navController: NavHostController) {
        repository.signOut()
        navigateClearingAllBackstack(navController, Routes.LoginScreen.route)
    }
}