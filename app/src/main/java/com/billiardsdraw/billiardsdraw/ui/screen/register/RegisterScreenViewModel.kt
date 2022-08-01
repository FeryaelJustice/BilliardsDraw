package com.billiardsdraw.billiardsdraw.ui.screen.register

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.common.md5
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class RegisterScreenViewModel @Inject constructor(private val repository: BilliardsDrawRepository) :
    ViewModel(), LifecycleObserver {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var repeatPassword: String by mutableStateOf("")

    fun signUp(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController
    ) {
        try {
            // Check if all fields are not empty
            if (email.isNotBlank() && password.isNotBlank() && repeatPassword.isNotBlank()) {
                if (password == repeatPassword) {
                    // Log user in auth with email and password
                    viewModelScope.launch(Dispatchers.IO) {
                        val user = repository.signUpWithEmailPassword(email, password)?.toUser()
                        if (user == null) {
                            withContext(Dispatchers.Main) {
                                showToastShort(context, "Ha habido un error interno")
                                Log.d("error", "error en user register")
                            }
                        }
                        user?.let { userAuth ->
                            withContext(Dispatchers.Main) {
                                appViewModel.setUser(userAuth)
                            }

                            // Add user to firestore
                            val userToAdd: MutableMap<String, Any> = HashMap()
                            userToAdd["uid"] = userAuth.uid
                            userToAdd["username"] = "username " + userAuth.email
                            userToAdd["nickname"] = "nickname"
                            userToAdd["name"] = "name"
                            userToAdd["surnames"] = "surnames"
                            userToAdd["email"] = userAuth.email
                            userToAdd["password"] = md5(password)
                            userToAdd["age"] = 18
                            userToAdd["birthdate"] = Date()
                            userToAdd["country"] = "Spain"
                            userToAdd["carambola_paints"] = arrayListOf("1,2")
                            userToAdd["pool_paints"] = arrayListOf("3,4")
                            userToAdd["role"] = "free"
                            userToAdd["active"] = true
                            userToAdd["deleted"] = false

                            repository.createUserInFirebaseFirestore(userToAdd) {
                                if (it) {
                                    Log.d("register", "User created in db")
                                } else {
                                    Log.d("register", "Failed to create user in db")
                                }
                            }

                            repository.getUserFromFirebaseFirestore(userAuth.uid) { userData ->
                                // User data retrieved
                                appViewModel.user.value?.apply {
                                    username = userData.username
                                    nickname = userData.nickname
                                    name = userData.name
                                    surnames = userData.surnames
                                    password = userData.password
                                    age = userData.age
                                    country = userData.country
                                    role = userData.role
                                }
                            }

                            withContext(Dispatchers.Main) {
                                showToastLong(context, "Welcome to Billiards Draw!")
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.LoggedApp.route
                                )
                            }
                        }
                    }
                } else {
                    showToastShort(context, "Las contraseñas no coinciden")
                }
            } else {
                showToastShort(context, "Los campos no pueden estar vacíos")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}