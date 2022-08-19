package com.billiardsdraw.billiardsdraw.ui.screen.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.*
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.EMAIL_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.IS_LOGGED_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.KEEP_SESSION_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.PASSWORD_KEY
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) :
    ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var passwordVisible: Boolean by mutableStateOf(false)
    var keepSession: Boolean by mutableStateOf(false)

    fun onCreate() {
        keepSession = repository.sharedPreferencesBoolean(KEEP_SESSION_KEY)
        if (keepSession) {
            email = repository.sharedPreferencesString(EMAIL_KEY)
            password = repository.sharedPreferencesString(PASSWORD_KEY)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}