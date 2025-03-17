package com.kevinguitarist.healthcareappown1.patients

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kevinguitarist.healthcareappown1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsInformationUI(navHostController: NavHostController) {
    // State for bottom navigation
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        R.drawable.home,
        R.drawable.messages,
        R.drawable.profile,
        R.drawable.calender
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth().wrapContentSize(align = Alignment.Center)) {
                        Text(
                            text = "Doctor Info",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                            fontSize = 24.sp,
                            color = Color(0xFF2260FF),
                        )
                    }
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.back_icon),
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(start = 26.dp)
                            .clickable { navHostController.navigateUp() },
                        Color(0xFF2260FF)
                    )
                }
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, item ->
                        val isSelected = index == selectedItem
                        val backgroundColor =
                            if (isSelected) Color(0xFF2260FF) else Color.Transparent
                        val contentColor = if (isSelected) Color.White else Color(0xFF2260FF)

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .background(backgroundColor)
                                .clickable { selectedItem = index }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item),
                                contentDescription = null,
                                tint = contentColor
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(17.dp))
                        .background(Color(0xFFCAD6FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(modifier = Modifier.padding(top = 18.dp, start = 21.dp, end = 21.dp)){
                            Box(modifier = Modifier.wrapContentSize(align = Alignment.TopStart)){
                                Image(
                                    painter = painterResource(id = R.drawable.dummy),
                                    contentDescription = "Doctor Image",
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clip(RoundedCornerShape(60.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Box(modifier = Modifier.fillMaxWidth().height(40.dp).background(color = Color(0xFF2260FF),
                                    shape = RoundedCornerShape(18.dp)),
                                ){
                                    Row(){
                                        Box(){
                                            Image(modifier = Modifier.align(Alignment.CenterStart).padding(horizontal = 3.dp, vertical = 3.dp),
                                                painter = painterResource(R.drawable.experience),
                                                contentDescription = "Experience"
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Column(modifier = Modifier.fillMaxSize()) {
                                            Box(modifier = Modifier.wrapContentSize(align = Alignment.TopCenter)){
                                                Text(
                                                    text = "15 Years",
                                                    fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 12.sp,
                                                    color = Color(0xFFFFFFFF),
                                                    modifier = Modifier.padding(top = 5.dp)
                                                )
                                            }

                                            Text(
                                                text = "Experience",
                                                fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                                                fontSize = 8.sp,
                                                color = Color(0xFFFFFFFF),
                                                modifier = Modifier.padding(top = 0.dp)
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(5.dp))

                                Box(modifier = Modifier.fillMaxWidth().background(color = Color(0xFF2260FF),
                                    shape = RoundedCornerShape(10.dp))
                                ){
                                    Text(
                                        text = "Focus: The impact of hormonal imbalances on skin conditions, specializing in acne, hirsutism, and other skin disorders.",
                                        fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                                        textAlign = TextAlign.Center,
                                        color = Color(0xFF2260FF)
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 250.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(R.drawable.star),
                                contentDescription = "Rating",
                                tint = Color.Yellow
                            )

                            Text("-", fontSize = 12.sp)
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(R.drawable.message),
                                contentDescription = "Reviews",
                                tint = Color.Gray
                            )

                            Text("-", fontSize = 12.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(painter = painterResource(R.drawable.clock),
                                contentDescription = "Availability",
                                tint = Color.Gray
                            )

                            Text("Mon-Sat / 9:00AM - 5:00PM", fontSize = 12.sp)
                        }
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 280.dp)
                    ) {
                        Button(
                            onClick = {  },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(painter = painterResource(R.drawable.calender), contentDescription = "Schedule", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Schedule", fontSize = 12.sp)
                        }
                        IconButton(onClick = { /* Handle info */ }) {
                            Icon(imageVector = Icons.Outlined.Call, contentDescription = "Info", tint = Color.Gray)
                        }
                        IconButton(onClick = { /* Handle question */ }) {
                            Icon(painter = painterResource(R.drawable.message), contentDescription = "Question", tint = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Profile",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                )
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Career Path",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                )
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Highlights",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                )
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                )
            }
        }
    }
}