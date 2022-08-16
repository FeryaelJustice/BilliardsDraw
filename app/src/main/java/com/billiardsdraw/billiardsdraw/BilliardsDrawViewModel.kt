package com.billiardsdraw.billiardsdraw

import android.util.Log
import androidx.lifecycle.*
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepositoryImp
import com.billiardsdraw.billiardsdraw.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BilliardsDrawViewModel @Inject constructor(
    private val repository: BilliardsDrawRepositoryImp
    // private val savedStateHandle: SavedStateHandle
) : ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    // Loading
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    // Firebase user
    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user
    fun setUser(user: User) {
        _user.value = user
    }

    fun onCreate() {
        _isLoading.value = true
        // Something of background process
        _isLoading.value = false
    }

    fun isLogged() = repository.sharedPreferencesBoolean(SharedPrefConstants.IS_LOGGED_KEY)
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}