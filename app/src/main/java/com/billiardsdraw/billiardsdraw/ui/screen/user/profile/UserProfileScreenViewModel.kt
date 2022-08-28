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
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.common.serializeToMap
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.domain.model.User
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UserProfileScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) :
    ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    var profilePicture: Uri? by mutableStateOf(null)
    var isEditing: Boolean by mutableStateOf(false)
    var user: User by mutableStateOf(
        User(
            id = null,
            uid = "uid",
            username = "username",
            nickname = "nickname",
            name = "name",
            surnames = "surnames",
            email = "email",
            password = "password",
            age = 18,
            birthdate = Date(),
            country = "Spain",
            carambola_paints = arrayOf(),
            pool_paints = arrayOf(),
            role = "free",
            active = true,
            deleted = true
        )
    )

    fun onCreate(appViewModel: BilliardsDrawViewModel) {
        viewModelScope.launch(dispatchers.io) {
            user = appViewModel.user.value as User
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

    fun saveEdit(context: Context, appViewModel: BilliardsDrawViewModel) {
        viewModelScope.launch(dispatchers.io) {
            appViewModel.setUser(user)
            var success = false
            repository.updateUserInFirebaseFirestore(user.uid, user.serializeToMap()) {
                success = it
            }
            if (success) {
                withContext(dispatchers.main) {
                    showToastShort(context, context.resources.getString(R.string.success))
                }
            }
            isEditing = false
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}