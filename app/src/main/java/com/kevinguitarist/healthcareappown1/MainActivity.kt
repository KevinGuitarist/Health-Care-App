package com.kevinguitarist.healthcareappown1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kevinguitarist.healthcareappown1.authentication_ui.loginScreen
import com.kevinguitarist.healthcareappown1.authentication_ui.signUp
import com.kevinguitarist.healthcareappown1.ui.theme.HealthCareAppOwn1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            HealthCareAppOwn1Theme(darkTheme = false) {
                NavigationMain()
            }
        }
    }
}

@Composable
fun NavigationMain(){
    val currentUser = FirebaseAuth.getInstance().currentUser
    val navController = rememberNavController()

    LaunchedEffect(currentUser) {
        // Reload user to check if they are still valid
        currentUser?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful && currentUser != null) {
                // User is still valid, navigate to HomeScreen
                navController.navigate(HomeScreen.route) {
                    popUpTo(0) {
                        inclusive = true // Clear backstack including start destination
                    }
                    launchSingleTop = true
                }
            } else {
                // User has been deleted or sign in failed, navigate to WelcomeScreen
                navController.navigate(WelcomeScreen.route) {
                    popUpTo(0) {
                        inclusive = true // Clear backstack including start destination
                    }
                    launchSingleTop = true
                }
            }
        } ?: run {
            // No user is signed in, navigate to WelcomeScreen
            navController.navigate(WelcomeScreen.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true // Clear backstack including start destination
                }
                launchSingleTop = true
            }
        }
    }

    NavHost(navController = navController, startDestination = if (currentUser != null) HomeScreen.route else WelcomeScreen.route){
        composable(WelcomeScreen.route){
            welcomeScreen(navController)
        }
        composable(LoginScreen.route){
            loginScreen(navController)
        }
        composable(SignUpScreen.route){
            signUp(navController)
        }
        composable(HomeScreen.route){
            HomePage(navController)
        }
    }
}

