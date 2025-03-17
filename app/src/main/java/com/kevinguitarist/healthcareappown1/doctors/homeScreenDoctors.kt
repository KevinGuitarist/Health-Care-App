package com.kevinguitarist.healthcareappown1.doctors

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.kevinguitarist.healthcareappown1.LoginDoctorScreen
import com.kevinguitarist.healthcareappown1.R

@Composable
fun homeScreenDoctors(navHostController: NavHostController){
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxWidth().padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(R.drawable.signout),
            contentDescription = "Logo",
            modifier = Modifier.clickable {
                // Sign out from Firebase
                FirebaseAuth.getInstance().signOut()
                // Clear the form filled status
                context.getSharedPreferences("DoctorPrefs", Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFormFilled", false)
                    .apply()
                // Clear the user type
                context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply()
                // Navigate to sign up screen
                navHostController.navigate(LoginDoctorScreen.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

        Spacer(modifier = Modifier.height(100.dp))

        Box(contentAlignment = Alignment.Center){
            Image(painter = painterResource(R.drawable.chats),
                contentDescription = "Chats",
                modifier = Modifier.clickable {  }
            )
        }
    }
}