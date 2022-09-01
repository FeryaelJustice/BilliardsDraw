package com.billiardsdraw.billiardsdraw

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.billiardsdraw.billiardsdraw.common.*
import com.billiardsdraw.billiardsdraw.domain.model.SignInMethod
import com.billiardsdraw.billiardsdraw.ui.navigation.*
import com.billiardsdraw.billiardsdraw.ui.theme.BilliardsDrawTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class BilliardsDraw : ComponentActivity() {

    // APP VIEW MODEL
    private val viewModel: BilliardsDrawViewModel by viewModels()

    // Google Auth
    private lateinit var googleSignInClient: GoogleSignInClient // Client google sign in
    private lateinit var authResultLauncher: ActivityResultLauncher<Intent> // Contract google sign in

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Google Play Services (pay)
        initGooglePlayBilling(context = this)

        // AUTHENTICATION
        // Google
        initGoogleAuth()

        // APP
        setContent {
            // Navigation
            val navController = rememberNavController()
            // CoroutineScope
            val coroutineScope = rememberCoroutineScope()
            // Get window info (responsiveness) only used to assign it in view model, not in ui
            val windowInfo = rememberWindowInfo()
            // Assign app navigation and coroutineScope to viewModel
            LaunchedEffect(Unit) {
                viewModel.onCreate(navController, coroutineScope, windowInfo)
            }
            // App
            BilliardsDrawTheme {
                BilliardsDrawApp(viewModel, navController, coroutineScope)
            }
        }
    }

    override fun onDestroy() {
        // Only sign out if we are in custom login type, not with google and facebook type
        if (!viewModel.isKeepSession() && (viewModel.getSignInMethodSharedPrefs() != SignInMethod.Google)) {
            viewModel.onlySignOut()
        }
        super.onDestroy()
    }

    private fun initGoogleAuth() {
        viewModel.auth.value?.addAuthStateListener { auth ->
            Log.d("auth", "addAuthStateListener: ${auth.currentUser}")
            viewModel.setCurrentUser(auth.currentUser)
        }

        // R.string.default_web_client_id is created automatically as per google-services.json
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignInClient.signOut().addOnSuccessListener {
            // Sign out
        }
        // Google auth handler
        authResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // There are no request codes
                val data: Intent? = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    // Save google account id auth token in shared prefs to use it out of this contract only on sign in once (remember to delete it on sign out)
                    if (!viewModel.isSignedIn()) {
                        saveGoogleAuthIDToken(account.idToken!!)
                    }
                    Log.d("auth", "firebaseAuthWithGoogle:" + account.id)
                    viewModel.navController.value?.let { navController ->
                        viewModel.firebaseAuthWithGoogle(
                            this,
                            viewModel.auth.value!!,
                            account.idToken!!,
                            navController,
                            this
                        )
                    }
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(e.toString(), "Google sign in failed")
                }
            }
    }

    private fun getGoogleAuthIDToken(): String = viewModel.getGoogleAuthIDToken()
    private fun saveGoogleAuthIDToken(idToken: String) {
        viewModel.saveGoogleAuthIDToken(idToken)
    }

    // AUTH Main Methods
    private fun signIn(
        signInMethod: SignInMethod,
        context: Context,
        navController: NavHostController,
        emailStr: String?,
        passwordStr: String?,
        keepSession: Boolean?
    ) {
        viewModel.signIn(
            signInMethod,
            context,
            this,
            navController,
            emailStr,
            passwordStr,
            keepSession,
            googleSignInClient,
            authResultLauncher
        )
    }

    private fun signOut(navController: NavHostController) {
        viewModel.signOut(navController)
    }

    // APP UI
    @Composable
    fun BilliardsDrawApp(
        model: BilliardsDrawViewModel,
        navController: NavHostController,
        coroutineScope: CoroutineScope
    ) {
        // This locks orientation in all app, to lock individually just put this line in each screen composable
        LockScreenOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // Billiards Draw App UI Structure (here starts the UI)
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                BilliardsDrawTopBar(
                    viewModel = model,
                    navController = navController
                )
            }, content =
            { innerPadding ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationManager(
                        viewModel = model,
                        navController = navController,
                        coroutineScope = coroutineScope,
                        onSignIn = { signInMethod, context, navController, emailStr, passwordStr, keepSession ->
                            signIn(
                                signInMethod,
                                context,
                                navController,
                                emailStr,
                                passwordStr,
                                keepSession
                            )
                        },
                        onSignOut = { navHostController -> signOut(navHostController) }
                    )
                }
            })
    }
}