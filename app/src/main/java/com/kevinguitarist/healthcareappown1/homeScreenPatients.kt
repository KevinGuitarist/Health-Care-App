package com.kevinguitarist.healthcareappown1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomePage(navHostController: NavHostController) {
    val currentUser = FirebaseAuth.getInstance().currentUser
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

    Column{
        Box(modifier = Modifier.padding(start = 32.dp, top = 41.dp)){
            Row() {
                imageUrl?.let {
                    Image(
                        painter = rememberAsyncImagePainter(model = it),  // Use AsyncImagePainter to load image
                        contentDescription = "Google Account Image",
                        modifier = Modifier
                            .size(40.dp)  // Set the size you prefer (40.dp is a good default size)
                            .clip(CircleShape)  // Make it circular
                            .background(Color.Gray)  // Background color
                            .clickable {
                                // Optional: Add action on clicking the image
                            }
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
                    Text("Hi, WelcomeBack",
                        color = Color(0xFF2260FF),
                        fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                    )
                    Text(text = displayName ?: "User",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.leaguespartan_regular))
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Ensure the Box takes up the full width
                        .padding(end = 30.dp) // Optional: Add padding if needed
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd) // Align the Row to the end (right)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.notification),
                            contentDescription = "notification",
                            modifier = Modifier
                                .clickable {  }
                                .size(35.dp),
                            tint = Color.Unspecified
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            painter = painterResource(R.drawable.settings),
                            contentDescription = "settings",
                            modifier = Modifier
                                .clickable {  }
                                .size(35.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Row(modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 31.dp)){

            Icon(
                painter = painterResource(R.drawable.doctors),
                contentDescription = "doctors",
                modifier = Modifier
                    .clickable {  }
                    .size(40.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(10.dp))

            Icon(
                painter = painterResource(R.drawable.favorite),
                contentDescription = "favourite",
                modifier = Modifier
                    .clickable {  }
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

        Column(modifier = Modifier.padding(start = 30.dp, end = 30.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
                .clip(RoundedCornerShape(17.dp))
                .background(Color(0xFFCAD6FF)))
            {
                /*
                Image(
                    painter = painterResource(id = R.drawable.your_image),  // Replace with your image resource
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterStart)
                        .padding(start = 33.dp)
                        .clip(CircleShape),  // Optional: if you want the image to be circular
                    contentScale = ContentScale.Crop  // This will ensure the image fills the space properly
                )

                 */
            }
        }
    }
}