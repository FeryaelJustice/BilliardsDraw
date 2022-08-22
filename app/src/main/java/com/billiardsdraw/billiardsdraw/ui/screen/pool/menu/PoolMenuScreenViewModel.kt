package com.billiardsdraw.billiardsdraw.ui.screen.pool.menu

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoolMenuScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider,
) : ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    var profilePicture: Uri? by mutableStateOf(Uri.parse(""))

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

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}