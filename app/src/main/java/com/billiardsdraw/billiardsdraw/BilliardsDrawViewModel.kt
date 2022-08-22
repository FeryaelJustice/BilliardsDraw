package com.billiardsdraw.billiardsdraw

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants
import com.billiardsdraw.billiardsdraw.common.md5
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepositoryImp
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.billiardsdraw.billiardsdraw.domain.model.SignInMethod
import com.billiardsdraw.billiardsdraw.domain.model.User
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.facebook.internal.Mutable
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap

@HiltViewModel
class BilliardsDrawViewModel @Inject constructor(
    val repository: BilliardsDrawRepository,
    val dispatchers: DispatcherProvider
) : ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    fun onCreate(navController: NavHostController) {
        _navController.value = navController
    }

    // APP Nav Controller
    private var _navController: MutableLiveData<NavHostController?> = MutableLiveData()
    val navController: LiveData<NavHostController?> = _navController

    // Auth firebase
    var auth: FirebaseAuth by mutableStateOf(Firebase.auth)

    // Firebase user (Only for Google and Facebook sign in support)
    private var _currentUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val currentUser: LiveData<FirebaseUser?> = _currentUser
    fun setCurrentUser(user: FirebaseUser?) {
        _currentUser.value = user
    }

    // Domain user (useful when there's only own sign in method, but still used with google and facebook sign in)
    private var _user: MutableLiveData<User?> = MutableLiveData()
    val user: LiveData<User?> = _user
    fun setUser(user: User) {
        _user.value = user
    }

    // Sign in method
    // We have to share sign in method in repository and shared preferences
    fun getSignInMethodSharedPrefs(): SignInMethod {
        return when (repository.sharedPreferencesString(SharedPrefConstants.SIGN_IN_METHOD_KEY)) {
            "Custom" -> SignInMethod.Custom
            "Google" -> SignInMethod.Google
            else -> SignInMethod.Custom
        }
    }

    fun setSignInMethodSharedPrefs(signInMethod: SignInMethod) {
        when (signInMethod) {
            SignInMethod.Custom -> repository.setSharedPreferencesString(
                SharedPrefConstants.SIGN_IN_METHOD_KEY,
                "Custom"
            )
            SignInMethod.Google -> repository.setSharedPreferencesString(
                SharedPrefConstants.SIGN_IN_METHOD_KEY,
                "Google"
            )
        }
    }

    // Is logged (using repository and shared preferences)
    fun isLogged() = repository.sharedPreferencesBoolean(SharedPrefConstants.IS_LOGGED_KEY)
    fun setIsLogged(isLogged: Boolean) =
        repository.setSharedPreferencesBoolean(SharedPrefConstants.IS_LOGGED_KEY, isLogged)

    // Keep session with repository and shared preferences
    fun isKeepSession() = repository.sharedPreferencesBoolean(SharedPrefConstants.KEEP_SESSION_KEY)
    private fun setKeepSession(keepSession: Boolean) =
        repository.setSharedPreferencesBoolean(SharedPrefConstants.KEEP_SESSION_KEY, keepSession)

    // Lifecycle
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }

    fun signIn(
        signInMethod: SignInMethod,
        context: Context,
        navController: NavHostController,
        emailStr: String?,
        passwordStr: String?,
        keepSession: Boolean?,
        googleSignInClient: GoogleSignInClient,
        authResultLauncher: ActivityResultLauncher<Intent>
    ) {
        try {
            when (signInMethod) {
                SignInMethod.Custom -> {
                    // Log user in auth with email and password
                    // Check if all fields are not empty
                    viewModelScope.launch(dispatchers.io) {
                        val firebaseUser =
                            if (emailStr!!.isNotBlank() && passwordStr!!.isNotBlank()) {
                                repository.signInWithEmailPassword(
                                    emailStr,
                                    passwordStr
                                )
                            } else {
                                FirebaseAuth.getInstance().currentUser
                            }
                        withContext(dispatchers.main) {
                            if (firebaseUser != null) {
                                setCurrentUser(firebaseUser)
                            }
                        }
                        val userDomain = firebaseUser?.toUser()
                        if (userDomain == null) {
                            withContext(dispatchers.main) {
                                showToastShort(
                                    context,
                                    context.resources.getString(R.string.internalError)
                                )
                                Log.d("error", "error en user login")
                            }
                        }
                        userDomain?.let { userAuth ->
                            withContext(dispatchers.main) {
                                setUser(userAuth)
                            }
                            repository.getUserFromFirebaseFirestore(userAuth.uid) { userData ->
                                // User data retrieved
                                user.value?.apply {
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

                            withContext(dispatchers.main) {
                                setSignInMethodSharedPrefs(SignInMethod.Custom)
                                setIsLogged(true)
                                // Solo si mantener sesion iniciada is checked
                                setKeepSession(keepSession!!)
                                repository.setSharedPreferencesString(
                                    SharedPrefConstants.EMAIL_KEY,
                                    emailStr.toString()
                                )
                                repository.setSharedPreferencesString(
                                    SharedPrefConstants.PASSWORD_KEY,
                                    passwordStr.toString()
                                )
                                repository.setSharedPreferencesString(
                                    SharedPrefConstants.USER_ID_KEY,
                                    userAuth.uid
                                )

                                showToastLong(
                                    context,
                                    context.resources.getString(R.string.welcome) + " " + context.resources.getString(
                                        R.string.app_name
                                    )
                                )
                                // After successful login
                                navigateClearingAllBackstack(
                                    navController,
                                    Routes.LoggedApp.route
                                )
                            }
                        }
                    }
                }
                SignInMethod.Google -> {
                    authResultLauncher.launch(googleSignInClient.signInIntent)
                    // Inside contract firebaseAuthWithGoogle method only on successful login assigns sign in method to viewModel
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Sign in en google con token
    fun firebaseAuthWithGoogle(
        activity: Activity,
        auth: FirebaseAuth,
        idToken: String,
        navController: NavHostController,
        context: Context
    ) {
        auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("auth", "signInWithCredential:success")

                    val firebaseUser = auth.currentUser
                    setCurrentUser(firebaseUser)
                    Log.d("auth_id", firebaseUser?.uid ?: "")

                    val userDomain = firebaseUser?.toUser()
                    userDomain?.let { userAuth ->
                        setUser(userAuth)
                        viewModelScope.launch(dispatchers.io) {
                            // if its first login on google, create a new user in fire store
                            if (task.result.additionalUserInfo?.isNewUser == true) {
                                // Add user to firestore
                                val userToAdd: MutableMap<String, Any> = HashMap()
                                userToAdd["uid"] = userAuth.uid
                                userToAdd["username"] = "username " + userAuth.email
                                userToAdd["nickname"] = "nickname"
                                userToAdd["name"] = "name"
                                userToAdd["surnames"] = "surnames"
                                userToAdd["email"] = userAuth.email
                                userToAdd["password"] =
                                    md5("unknown") // google doesn't provide password access
                                userToAdd["age"] = "0"
                                userToAdd["birthdate"] = Date()
                                userToAdd["country"] = "Spain"
                                userToAdd["carambola_paints"] = arrayListOf("1,2")
                                userToAdd["pool_paints"] = arrayListOf("3,4")
                                userToAdd["role"] = "free"
                                userToAdd["active"] = true
                                userToAdd["deleted"] = false
                                repository.createUserInFirebaseFirestore(userAuth.uid, userToAdd) {
                                    if (it) {
                                        Log.d("register", "User created in db")
                                    } else {
                                        Log.d("register", "Failed to create user in db")
                                    }
                                }
                                withContext(dispatchers.main) {
                                    showToastLong(
                                        context,
                                        context.resources.getString(R.string.welcome) + " " + context.resources.getString(
                                            R.string.app_name
                                        )
                                    )
                                    navigateClearingAllBackstack(
                                        navController,
                                        Routes.CompleteProfileScreen.route
                                    )
                                }
                            } else {
                                withContext(dispatchers.main) {
                                    navigateClearingAllBackstack(
                                        navController,
                                        Routes.LoggedApp.route
                                    )
                                }
                            }
                            repository.getUserFromFirebaseFirestore(userAuth.uid) { userData ->
                                // User data retrieved
                                user.value?.apply {
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
                            setIsLogged(true)
                            setSignInMethodSharedPrefs(SignInMethod.Google)
                            repository.setSharedPreferencesBoolean(
                                SharedPrefConstants.KEEP_SESSION_KEY,
                                true
                            )
                            repository.setSharedPreferencesString(
                                SharedPrefConstants.EMAIL_KEY,
                                userAuth.email
                            )
                            repository.setSharedPreferencesString(
                                SharedPrefConstants.PASSWORD_KEY,
                                userAuth.password,
                            )
                            repository.setSharedPreferencesString(
                                SharedPrefConstants.USER_ID_KEY,
                                userAuth.uid
                            )
                        }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(task.exception.toString(), "signInWithCredential:failure")
                }
            }
    }

    fun onlySignOut() {
        when (getSignInMethodSharedPrefs()) {
            SignInMethod.Custom -> {
                // Email sign out
                repository.signOut()
            }
            SignInMethod.Google -> {
                // Google sign out
                auth.signOut()
            }
        }

        // App sign out
        setIsLogged(false)
        _currentUser.value = null
        _user.value = null
        repository.setSharedPreferencesBoolean(SharedPrefConstants.IS_LOGGED_KEY, false)
        repository.setSharedPreferencesString(SharedPrefConstants.EMAIL_KEY, "")
        repository.setSharedPreferencesString(SharedPrefConstants.PASSWORD_KEY, "")
        repository.setSharedPreferencesString(SharedPrefConstants.USER_ID_KEY, "")
    }

    fun signOut(
        navController: NavHostController
    ) {
        onlySignOut()
        navigateClearingAllBackstack(navController, Routes.LoginScreen.route)
    }
}