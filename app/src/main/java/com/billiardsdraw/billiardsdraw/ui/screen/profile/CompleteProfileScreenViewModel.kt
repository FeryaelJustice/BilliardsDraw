package com.billiardsdraw.billiardsdraw.ui.screen.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.common.md5
import com.billiardsdraw.billiardsdraw.common.toDate
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class CompleteProfileScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    var profilePicture: Uri? by mutableStateOf(null)
    var age: String by mutableStateOf("")
    var birthdate: String by mutableStateOf("") // Calendar picker
    var country: String by mutableStateOf("")
    var name: String by mutableStateOf("")
    var surnames: String by mutableStateOf("")
    var username: String by mutableStateOf("") // Specify that this can't be changed afterwards
    var nickname: String by mutableStateOf("")

    private fun areAllFieldsNotEmpty(): Boolean =
        age.isNotEmpty() && birthdate.isNotEmpty() && country.isNotEmpty() && name.isNotEmpty() && surnames.isNotEmpty() && username.isNotEmpty() && nickname.isNotEmpty()

    fun completeProfile(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController
    ) {
        if (areAllFieldsNotEmpty()) {
            // Add user to fire store Helper
            val userToAdd: MutableMap<String, Any> = HashMap()
            userToAdd["uid"] = repository.sharedPreferencesString(SharedPrefConstants.USER_ID_KEY)
            userToAdd["username"] = username
            userToAdd["nickname"] = nickname
            userToAdd["name"] = name
            userToAdd["surnames"] = surnames
            userToAdd["email"] = repository.sharedPreferencesString(SharedPrefConstants.EMAIL_KEY)
            userToAdd["password"] =
                md5(repository.sharedPreferencesString(SharedPrefConstants.PASSWORD_KEY))
            userToAdd["age"] = age
            userToAdd["birthdate"] = birthdate.toDate() ?: Date()
            userToAdd["country"] = country
            userToAdd["carambola_paints"] = arrayListOf("")
            userToAdd["pool_paints"] = arrayListOf("")
            userToAdd["role"] = "free"
            userToAdd["active"] = true
            userToAdd["deleted"] = false

            viewModelScope.launch(dispatchers.io) {
                // Update user
                repository.updateUserInFirebaseFirestore(
                    repository.sharedPreferencesString(
                        SharedPrefConstants.USER_ID_KEY
                    ), userToAdd
                ) {
                    if (it) {
                        Log.d("register", "User updated in db")
                    } else {
                        Log.d("register", "Failed to update user in db")
                    }
                }

                if (repository.uploadUserProfilePicture(
                        repository.sharedPreferencesString(
                            SharedPrefConstants.USER_ID_KEY
                        ), profilePicture
                    )
                ) {
                    withContext(dispatchers.main) {
                        showToastShort(context, "Profile picture created")
                    }
                }

                // Retrieve user data
                repository.getUserFromFirebaseFirestore(
                    repository.sharedPreferencesString(
                        SharedPrefConstants.USER_ID_KEY
                    )
                ) { userData ->
                    // User data retrieved
                    appViewModel.user.value?.apply {
                        username = userData.username
                        nickname = userData.nickname
                        name = userData.name
                        surnames = userData.surnames
                        password = userData.password
                        age = userData.age
                        country = userData.country
                        role = userData.role
                    }
                }

                // Navigate
                withContext(dispatchers.main) {
                    navigateClearingAllBackstack(navController, Routes.LoggedApp.route)
                }
            }
        } else {
            showToastShort(context, context.resources.getString(R.string.fieldsCantBeEmpty))
        }

    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}