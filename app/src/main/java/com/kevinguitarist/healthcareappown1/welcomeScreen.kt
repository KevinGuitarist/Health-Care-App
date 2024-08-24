package com.kevinguitarist.healthcareappown1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kevinguitarist.healthcareappown.ui.theme.button_Color

@Composable
fun welcomeScreen(navHostController: NavHostController){

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(235.dp))
            Image(painter = painterResource(R.drawable.loginscreenlogo),
                contentDescription = "Logo", modifier = Modifier.size(138.dp),
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(stringResource(R.string.Skin_Text,
                Color.Blue),
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.league_spartan_thin)),
                color = Color(0xFF2260FF)
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(stringResource(R.string.Firts_Text),
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.league_spartan_thin)),
                color = Color(0xFF2260FF)
            )

            Spacer(modifier = Modifier.height(17.dp))

            Text(stringResource(R.string.Dermalogy_Center),
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                color = Color(0xFF2260FF)
            )

            Spacer(modifier = Modifier.height(84.dp))

            Box(modifier = Modifier.height(50.dp).width(267.dp)){
                Text(stringResource(R.string.Lorem_Text),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {navHostController.navigate(LoginScreen.route)},
                colors = ButtonDefaults.buttonColors(button_Color.Blue),
                modifier = Modifier.width(207.dp).height(45.dp)
            ){
                Text(text = "Log in",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(7.dp))

            Button(onClick = {navHostController.navigate(SignUpScreen.route)},
                colors = ButtonDefaults.buttonColors(button_Color.light_Blue),
                modifier = Modifier.width(207.dp).height(45.dp)
            ){
                Text(text = "Sign Up",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 24.sp,
                    color = Color(0xFF2260FF)
                )
            }
        }
    }
}

