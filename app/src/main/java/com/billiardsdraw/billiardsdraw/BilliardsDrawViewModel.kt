package com.billiardsdraw.billiardsdraw

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.billiardsdraw.billiardsdraw.common.APP_API_KEY
import com.billiardsdraw.billiardsdraw.data.model.User
import com.billiardsdraw.billiardsdraw.domain.usecase.BilliardsDrawUseCase
import com.billiardsdraw.billiardsdraw.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BilliardsDrawViewModel @Inject constructor(
    // private val billiardsDrawUseCase: BilliardsDrawUseCase,
    // private val usersUseCase: GetUsersUseCase,
    // private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    // Loading
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    // Users
    /*
    private var _users: MutableLiveData<List<User>> = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    fun setUsers(users: List<User>){
        _users.value = users
    }
    */

    fun onCreate() {
        viewModelScope.launch {
            /*
            setLoading(false)
            val result = billiardsDrawUseCase()
            if (result.users.isNotEmpty()) {
                users.postValue(result.users)
                setLoading(false)
            }
            */
            _isLoading.value = true
            //_users.value = mutableListOf()
        }
    }

    // Users
    /*
    fun getUsers() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val usersList = usersUseCase()
            if (usersList.isNotEmpty()) {
                users.postValue(usersList)
                isLoading.postValue(false)
            }
        }
    }
    fun getUsersFromDB() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val usersList = usersUseCase.getUsersFromDB()
            if (usersList.isNotEmpty()) {
                users.postValue(usersList)
                isLoading.postValue(false)
            }
        }
    }
    */
}