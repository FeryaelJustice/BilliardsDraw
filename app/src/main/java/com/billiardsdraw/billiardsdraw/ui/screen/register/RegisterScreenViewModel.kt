package com.billiardsdraw.billiardsdraw.ui.screen.register

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
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
import java.lang.Exception
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class RegisterScreenViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var repeatPassword: String by mutableStateOf("")

    fun signIn(
        emaill: String,
        passwordd: String,
        repeatPasswordd: String,
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController
    ) {
        try {
            // Check if all fields are not empty
            if (emaill.isNotBlank() && emaill.isNotBlank() && repeatPasswordd.isNotBlank()) {
                // Check password and repeat password are equal
                if (passwordd != repeatPasswordd) {
                    showToastLong(context, "Passwords don't match!")
                    return
                }
                // Lanzar en corutina de view model en IO para no sobrecargar el main thread
                viewModelScope.launch(Dispatchers.IO) {
                    // ESTO PETA LA APP, NO HACER, USAR sharedPrefs porque si compruebas, al iniciar el composable se repinta infinitamente
                    // appViewModel.setLogged(true)

                    // Create user in auth with email and password
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(
                            emaill,
                            passwordd
                        )
                        .addOnSuccessListener { authResult ->
                            // Set firebase user to mainviewmodel mapping it with user domain
                            authResult.user?.toUser()
                                ?.let { usuario ->
                                    appViewModel.setUser(usuario)
                                }

                            // Add user to firestore
                            val userToAdd: MutableMap<String, Any> = HashMap()
                            userToAdd["uid"] = authResult.user!!.uid
                            userToAdd["username"] = "username " + authResult.user!!.email
                            userToAdd["nickname"] = "nickname"
                            userToAdd["name"] = "name"
                            userToAdd["surnames"] = "surnames"
                            userToAdd["email"] = authResult.user!!.email.toString()
                            userToAdd["password"] = "hashedpassword"
                            userToAdd["age"] = 18
                            userToAdd["birthdate"] = Date()
                            userToAdd["country"] = "Spain"
                            userToAdd["carambola_paints"] = arrayListOf("1,2")
                            userToAdd["pool_paints"] = arrayListOf("3,4")
                            userToAdd["role"] = "free"
                            userToAdd["active"] = true
                            userToAdd["deleted"] = false

                            FirebaseFirestore.getInstance().collection("users").add(userToAdd)
                                .addOnCompleteListener {
                                    showToastShort(context, "User created in db")
                                }.addOnFailureListener {
                                    showToastShort(context, "Failed to create user in db")
                                }

                            // Get user from firestore and assign to user domain variable
                            FirebaseFirestore.getInstance()
                                .collection("users").document(
                                    authResult.user!!.uid
                                ).get()
                                .addOnCompleteListener { docSnap ->
                                    val documento = docSnap.result
                                    appViewModel.user.value?.apply {
                                        username =
                                            documento.getString("username")
                                                .toString()
                                        nickname =
                                            documento.getString("nickname")
                                                .toString()
                                        name =
                                            documento.getString("name")
                                                .toString()
                                        surnames =
                                            documento.getString("surnames")
                                                .toString()
                                        password =
                                            documento.getString("password")
                                                .toString()
                                        age = documento.getLong("age")
                                            ?.toInt() ?: 0
                                        country =
                                            documento.getString("country")
                                                .toString()
                                        role =
                                            documento.getString("role")
                                                .toString()
                                    }
                                }
                        }
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                // If user auth was registered, do
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.LoggedApp.route
                                )
                                showToastLong(
                                    context, "Welcome to Billiards Draw!"
                                )
                            } else {
                                // If user auth was not registered
                                showToastLong(context, "Password must be longer")
                            }
                        }.addOnFailureListener {
                            // If user auth was not registered
                            showToastLong(context, "User already registered")
                        }
                }
            } else {
                showToastShort(context, "Some field is empty.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}