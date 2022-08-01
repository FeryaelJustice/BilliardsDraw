package com.billiardsdraw.billiardsdraw

import androidx.lifecycle.*
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepositoryImp
import com.billiardsdraw.billiardsdraw.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BilliardsDrawViewModel @Inject constructor(
private val repository: BilliardsDrawRepositoryImp
    // private val savedStateHandle: SavedStateHandle
) : ViewModel(), LifecycleObserver {

    // Loading
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading
    fun setLoading(loading: Boolean) {
        _isLoading.value = loading
    }

    // Firebase user
    private var _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user
    fun setUser(user: User){
        _user.value = user
    }

    fun onCreate() {
        viewModelScope.launch {
            _isLoading.value = true
            /*
            _isLoading.value = true
            val result = billiardsDrawUseCase()
            if (result.users.isNotEmpty()) {
                users.postValue(result.users)
                _isLoading.value = false
            }
            */
        }
    }

    // Shared prefs
    /*
    fun sharedPrefsUsage(){
        sharedPreferences.edit().putString("firstStoredString", "this is the content").apply()
        val firstStoredString = sharedPreferences.getString("firstStoredString", "")
    }
    */

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