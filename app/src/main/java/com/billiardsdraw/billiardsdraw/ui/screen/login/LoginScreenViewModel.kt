package com.billiardsdraw.billiardsdraw.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
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
}