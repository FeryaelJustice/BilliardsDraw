package com.billiardsdraw.billiardsdraw.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.billiardsdraw.billiardsdraw.BilliardsDrawViewModel
import com.billiardsdraw.billiardsdraw.domain.model.SignInMethod
import com.billiardsdraw.billiardsdraw.ui.screen.splash.SplashScreen
import com.billiardsdraw.billiardsdraw.ui.screen.login.LoginScreen
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.CarambolaScreen
import com.billiardsdraw.billiardsdraw.ui.screen.carambola.menu.CarambolaMenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.contact.ContactScreen
import com.billiardsdraw.billiardsdraw.ui.screen.menu.MenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.PoolScreen
import com.billiardsdraw.billiardsdraw.ui.screen.pool.menu.PoolMenuScreen
import com.billiardsdraw.billiardsdraw.ui.screen.profile.CompleteProfileScreen
import com.billiardsdraw.billiardsdraw.ui.screen.recoveraccount.RecoverAccountScreen
import com.billiardsdraw.billiardsdraw.ui.screen.register.RegisterScreen
import com.billiardsdraw.billiardsdraw.ui.screen.user.premium.UserPremiumScreen
import com.billiardsdraw.billiardsdraw.ui.screen.user.profile.UserProfileScreen
import kotlinx.coroutines.CoroutineScope

// NAVIGATION ROUTES
sealed class Routes(val route: String) {
    object GeneralApp : Routes("GeneralApp")
    object LoggedApp : Routes("LoggedApp")

    object SplashScreen : Routes("SplashScreen")
    object LoginScreen : Routes("LoginScreen")
    object RegisterScreen : Routes("RegisterScreen")
    object CompleteProfileScreen : Routes("CompleteProfileScreen")
    object RecoverAccountScreen : Routes("RecoverAccountScreen")
    object MenuScreen : Routes("MenuScreen")
    object CarambolaScreen : Routes("CarambolaScreen")
    object CarambolaMenuScreen : Routes("CarambolaMenuScreen")
    object PoolScreen : Routes("PoolScreen")
    object PoolMenuScreen : Routes("PoolMenuScreen")
    object UserProfileScreen : Routes("UserProfileScreen")
    object UserPremiumScreen : Routes("UserPremiumScreen")
    object ContactScreen : Routes("ContactScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}

// NAVIGATION MANAGER
@Composable
fun NavigationManager(
    viewModel: BilliardsDrawViewModel,
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    onSignIn: (
        signInMethod: SignInMethod,
        context: Context,
        navController: NavHostController,
        emailStr: String?,
        passwordStr: String?,
        keepSession: Boolean?
    ) -> Unit,
    onSignOut: (navController: NavHostController) -> Unit
) {
    NavHost(navController = navController, startDestination = Routes.GeneralApp.route) {
        generalApp(navController, coroutineScope, viewModel, onSignIn)
        loggedApp(navController, coroutineScope, viewModel, onSignOut)
    }
}

fun NavGraphBuilder.generalApp(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    viewModel: BilliardsDrawViewModel,
    onSignIn: (
        signInMethod: SignInMethod,
        context: Context,
        navController: NavHostController,
        emailStr: String?,
        passwordStr: String?,
        keepSession: Boolean?
    ) -> Unit
) {
    navigation(startDestination = Routes.SplashScreen.route, route = Routes.GeneralApp.route) {
        composable(Routes.SplashScreen.route) {
            SplashScreen(navController,  coroutineScope)
        }
        composable(Routes.LoginScreen.route) { backStackEntry ->
            // We get the instances of the sub navgraph
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.GeneralApp.route)
            }
            LoginScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel, onSignIn)
        }
        composable(Routes.RegisterScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.GeneralApp.route)
            }
            RegisterScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel)
        }
        composable(Routes.RecoverAccountScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.GeneralApp.route)
            }
            RecoverAccountScreen(hiltViewModel(parentEntry), navController, coroutineScope)
        }
    }
}

fun NavGraphBuilder.loggedApp(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    viewModel: BilliardsDrawViewModel,
    onSignOut: (navController: NavHostController) -> Unit
) {
    navigation(startDestination = Routes.MenuScreen.route, route = Routes.LoggedApp.route) {
        composable(Routes.CompleteProfileScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            CompleteProfileScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel)
        }
        composable(Routes.MenuScreen.route) {
            MenuScreen(navController, coroutineScope, viewModel)
        }
        composable(Routes.CarambolaScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            CarambolaScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel)
        }
        composable(Routes.PoolScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            PoolScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel)
        }
        composable(Routes.CarambolaMenuScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            CarambolaMenuScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel)
        }
        composable(Routes.PoolMenuScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            PoolMenuScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel)
        }
        composable(Routes.UserProfileScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            UserProfileScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel, onSignOut)
        }
        composable(Routes.UserPremiumScreen.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            UserPremiumScreen(hiltViewModel(parentEntry), navController, coroutineScope, viewModel, onSignOut)
        }
        composable(Routes.ContactScreen.route) { navBackStackEntry ->
            val parentEntry = remember(navBackStackEntry) {
                navController.getBackStackEntry(Routes.LoggedApp.route)
            }
            ContactScreen(hiltViewModel(parentEntry), navController,  coroutineScope)
        }
    }
}

// NAVIGATION ACTIONS
fun navigate(navController: NavHostController, route: String) {
    navController.navigate(route)
}

fun navigateClearingAllBackstack(navController: NavHostController, route: String) {
    navigationPopBackstack(navController) // Remove backstack
    navController.navigate(route) {
        // Will pop from SplashScreen to parameter route destination, popping SplashScreen included and up
        popUpTo(Routes.SplashScreen.route) {
            inclusive = true
            // saveState = false
        }
    }
}

// Go back and pop back stack
private fun navigationPopBackstack(navController: NavHostController) {
    navController.popBackStack()
}