package com.kevinguitarist.healthcareappown1

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.kevinguitarist.healthcareappown1.authentication_ui.Login_Doctor
import com.kevinguitarist.healthcareappown1.authentication_ui.loginScreen
import com.kevinguitarist.healthcareappown1.authentication_ui.signUp
import com.kevinguitarist.healthcareappown1.authentication_ui.signUp_doctor
import com.kevinguitarist.healthcareappown1.doctors.formscreenDoctors
import com.kevinguitarist.healthcareappown1.doctors.homeScreenDoctors
import com.kevinguitarist.healthcareappown1.patients.HomePage
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
    val context = LocalContext.current

    LaunchedEffect(currentUser) {
        currentUser?.reload()?.addOnCompleteListener { task ->
            if (task.isSuccessful && currentUser != null) {
                val userType = getUserType(context)
                if (userType == "doctor") {
                    // Check if form is completed
                    val sharedPreferences = context.getSharedPreferences("DoctorPrefs", Context.MODE_PRIVATE)
                    val isFormCompleted = sharedPreferences.getBoolean("formCompleted", false)
                    
                    if (isFormCompleted) {
                        navController.navigate(HomeScreenDoctor.route) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(FormScreenDoctor.route) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                } else {
                    navController.navigate(HomeScreen.route) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            } else {
                navController.navigate(WelcomeScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    NavHost(navController = navController, startDestination = WelcomeScreen.route) {
        composable(WelcomeScreen.route) { welcomeScreen(navController) }
        composable(LoginScreen.route) { loginScreen(navController) }
        composable(SignUpScreen.route) { signUp(navController) }
        composable(HomeScreen.route) { HomePage(navController) }
        composable(LoginDoctorScreen.route) { Login_Doctor(navController) }
        composable(SignUpDoctorScreen.route) { signUp_doctor(navController) }
        composable(FormScreenDoctor.route) { formscreenDoctors(navController, context) }
        composable(HomeScreenDoctor.route) { homeScreenDoctors(navController) }
    }
}

fun saveUserType(context: Context, userType: String) {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString("userType", userType).apply()
}

fun getUserType(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("userType", null)
}



