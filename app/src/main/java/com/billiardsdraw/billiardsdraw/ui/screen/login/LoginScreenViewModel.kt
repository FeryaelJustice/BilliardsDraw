package com.billiardsdraw.billiardsdraw.ui.screen.login

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
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor() : ViewModel(), LifecycleObserver {

    /*
    private var _email: MutableLiveData<String> = MutableLiveData(null)
    val email: LiveData<String> = _email
    fun setEmail(email: String) {
        _email.value = email
    }

    private var _password: MutableLiveData<String> = MutableLiveData(null)
    val password: LiveData<String> = _password
    fun setPassword(password: String) {
        _password.value = password
    }

    private var _keepSession: MutableLiveData<Boolean> = MutableLiveData(null)
    val keepSession: LiveData<Boolean> = _keepSession
    fun setKeepSession(keepSession: Boolean) {
        _keepSession.value = keepSession
    }

    fun login(): Boolean {
        if ((_email.value?.isNotBlank() == true) && (_password.value?.isNotBlank() == true)){
            return true
        }
    return false
    }
    */

    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var keepSession: Boolean by mutableStateOf(false)

    fun login(
        emaill: String,
        passwordd: String,
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController
    ) {
        try {
            // Check if all fields are not empty
            if (emaill.isNotBlank() && emaill.isNotBlank()) {
                // Lanzar en corutina de view model en IO para no sobrecargar el main thread
                viewModelScope.launch(Dispatchers.IO) {
                    // ESTO PETA LA APP, NO HACER, USAR sharedPrefs porque si compruebas, al iniciar el composable se repinta infinitamente
                    // appViewModel.setLogged(true)

                    // Log user in auth with email and password
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(
                            emaill,
                            passwordd
                        )
                        .addOnSuccessListener { authResult ->
                            // Set firebase user to mainviewmodel mapping it with user domain
                            authResult.user?.toUser()
                                ?.let { usuario ->
                                    appViewModel.setUser(usuario)
                                }
                            // showToastLong(context,authResult.user!!.uid)

                            // Get user from firestore and assign to user domain variable
                            FirebaseFirestore.getInstance()
                                .collection("users").document(
                                    authResult.user!!.uid
                                ).get()
                                .addOnCompleteListener { docSnap ->
                                    val documento = docSnap.result
                                    // Da null pointer al añadir campos birthdate, pool y carambola paints y active y deleted, debe venir a null el docsnap result, arreglar
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
                                // If user auth was logged
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.MenuScreen.route
                                )
                                showToastLong(
                                    context,
                                    "Welcome to Billiards Draw!"
                                )
                            } else {
                                // If user auth was not logged
                                showToastLong(context, "Wrong credentials")
                            }
                        }.addOnFailureListener {
                            // If user auth was not logged
                            showToastLong(context, "Wrong credentials, error logging")
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