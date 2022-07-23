package com.billiardsdraw.billiardsdraw.ui.screen.register

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterScreenViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var repeatPassword: String by mutableStateOf("")

    fun signIn(context: Context): Boolean {
        var success = false
        if (email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank()) {
            if (password == repeatPassword) {
                success = true
            } else {
                showToastLong(context, "Passwords don't match!")
            }
        }
        return success
    }
}