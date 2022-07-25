package com.billiardsdraw.billiardsdraw

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BilliardsDrawViewModel @Inject constructor(
    // private val sharedPreferences: SharedPreferences
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

    // Firebase user
    private var _user: MutableLiveData<FirebaseUser> = MutableLiveData()
    val user: LiveData<FirebaseUser> = _user
    fun setUser(user: FirebaseUser){
        _user.value = user
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