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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.kevinguitarist.healthcareappown1.R
import com.kevinguitarist.healthcareappown1.database.DoctorDatabaseManager
import com.kevinguitarist.healthcareappown1.database.DoctorInformation

@Composable
fun HomePage(navHostController: NavHostController) {
    val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
    val imageUrl = currentUser?.photoUrl
    val displayName = currentUser?.displayName
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }

    val specialistImages = listOf(
        R.drawable.cardiologist,
        R.drawable.dentistlogo,
        R.drawable.dermatologist,
        R.drawable.gynecologist,
        R.drawable.neurologist,
        R.drawable.ophthalmologist,
        R.drawable.orthopedic,
        R.drawable.pediatrician,
        R.drawable.psychiatrist
    )

    // State to hold the list of doctors
    var doctorsList by rememberSaveable { mutableStateOf<List<DoctorInformation>>(emptyList()) }

    // Fetch doctors data from Firebase
    LaunchedEffect(key1 = true) {  // This ensures it only runs once
        val doctorDatabaseManager = DoctorDatabaseManager()
        doctorDatabaseManager.getAllDoctors(
            onSuccess = { doctors ->
                doctorsList = doctors
            },
            onError = { errorMessage ->
                println("Error fetching doctors: $errorMessage")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Your existing UI code goes here:
            Box(modifier = Modifier.padding(start = 32.dp, top = 41.dp)) {
                Row() {
                    imageUrl?.let {
                        Image(
                            painter = rememberAsyncImagePainter(model = it),
                            contentDescription = "Google Account Image",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                                .clickable { }
                        )
                    } ?: Icon(
                        painter = painterResource(R.drawable.user),  // Fallback icon if no image is found
                        contentDescription = "Default Profile Icon",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column() {
                        Text(
                            "Hi, WelcomeBack",
                            color = Color(0xFF2260FF),
                            fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                        )
                        Text(
                            text = displayName ?: "User",
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.leaguespartan_regular))
                        )
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 30.dp)
                    ) {
                        Row(
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.notification),
                                contentDescription = "notification",
                                modifier = Modifier
                                    .clickable { }
                                    .size(35.dp),
                                tint = Color.Unspecified
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Icon(
                                painter = painterResource(R.drawable.settings),
                                contentDescription = "settings",
                                modifier = Modifier
                                    .clickable { }
                                    .size(35.dp),
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 31.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.doctors),
                    contentDescription = "doctors",
                    modifier = Modifier
                        .clickable { }
                        .size(40.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.width(10.dp))

                Icon(
                    painter = painterResource(R.drawable.favorite),
                    contentDescription = "favourite",
                    modifier = Modifier
                        .clickable { }
                        .size(44.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.width(12.dp))

                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .background(
                            Color(0xFFCAD6FF),
                            RoundedCornerShape(23.dp)
                        ),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 1.dp, start = 1.dp, bottom = 1.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                verticalAlignment = Alignment.CenterVertically // Ensures vertical centering
                            ) {
                                // Left-side icon
                                Icon(
                                    painter = painterResource(id = R.drawable.adjust),
                                    contentDescription = "adjust",
                                    modifier = Modifier
                                        .padding(start = 3.dp, bottom = 4.dp)
                                        .size(26.dp),
                                    tint = Color.Unspecified
                                )

                                Spacer(modifier = Modifier.width(5.dp))

                                // Text field in the middle
                                Box(
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)
                                        .weight(1f) // This takes up the remaining space between the icons
                                        .fillMaxHeight(),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    if (text.text.isEmpty()) {
                                        Text(
                                            text = " ", // Placeholder text
                                            fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                            fontSize = 16.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    innerTextField()
                                }

                                Spacer(modifier = Modifier.width(5.dp)) // Optional spacing before the right icon

                                // Right-side search icon
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "search",
                                    modifier = Modifier
                                        .padding(end = 6.dp, bottom = 3.dp) // Padding for right-side alignment
                                        .size(23.dp),
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            Column(modifier = Modifier.padding(start = 30.dp)) {
                Text(
                    text = "Most Popular",
                    color = Color(0xFF000000),
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium))
                )

                Spacer(modifier = Modifier.height(17.dp))

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(specialistImages) { imageRes ->
                        Image(
                            painter = painterResource(id = imageRes),
                            contentDescription = null,
                            modifier = Modifier
                                .size(size = 90.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(modifier = Modifier.padding(start = 30.dp, end = 30.dp)) {
                items(doctorsList) { doctor ->
                    DoctorCard(doctor = doctor)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: DoctorInformation) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(105.dp)
            .clip(RoundedCornerShape(17.dp))
            .background(Color(0xFFCAD6FF))
    )
    {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 13.dp)
                    .size(85.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFECF1FF))
            ) {
                // Load doctor's image if available
                if (!doctor.imageUrl.isNullOrEmpty()) {
                    val context = LocalContext.current

                    Log.d("DoctorCard", "Image URL: ${doctor.imageUrl}") // Debugging line

                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(context)
                                .data(data = doctor.imageUrl)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                    error(R.drawable.user) // Show this if the image fails to load
                                    placeholder(R.drawable.user) // Show this while loading
                                }).build()
                        ),
                        contentDescription = "Doctor Image",
                        modifier = Modifier
                            .size(85.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop // Experiment with other ContentScale options
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Default Doctor Icon",
                        modifier = Modifier.size(85.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.fillMaxHeight()
            ){
                Box(
                    modifier = Modifier
                        .width(211.dp)
                        .height(50.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFFECF1FF))
                ){
                    Column(
                        modifier = Modifier
                            .padding(start = 14.dp, top = 9.dp)
                    ) {
                        Text(
                            text = "Dr. ${doctor.doctorName}", // Use doctor's name from DoctorInformation
                            color = Color(0xFF2260FF),
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            fontSize = 14.sp,
                            style = TextStyle(lineHeight = 3.sp)
                        )

                        Text(
                            text = doctor.profile, // Use doctor's specialization from DoctorInformation
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(7.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Box(modifier = Modifier
                            .width(60.dp)
                            .height(25.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFECF1FF)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.star),
                                    contentDescription = "Rating",
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                        .size(19.dp),
                                    tint = Color(0xFF2260FF)
                                )

                                Spacer(modifier = Modifier.width(3.dp))

                                Text(
                                    text = "-",
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(6.dp))

                        Box(modifier = Modifier
                            .width(60.dp)
                            .height(25.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFECF1FF)),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.message),
                                    contentDescription = "Message",
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                        .size(17.dp),
                                    tint = Color(0xFF2260FF)
                                )

                                Spacer(modifier = Modifier.width(3.dp))

                                Text(
                                    text = "-",
                                    color = Color.Black,
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFECF1FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.questionmarkicon),
                                contentDescription = "questionmark",
                                tint = Color.Unspecified
                            )
                        }

                        Spacer(modifier = Modifier.width(5.dp))

                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFECF1FF)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.hearticon),
                                contentDescription = "Heart",
                                tint = Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
}