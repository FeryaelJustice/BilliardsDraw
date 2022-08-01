package com.billiardsdraw.billiardsdraw.ui.screen.user.profile

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(private val repository: BilliardsDrawRepository) :
    ViewModel(), LifecycleObserver {

    fun signOut(navController: NavHostController) {
        repository.signOut()
        navigateClearingAllBackstack(navController, Routes.LoginScreen.route)
    }
}