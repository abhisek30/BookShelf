package com.abhisek.project.bookshelf.ui.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abhisek.project.bookshelf.ui.auth.dashboard.DashboardScreen
import com.abhisek.project.bookshelf.ui.auth.signup.SignUpScreen
import kotlinx.serialization.Serializable

@Composable
fun AuthenticationNavHost(modifier: Modifier = Modifier, navigateToDashboard: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AuthenticationScreen.Dashboard) {
        composable<AuthenticationScreen.Dashboard> {
            DashboardScreen(onNavigateToSignIn = {
                navController.navigate(AuthenticationScreen.SignIn)
            }, onNavigateToSignUp = {
                navController.navigate(AuthenticationScreen.SignUp)
            }, modifier = Modifier.fillMaxSize())
        }
        composable<AuthenticationScreen.SignIn> {

        }
        composable<AuthenticationScreen.SignUp> {
            SignUpScreen(Modifier.fillMaxSize())
        }
    }
}

@Serializable
sealed class AuthenticationScreen {
    @Serializable
    data object Dashboard : AuthenticationScreen()

    @Serializable
    data object SignIn : AuthenticationScreen()

    @Serializable
    data object SignUp : AuthenticationScreen()
}