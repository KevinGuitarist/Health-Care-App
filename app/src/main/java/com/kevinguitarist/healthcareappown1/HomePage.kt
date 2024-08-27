package com.kevinguitarist.healthcareappown1

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun HomePage(navController: NavHostController){
    Text(
        text = "Home Page",
        fontSize = 50.sp,
    )
}