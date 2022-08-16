package com.billiardsdraw.billiardsdraw.ui.screen.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.*
import androidx.navigation.NavHostController
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.R
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.EMAIL_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.IS_LOGGED_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.PASSWORD_KEY
import com.billiardsdraw.billiardsdraw.common.SharedPrefConstants.USER_ID_KEY
import com.billiardsdraw.billiardsdraw.common.findActivity
import com.billiardsdraw.billiardsdraw.coroutine.DispatcherProvider
import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import com.billiardsdraw.billiardsdraw.domain.map.toUser
import com.billiardsdraw.billiardsdraw.ui.navigation.Routes
import com.billiardsdraw.billiardsdraw.ui.navigation.navigateClearingAllBackstack
import com.billiardsdraw.billiardsdraw.ui.util.showToastLong
import com.billiardsdraw.billiardsdraw.ui.util.showToastShort
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val repository: BilliardsDrawRepository,
    private val dispatchers: DispatcherProvider
) :
    ViewModel(), DefaultLifecycleObserver, LifecycleEventObserver {

    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var keepSession: Boolean by mutableStateOf(false)
    private var auth: FirebaseAuth = Firebase.auth

    fun onCreate() {
        keepSession = repository.sharedPreferencesBoolean(IS_LOGGED_KEY)
        email = repository.sharedPreferencesString(EMAIL_KEY)
        password = repository.sharedPreferencesString(PASSWORD_KEY)

        // Check if user is signed in (non-null) and update UI accordingly (google recommended way)
        // val currentUser = auth.currentUser
        // updateUI(currentUser)
    }

    fun signIn(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController,
        isLogged: Boolean
    ) {
        try {
            if (!isLogged) {
                // Check if all fields are not empty
                if (email.isNotBlank() && password.isNotBlank()) {
                    // Log user in auth with email and password
                    login(appViewModel, context, navController, false)
                } else {
                    showToastShort(context, context.resources.getString(R.string.fieldsCantBeEmpty))
                }
            } else {
                // Log user in auth with email and password
                login(appViewModel, context, navController, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            appViewModel.setLoading(false)
        }
    }

    private fun login(
        appViewModel: BilliardsDrawViewModel,
        context: Context,
        navController: NavHostController,
        isLogged: Boolean
    ) {
        // Log user in auth with email and password
        viewModelScope.launch(dispatchers.io) {
            val user = repository.signInWithEmailPassword(email, password)?.toUser()
            if (user == null) {
                withContext(dispatchers.main) {
                    showToastShort(
                        context,
                        context.resources.getString(R.string.internalError)
                    )
                    Log.d("error", "error en user login")
                }
            }
            user?.let { userAuth ->
                withContext(dispatchers.main) {
                    appViewModel.setUser(userAuth)
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
                if (!isLogged) {
                    // Solo si mantener sesion iniciada is checked
                    repository.setSharedPreferencesBoolean(IS_LOGGED_KEY, keepSession)
                    repository.setSharedPreferencesString(EMAIL_KEY, email)
                    repository.setSharedPreferencesString(PASSWORD_KEY, password)
                    repository.setSharedPreferencesString(USER_ID_KEY, userAuth.uid)
                }
                withContext(dispatchers.main) {
                    if (!isLogged) {
                        showToastLong(
                            context,
                            context.resources.getString(R.string.welcome) + " " + context.resources.getString(
                                R.string.app_name
                            )
                        )
                    }
                    navigateClearingAllBackstack(
                        navController,
                        Routes.LoggedApp.route
                    )
                }
            }
        }
    }

    fun handleFacebookAccessToken(
        token: AccessToken,
        context: Context,
        appViewModel: BilliardsDrawViewModel,
        navController: NavHostController
    ) {
        Log.d("facebook", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        context.findActivity()?.let { activity ->
            auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("facebook", "signInWithCredential:success")
                        val user = auth.currentUser

                        viewModelScope.launch(dispatchers.io) {
                            user?.let { firebaseUser ->
                                withContext(dispatchers.main) {
                                    appViewModel.setUser(firebaseUser.toUser())
                                }
                                repository.getUserFromFirebaseFirestore(firebaseUser.uid) { userData ->
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
                                repository.setSharedPreferencesBoolean(IS_LOGGED_KEY, keepSession)
                                repository.setSharedPreferencesString(EMAIL_KEY, email)
                                repository.setSharedPreferencesString(PASSWORD_KEY, password)
                                repository.setSharedPreferencesString(USER_ID_KEY, firebaseUser.uid)
                                withContext(dispatchers.main){
                                    navigateClearingAllBackstack(
                                        navController,
                                        Routes.LoggedApp.route
                                    )
                                }
                            }
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("facebook", "signInWithCredential:failure", task.exception)
                        showToastShort(context, "Authentication failed")
                    }
                }
        }
    }

    suspend fun handleGoogleSignIn(
        context: Context,
        launcher: ActivityResultLauncher<IntentSenderRequest>
    ) {
        val oneTapClient = Identity.getSignInClient(context)
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(context.resources.getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build()
            )
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()

        try {
            // Use await() from https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-play-services
            // Instead of listeners that aren't cleaned up automatically
            val result = oneTapClient.beginSignIn(signInRequest).await()

            // Now construct the IntentSenderRequest the launcher requires
            val intentSenderRequest = IntentSenderRequest.Builder(result.pendingIntent).build()
            launcher.launch(intentSenderRequest)
        } catch (e: Exception) {
            // No saved credentials found. Launch the One Tap sign-up flow, or
            // do nothing and continue presenting the signed-out UI.
            Log.d("LOG", e.message.toString())
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Lifecycle", "onStateChanged")
    }
}