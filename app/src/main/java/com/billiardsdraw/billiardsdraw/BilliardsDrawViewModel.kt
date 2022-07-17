package com.billiardsdraw.billiardsdraw

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.billiardsdraw.billiardsdraw.data.model.User
import com.billiardsdraw.billiardsdraw.domain.usecase.BilliardsDrawUseCase
import com.billiardsdraw.billiardsdraw.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BilliardsDrawViewModel @Inject constructor(
    private val billiardsDrawUseCase: BilliardsDrawUseCase,
    private val usersUseCase: GetUsersUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    val isLoading =  MutableLiveData<Boolean>()

    // Carambola Canvas
    var selectedCarambolaCanvasColor by mutableStateOf("")

    // Pool Canvas
    var selectedPoolCanvasColor by mutableStateOf("")

    // Users
    val users = MutableLiveData<List<User>>()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = billiardsDrawUseCase()
            if (result.users.isNotEmpty()){
                users.postValue(result.users)
                isLoading.postValue(false)
            }
        }
    }

    fun getUsers(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val usersList = usersUseCase()
            if(usersList.isNotEmpty()){
                users.postValue(usersList)
                isLoading.postValue(false)
            }
        }
    }

    fun getUsersFromDB(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val usersList = usersUseCase.getUsersFromDB()
            if(usersList.isNotEmpty()){
                users.postValue(usersList)
                isLoading.postValue(false)
            }
        }
    }
}