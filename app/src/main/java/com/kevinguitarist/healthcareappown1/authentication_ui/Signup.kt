package com.kevinguitarist.healthcareappown1.authentication_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kevinguitarist.healthcareappown.ui.theme.button_Color
import com.kevinguitarist.healthcareappown1.R
import com.kevinguitarist.healthcareappown1.WelcomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signUp(navHostController: NavHostController) {

    var email by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth().padding(start = 90.dp)) {
                    Text(
                        text = "New Account",
                        fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                        fontSize = 24.sp,
                        color = Color(0xFF2260FF),
                        textAlign = TextAlign.Center
                        )
                }
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "Back",
                    modifier = Modifier.padding(start = 26.dp).clickable {navHostController.navigate(WelcomeScreen.route)},
                    Color(0xFF2260FF)
                )
            }
        )
        Box(modifier = Modifier.fillMaxSize().padding(start = 30.dp, top = 10.dp, end = 30.dp)){
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Full Name",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    value = email,
                    onValueChange = {email = it},
                    modifier = Modifier.fillMaxWidth().height(45.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().fillMaxHeight()
                                .padding(top = 15.dp, start = 13.dp, end = 32.dp, bottom = 5.dp)
                        ){
                            if (email.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.height(30.dp),
                                    text = "example@example.com",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 20.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(text = "Password",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                BasicTextField(
                    value = password,
                    onValueChange = {password = it},
                    modifier = Modifier.fillMaxWidth().height(45.dp).background(Color(0xFFECF1FF), RoundedCornerShape(13.dp)),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Row(modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 13.dp, top = 14.dp, bottom = 15.dp)){
                            Box(modifier = Modifier.width(240.dp).height(12.dp).align(Alignment.CenterVertically)) {
                                if (password.text.isEmpty()){
                                    Text(text = "*************",
                                        fontSize = 20.sp,
                                        fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                        color = Color(0xFF809CFF)
                                    )
                                }
                                innerTextField()
                            }
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                                contentAlignment = Alignment.TopEnd){
                                Icon(painter = painterResource(R.drawable.hide_eyeicon),
                                    contentDescription = "HideEye",
                                    modifier = Modifier.clickable {  }
                                )
                            }

                        }
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "Email",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    value = email,
                    onValueChange = {email = it},
                    modifier = Modifier.fillMaxWidth().height(45.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().fillMaxHeight()
                                .padding(top = 15.dp, start = 13.dp, end = 32.dp, bottom = 5.dp)
                        ){
                            if (email.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.height(30.dp),
                                    text = "example@example.com",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 20.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(text = "Mobile Number",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    value = email,
                    onValueChange = {email = it},
                    modifier = Modifier.fillMaxWidth().height(45.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().fillMaxHeight()
                                .padding(top = 15.dp, start = 13.dp, end = 32.dp, bottom = 5.dp)
                        ){
                            if (email.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.height(30.dp),
                                    text = "example@example.com",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 20.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(text = "Date Of Birth",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    value = email,
                    onValueChange = {email = it},
                    modifier = Modifier.fillMaxWidth().height(45.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth().fillMaxHeight()
                                .padding(top = 14.dp, start = 24.dp, end = 32.dp, bottom = 14.dp)
                        ){
                            if (email.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.height(30.dp).width(144.dp),
                                    text = "DD / MM / YY",
                                    color = Color(0xFF2260FF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 20.sp
                                )
                            }
                            innerTextField()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(38.dp))


                TermsAndCond()

                Spacer(modifier = Modifier.height(11.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Button(onClick = { },
                            colors = ButtonDefaults.buttonColors(button_Color.Blue),
                            modifier = Modifier.width(207.dp).height(45.dp).align(Alignment.TopCenter)
                        ){
                            Text(text = "Sign Up",
                                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                                fontSize = 24.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Text(text = "or sign up with",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                            fontSize = 12.sp,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth()){
                    Box(modifier = Modifier.fillMaxWidth()){
                        Row(horizontalArrangement = Arrangement.spacedBy(9.dp), modifier = Modifier.align(
                            Alignment.Center)){
                            Icon(painter = painterResource(R.drawable.google_login_item),
                                contentDescription = "Googlel",
                                modifier = Modifier.clickable {  },
                                tint = Color.Unspecified
                                //    Color(0xFF2260FF)

                            )

                            Icon(painter = painterResource(R.drawable.facebook_login_icon),
                                contentDescription = "Facebook",
                                modifier = Modifier.clickable {  },
                                tint = Color.Unspecified
                                //    Color(0xFF2260FF)
                            )

                            Icon(painter = painterResource(R.drawable.finger_login_icon),
                                contentDescription = "FingerPrint",
                                modifier = Modifier.clickable {  },
                                tint = Color.Unspecified
                                //    Color(0xFF2260FF)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(38.dp))

                AlreadyHaveAnAccount()


            }
        }
    }
}

@Composable
fun TermsAndCond(){
    Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(text = "By continuing, you agree to",
            fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
            fontSize = 12.sp
        )
    }

    Spacer(modifier = Modifier.height(2.dp))

    Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        Box(){
            Text(text = "Terms of Use",
                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                color = Color(0xFF2260FF),
                fontSize = 12.sp,
                modifier = Modifier.clickable {  }
            )
        }
        Box(){
            Text(text = "and",
                fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                fontSize = 12.sp
            )
        }
        Box(){
            Text(text = "Privacy Policy.",
                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                color = Color(0xFF2260FF),
                fontSize = 12.sp,
                modifier = Modifier.clickable {  }
            )
        }
    }
}

@Composable
fun AlreadyHaveAnAccount(){
    Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        Box(){
            Text(text = "already have an account?",
                fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                fontSize = 12.sp

            )
        }
        Box(){
            Text(text = "Log in",
                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                color = Color(0xFF2260FF),
                fontSize = 12.sp,
                modifier = Modifier.clickable {  }
            )
        }
    }
}