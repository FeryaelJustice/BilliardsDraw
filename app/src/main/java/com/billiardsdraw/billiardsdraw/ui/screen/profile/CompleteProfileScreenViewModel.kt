package com.billiardsdraw.billiardsdraw.ui.screen.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CompleteProfileScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel(), LifecycleObserver {

    var age: String by mutableStateOf("18")
    var birthdate: Date by mutableStateOf(Date())
    var country: String by mutableStateOf("Spain")
    var name: String by mutableStateOf("")
    var surnames: String by mutableStateOf("")
    var username: String by mutableStateOf("") // Specify that this can't be changed afterwards
    var nickname: String by mutableStateOf("")

    fun completeProfile(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController
    ) {

    }
}