package com.kevinguitarist.healthcareappown1.patients

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kevinguitarist.healthcareappown1.R
import com.kevinguitarist.healthcareappown1.database.DoctorDatabaseManager
import com.kevinguitarist.healthcareappown1.database.DoctorInformation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorsInformationUI(navHostController: NavHostController, doctorId: String) {
    var doctorData by remember { mutableStateOf<DoctorInformation?>(null) }

    // Fetch doctor data when component loads
    LaunchedEffect(doctorId) {
        val doctorDatabaseManager = DoctorDatabaseManager()
        doctorDatabaseManager.getDoctorById(doctorId, 
            onSuccess = { doctor ->
                doctorData = doctor
            },
            onError = { error ->
                Log.e("DoctorsInformationUI", "Error fetching doctor: $error")
            }
        )
    }

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
                    .padding(horizontal = 30.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(17.dp))
                        .background(Color(0xFFCAD6FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(modifier = Modifier.padding(top = 18.dp, start = 21.dp, end = 21.dp)){
                            Box(modifier = Modifier.wrapContentSize(align = Alignment.TopStart)) {
                                if (!doctorData?.imageUrl.isNullOrEmpty()) {
                                    val context = LocalContext.current
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            ImageRequest.Builder(context)
                                                .data(data = doctorData?.imageUrl)
                                                .apply(block = fun ImageRequest.Builder.() {
                                                    crossfade(true)
                                                    error(R.drawable.user)
                                                    placeholder(R.drawable.user)
                                                }).build()
                                        ),
                                        contentDescription = "Doctor Image",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.user),
                                        contentDescription = "Default Doctor Image",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(40.dp)
                                        .background(
                                            color = Color(0xFF2260FF),
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Start
                                    ) {

                                        // Rounded Image Background
                                        Box(
                                            modifier = Modifier
                                                .size(40.dp)
                                                .background(
                                                    color = Color(0x40FFFFFF),
                                                    shape = CircleShape
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Image(
                                                modifier = Modifier
                                                    .size(24.dp),
                                                painter = painterResource(R.drawable.experience),
                                                contentDescription = "Experience",
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(0.dp)
                                        ) {
                                            Text(
                                                text = "15 years",
                                                fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )

                                            Text(
                                                text = "experience",
                                                fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                                                fontSize = 16.sp,
                                                fontWeight = FontWeight.Thin,
                                                color = Color.White,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = Color(0xFF2260FF),
                                            shape = RoundedCornerShape(20.dp)
                                        ),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp)
                                    ) {
                                        Text(
                                            text = "Focus:",
                                            fontFamily = FontFamily(Font(R.font.leaguespartan_semibold)),
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White,
                                            textAlign = TextAlign.Start
                                        )

                                        Text(
                                            text = "The impact of hormonal imbalances on skin conditions, specializing in acne, hirsutism, and other skin disorders.",
                                            fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                                            color = Color.White,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier.padding(top = 2.dp),
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 21.dp, end = 21.dp)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(30.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Dr. Alexander Bennett, Ph.D.",
                                    color = Color(0xFF2260FF),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center
                                )

                                Text(
                                    text = "Dermato-Genetics",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 21.dp, end = 21.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .height(25.dp)
                                        .width(50.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.star),
                                            contentDescription = "Rating",
                                            tint = Color(0xFF2260FF),
                                            modifier = Modifier.size(20.dp).padding(start = 5.dp)
                                        )

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = "-",
                                            fontSize = 18.sp,
                                            color = Color(0xFF000000)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .height(25.dp)
                                        .width(50.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(R.drawable.message),
                                            contentDescription = "Comments",
                                            tint = Color(0xFF2260FF),
                                            modifier = Modifier.size(20.dp).padding(start = 5.dp)
                                        )

                                        Spacer(modifier = Modifier.width(5.dp))

                                        Text(
                                            text = "-",
                                            fontSize = 18.sp,
                                            color = Color(0xFF000000)
                                        )
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(10.dp)
                                        )
                                        .height(50.dp),
                                    contentAlignment = Alignment.CenterStart
                                ){
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                                        Icon(
                                            painter = painterResource(R.drawable.clock),
                                            contentDescription = "Availability",
                                            tint = Color(0xFF2260FF),
                                            modifier = Modifier.size(20.dp).padding(start = 5.dp)
                                        )

                                        Spacer(modifier = Modifier.width(7.dp))

                                        Text("Mon-Sat / 9:00AM - 5:00PM", modifier = Modifier.padding(end = 5.dp) ,fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(15.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 21.dp, end = 21.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF2260FF),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .height(35.dp)
                                    .width(80.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.calender),
                                        contentDescription = "Rating",
                                        tint = Color(0xFFFFFFFF),
                                        modifier = Modifier.size(18.dp).padding(start = 7.dp)
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(
                                        text = "Schedule",
                                        fontSize = 12.sp,
                                        color = Color(0xFFFFFFFF)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF2260FF),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .height(35.dp)
                                    .width(80.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(imageVector = Icons.Outlined.Call,
                                        contentDescription = "Call",
                                        tint = Color(0XFFFFFFFF),
                                        modifier = Modifier.size(27.dp).padding(start = 15.dp)
                                    )

                                    Spacer(modifier = Modifier.width(7.dp))

                                    Text(
                                        text = "Call",
                                        fontSize = 12.sp,
                                        color = Color(0xFFFFFFFF)
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color(0xFF2260FF),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .height(35.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.message),
                                        contentDescription = "Question",
                                        tint = Color(0XFFFFFFFF),
                                        modifier = Modifier.size(16.dp)
                                    )

                                    Spacer(modifier = Modifier.width(5.dp))

                                    Text(
                                        text = "Message",
                                        fontSize = 12.sp,
                                        color = Color(0xFFFFFFFF),
                                        modifier = Modifier.padding(start = 2.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
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

                Spacer(modifier = Modifier.height(15.dp))

            }
        }
    }
}