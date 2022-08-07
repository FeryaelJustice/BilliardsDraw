package com.billiardsdraw.billiardsdraw.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.EMAIL_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.IS_LOGGED_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.PASSWORD_KEY
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) :
    ViewModel(), LifecycleObserver {

    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var keepSession: Boolean by mutableStateOf(false)

    fun onCreate() {
        keepSession = repository.sharedPreferencesBoolean(IS_LOGGED_KEY)
        email = repository.sharedPreferencesString(EMAIL_KEY)
        password = repository.sharedPreferencesString(PASSWORD_KEY)
    }

    fun isLogged() = repository.sharedPreferencesBoolean(IS_LOGGED_KEY)

    fun signIn(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController,
        isLogged: Boolean
    ) {
        try {
            if (!isLogged) {
                // Check if all fields are not empty
                if (email.isNotBlank() && password.isNotBlank()) {
                    // Log user in auth with email and password
                    login(appViewModel, context, navController, false)
                } else {
                    showToastShort(context, context.resources.getString(R.string.fieldsCantBeEmpty))
                }
            } else {
                // Log user in auth with email and password
                login(appViewModel, context, navController, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            appViewModel.setLoading(false)
        }
    }

    private fun login(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController,
        isLogged: Boolean
    ) {
        // Log user in auth with email and password
        viewModelScope.launch(dispatchers.io) {
            val user = repository.signInWithEmailPassword(email, password)?.toUser()
            if (user == null) {
                withContext(dispatchers.main) {
                    showToastShort(
                        context,
                        context.resources.getString(R.string.internalError)
                    )
                    Log.d("error", "error en user login")
                }
            }
            user?.let { userAuth ->
                withContext(dispatchers.main) {
                    appViewModel.setUser(userAuth)
                }
                repository.getUserFromFirebaseFirestore(userAuth.uid) { userData ->
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
                if (!isLogged) {
                    // Solo si mantener sesion iniciada is checked
                    repository.setSharedPreferencesBoolean(IS_LOGGED_KEY, keepSession)
                    repository.setSharedPreferencesString(EMAIL_KEY, email)
                    repository.setSharedPreferencesString(PASSWORD_KEY, password)
                }
                withContext(dispatchers.main) {
                    if (!isLogged) {
                        showToastLong(
                            context,
                            context.resources.getString(R.string.welcome) + " " + context.resources.getString(
                                R.string.app_name
                            )
                        )
                    }
                    navigateClearingAllBackstack(
                        navController,
                        Routes.LoggedApp.route
                    )
                }
            }
        }
    }
}