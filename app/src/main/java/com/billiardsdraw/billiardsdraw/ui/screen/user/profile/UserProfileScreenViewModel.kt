package com.billiardsdraw.billiardsdraw.ui.screen.user.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import coil.ImageLoader
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.common.getImageUriFromBitmap
import com.billiardsdraw.billiardsdraw.common.toBitmap
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider,
    private val imageLoader: ImageLoader
) :
    ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    var profilePicture: Uri? by mutableStateOf(null)

    fun onCreate(appViewModel: BilliardsDrawViewModel) {
        viewModelScope.launch(dispatchers.io) {
            appViewModel.user.value?.uid?.let { getUserProfilePicture(it) }
        }
    }

    private suspend fun getUserProfilePicture(userId: String) {
        repository.getUserProfilePicture(userId) {
            profilePicture = Uri.fromFile(it)
        }
    }

    fun openContactForm(navController: NavHostController) {
        navigateClearingAllBackstack(navController, Routes.ContactScreen.route)
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

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}