package com.billiardsdraw.billiardsdraw.ui.navigation

sealed class Routes(val route: String) {
    object SplashScreen: Routes("SplashScreen")
    object LoginScreen : Routes("LoginScreen")
    object RegisterScreen : Routes("RegisterScreen")
    object RecoverAccountScreen: Routes("RecoverAccountScreen")
    object MenuScreen : Routes("MenuScreen")
    object CarambolaScreen : Routes("CarambolaScreen")
    object CarambolaMenuScreen : Routes("CarambolaMenuScreen")
    object PoolScreen : Routes("PoolScreen")
    object PoolMenuScreen : Routes("PoolMenuScreen")
    object UserProfileScreen : Routes("UserProfileScreen")
    object UserPremiumScreen : Routes("UserPremiumScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
