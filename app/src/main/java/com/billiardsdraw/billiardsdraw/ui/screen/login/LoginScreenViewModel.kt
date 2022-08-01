package com.billiardsdraw.billiardsdraw.ui.screen.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.IS_LOGGED_KEY
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

    /*
    private var _email: MutableLiveData<String> = MutableLiveData(null)
    val email: LiveData<String> = _email
    fun setEmail(email: String) {
        _email.value = email
    }

    private var _password: MutableLiveData<String> = MutableLiveData(null)
    val password: LiveData<String> = _password
    fun setPassword(password: String) {
        _password.value = password
    }

    private var _keepSession: MutableLiveData<Boolean> = MutableLiveData(null)
    val keepSession: LiveData<Boolean> = _keepSession
    fun setKeepSession(keepSession: Boolean) {
        _keepSession.value = keepSession
    }

    fun login(): Boolean {
        if ((_email.value?.isNotBlank() == true) && (_password.value?.isNotBlank() == true)){
            return true
        }
    return false
    }
    */

    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var keepSession: Boolean by mutableStateOf(false)

    fun onCreate() {
        keepSession = repository.sharedPreferencesBoolean(IS_LOGGED_KEY)
    }

    fun signIn(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController
    ) {
        try {
            // Check if all fields are not empty
            if (email.isNotBlank() && password.isNotBlank()) {
                // Log user in auth with email and password
                viewModelScope.launch(dispatchers.io) {
                    val user = repository.signInWithEmailPassword(email, password)?.toUser()
                    if (user == null) {
                        withContext(dispatchers.main) {
                            showToastShort(context, "Ha habido un error interno")
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

                        // Solo si mantener sesion iniciada is checked
                        repository.setSharedPreferencesBoolean(IS_LOGGED_KEY, keepSession)

                        withContext(dispatchers.main) {
                            showToastLong(context, "Welcome to Billiards Draw!")
                            navigateClearingAllBackstack(
                                navController,
                                Routes.LoggedApp.route
                            )
                        }
                    }
                }
            } else {
                showToastShort(context, "Los campos no pueden estar vacíos")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun isLogged() = repository.sharedPreferencesBoolean(IS_LOGGED_KEY)
}