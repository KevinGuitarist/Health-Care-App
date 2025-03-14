package com.kevinguitarist.healthcareappown1.doctors

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.kevinguitarist.healthcareappown1.R

@Composable
fun homeScreenDoctors(navHostController: NavHostController){
    Column(modifier = Modifier.fillMaxWidth().padding(30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.signout),
            contentDescription = "Logo"
        )

        Spacer(modifier = Modifier.height(100.dp))

        Box(contentAlignment = Alignment.Center){
            Image(painter = painterResource(R.drawable.chats),
                contentDescription = "Chats"
            )
        }
    }
}