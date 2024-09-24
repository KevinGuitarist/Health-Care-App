package com.kevinguitarist.healthcareappown1.authentication_ui

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kevinguitarist.healthcareappown.ui.theme.button_Color
import com.kevinguitarist.healthcareappown1.HomeScreen
import com.kevinguitarist.healthcareappown1.R
import com.kevinguitarist.healthcareappown1.SignUpScreen
import com.kevinguitarist.healthcareappown1.WelcomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginScreen(navHostController: NavHostController) {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }
    val loginViewModel = remember { LoginViewModel() }
    val authState by loginViewModel.authState.observeAsState(AuthState.Idle)

    val context = LocalContext.current
    val token = stringResource(R.string.web_client_id)
    val currentUser = FirebaseAuth.getInstance().currentUser
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
    ) {
        val task =
            try {
                val account = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    .getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "signInWithCredential:success")
                            val user = FirebaseAuth.getInstance().currentUser
                            Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                            navHostController.navigate(HomeScreen.route) {
                                popUpTo(0) {
                                    inclusive = true // Clear backstack including start destination
                                }
                                launchSingleTop = true
                            }
                        }
                        else {
                            // Sign-in failed, handle the error
                            Log.w("TAG", "signInWithCredential:failure", task.exception)
                            Toast.makeText(context, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                        }

                    }
            }
            catch (e: ApiException) {
                Log.w("TAG", "GoogleSign in Failed", e)
            }
    }

        Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth().padding(start = 120.dp)){
                    Text(text = "Log In",
                        fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                        fontSize = 24.sp,
                        color = Color(0xFF2260FF),

                    )
                }
            },
            navigationIcon = {
                Icon(painter = painterResource(R.drawable.back_icon),
                    contentDescription = "Back",
                    modifier = Modifier.padding(start = 26.dp).clickable { navHostController.navigate(WelcomeScreen.route)},
                    Color(0xFF2260FF)
                )
            }
        )

        Box(modifier = Modifier.fillMaxSize().padding(start = 30.dp, top = 10.dp, end = 30.dp)){
            Column {
                Text(text = "Welcome",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                    fontSize = 24.sp,
                    color = Color(0xFF2260FF)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = stringResource( R.string.Lorem_Text2),
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().height(24.dp)
                )

                Spacer(modifier = Modifier.height(47.dp))

                Text(text = "Email or Mobile Number",
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
                                .fillMaxSize()
                                .padding(start = 13.dp)
                        ){
                            if (email.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.align(Alignment.CenterStart),
                                    text = "example@example.com",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 18.sp
                                )
                            }
                            Box(modifier = Modifier.align(Alignment.CenterStart)){
                                innerTextField()
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Password",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    value = password,
                    onValueChange = {password = it},
                    modifier = Modifier.fillMaxWidth().height(45.dp).background(Color(0xFFECF1FF), RoundedCornerShape(13.dp)),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Row(modifier = Modifier.fillMaxSize().padding(start = 12.dp)){
                            Box(modifier = Modifier.fillMaxSize()) {
                                if (password.text.isEmpty()){
                                    Text(text = "***********",
                                        fontSize = 25.sp,
                                        fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                        color = Color(0xFF809CFF),
                                        modifier = Modifier.align(Alignment.CenterStart).padding(top = 13.dp)
                                    )
                                }
                                Box(modifier = Modifier.align(Alignment.CenterStart).width(240.dp)){
                                    innerTextField()
                                }
                            }
                        }
                        Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.CenterEnd).padding(end = 10.dp)){
                            Icon(painter = painterResource(R.drawable.hide_eyeicon),
                                contentDescription = "HideEye",
                                modifier = Modifier.clickable {  }
                            )
                        }

                    }
                )

                Spacer(modifier = Modifier.height(9.dp))

                Row(modifier = Modifier.fillMaxWidth()){
                    Box(modifier = Modifier.fillMaxWidth()){
                        Text(text = "Forget Password",
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            color = Color(0xFF2260FF),
                            modifier = Modifier.align(Alignment.CenterEnd).clickable {  }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(37.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Button(onClick = { loginViewModel.handleSignIn(email.text, password.text) },
                            colors = ButtonDefaults.buttonColors(button_Color.Blue),
                            modifier = Modifier.width(207.dp).height(45.dp).align(Alignment.TopCenter)
                        ){
                            Text(text = "Log in",
                                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                                fontSize = 24.sp
                            )
                        }
                    }
                }

                LaunchedEffect(authState) {
                    when (authState) {
                        is AuthState.Success -> {
                            Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                            navHostController.navigate(HomeScreen.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                        is AuthState.AuthError -> {
                            val errorMessage = (authState as AuthState.AuthError).message
                            Toast.makeText(context, errorMessage ?: "Authentication Failed", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            // Handle other states if necessary
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
                                contentDescription = "Google",
                                modifier = Modifier.clickable {
                                    val gso = GoogleSignInOptions
                                        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .requestIdToken(token)
                                        .requestEmail()
                                        .build()
                                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                                    launcher.launch(googleSignInClient.signInIntent)
                                    googleSignInClient.signOut().addOnCompleteListener {
                                        // Revoke access to ensure account selection prompt
                                        googleSignInClient.revokeAccess().addOnCompleteListener {
                                            // Launch the sign-in intent
                                            launcher.launch(googleSignInClient.signInIntent)
                                        }
                                    }

                                },
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

                Texts(navHostController)

                Spacer(modifier = Modifier.height(17.dp))

                Column(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)){
                        Text(text = "or",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)){
                        Icon(painter = painterResource(R.drawable.doctor_login_button),
                            contentDescription = "FingerPrint",
                            modifier = Modifier
                                .clickable {  }
                                .size(45.dp),
                            tint = Color.Unspecified
                            //    Color(0xFF2260FF)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Texts(navHostController: NavHostController){
    Row(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center), horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        Box(){
            Text(text = "Don't have an account?",
                fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                fontSize = 12.sp

            )
        }
        Box(){
            Text(text = "Sign Up",
                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                color = Color(0xFF2260FF),
                fontSize = 12.sp,
                modifier = Modifier.clickable {navHostController.navigate(SignUpScreen.route)}
            )
        }
    }
}

