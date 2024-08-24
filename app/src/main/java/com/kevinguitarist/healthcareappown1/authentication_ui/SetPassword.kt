package com.kevinguitarist.healthcareappown1.authentication_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kevinguitarist.healthcareappown.ui.theme.button_Color
import com.kevinguitarist.healthcareappown1.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun SetPassword() {
    var email by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    var password by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth().padding(start = 90.dp)) {
                    Text(
                        text = "Set Password",
                        fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                        fontSize = 24.sp,
                        color = Color(0xFF2260FF)
                    )
                }
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = "Back",
                    modifier = Modifier.padding(start = 26.dp).clickable { },
                    Color(0xFF2260FF)
                )
            }
        )

        Box(modifier = Modifier.fillMaxSize().padding(start = 30.dp, top = 10.dp, end = 30.dp)) {
            Column {
                Text(
                    text = stringResource(R.string.Lorem_Text2),
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                    fontSize = 12.sp,
                    modifier = Modifier.fillMaxWidth().height(24.dp)
                )

                Spacer(modifier = Modifier.height(35.dp))

                Text(text = "Password",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

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

                Spacer(modifier = Modifier.height(31.dp))

                Text(text = "Confirm Password",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

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

                Spacer(modifier = Modifier.height(44.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.fillMaxWidth()){
                        Button(onClick = { },
                            colors = ButtonDefaults.buttonColors(button_Color.Blue),
                            modifier = Modifier.width(273.dp).height(45.dp).align(Alignment.TopCenter)
                        ){
                            Text(text = "Create New Password",
                                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
}