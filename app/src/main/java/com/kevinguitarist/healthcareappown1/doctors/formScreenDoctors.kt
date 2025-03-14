package com.kevinguitarist.healthcareappown1.doctors

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.kevinguitarist.healthcareappown.ui.theme.button_Color
import com.kevinguitarist.healthcareappown1.database.DoctorDatabaseManager
import com.kevinguitarist.healthcareappown1.database.DoctorInformation
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.kevinguitarist.healthcareappown1.database.DoctorImageUploader
import kotlinx.coroutines.launch
import androidx.compose.ui.window.Dialog
import com.kevinguitarist.healthcareappown1.HomeScreenDoctor
import com.kevinguitarist.healthcareappown1.R

@Composable
fun formscreenDoctors(navHostController: NavHostController, context: Context){
    var doctorName by remember { mutableStateOf("XYZ") }
    val doctorDatabaseManager = remember { DoctorDatabaseManager() }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var imageUrl by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        doctorName = user?.displayName ?: "No name available" // Use a default message if name is null
    }

    var profile by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }

    var experience by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }

    var focus by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }

    val characterLimit = 250

    var about by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }

    var career_path by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }

    var Highlights by rememberSaveable(stateSaver = TextFieldValue.Saver){
        mutableStateOf(TextFieldValue(""))
    }

    val scrollState = rememberScrollState()

    var showDaySelectionDialog by remember { mutableStateOf(false) }

    var selectedDays by remember { mutableStateOf("Monday - Saturday") }

    var showTimeSelectionDialog by remember { mutableStateOf(false) }

    var selectedTime by remember { mutableStateOf("9:00 AM - 5:00 PM") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedImageUri = it }
    }

    val imageUploader = remember { DoctorImageUploader() }

    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        imageUploader.initializeCloudinary(context)
    }

    fun saveDoctorInformation() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }
        scope.launch {
            try {
                isLoading = true

                val imageUrlResult = selectedImageUri?.let { uri ->
                    try {
                        // Get file size
                        val fileSize = context.contentResolver.openFileDescriptor(uri, "r")?.statSize ?: 0

                        // Check if image is too large (e.g., > 5MB)
                        if (fileSize > 5 * 1024 * 1024) {
                            Toast.makeText(context, "Image too large. Please select an image under 5MB", Toast.LENGTH_LONG).show()
                            isLoading = false
                            return@launch
                        }

                        // Compress and upload image
                        imageUploader.uploadImage(context, uri)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                        isLoading = false
                        return@launch
                    }
                }

                val doctorInfo = DoctorInformation(
                    doctorName = doctorName,
                    profile = profile.text,
                    experience = experience.text,
                    focus = focus.text,
                    about = about.text,
                    careerPath = career_path.text,
                    highlights = Highlights.text,
                    workingDays = selectedDays,
                    workingHours = selectedTime,
                    imageUrl = imageUrlResult ?: "",
                    userId = userId // The user id that exists in firebaseAuth (this will be very long, e.g. "APUK15ff1eYkURvCfbLhcsEGpLL2")
                )

                doctorDatabaseManager.saveDoctorInformation(
                    doctorInfo = doctorInfo,
                    onSuccess = {
                        isLoading = false
                        Toast.makeText(context, "Information saved successfully", Toast.LENGTH_SHORT).show()
                    },
                    onError = { errorMessage ->
                        isLoading = false
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                )
            } catch (e: Exception) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 30.dp, top = 75.dp, end = 30.dp, bottom = 32.dp)
                .verticalScroll(scrollState)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(text = "Name",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(text = "Dr. $doctorName",
                    color = Color.Blue,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Profile",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(7.dp))

                BasicTextField(
                    value = profile,
                    onValueChange = {profile = it},
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
                            if (profile.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.align(Alignment.CenterStart),
                                    text = "eg : Dermatologist",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 14.sp
                                )
                            }
                            Box(modifier = Modifier.align(Alignment.CenterStart)){
                                innerTextField()
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "Upload Profile Picture",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                val stroke = Stroke(width = 5f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )

                Box(
                    modifier = Modifier
                        .height(39.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .clickable { imagePickerLauncher.launch("image/*") }
                        .background(color = Color(0xFFEAEFFF))
                        .drawBehind {
                            drawRoundRect(color = Color.Blue, style = stroke)
                        }
                ) {
                    Row(modifier = Modifier.fillMaxWidth().align(Alignment.Center).padding(start = 10.dp)) {
                        Icon(painter = painterResource(R.drawable.picture),
                            contentDescription = "Picture",
                            tint = Color.Unspecified
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(text = "Browse",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            fontSize = 14.sp,
                            color = Color.Blue,
                            modifier = Modifier.padding(top = 3.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(text = "your",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 3.dp)
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(text = "image",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 3.dp)
                        )

                        Spacer(modifier = Modifier.width(2.dp))

                        Text(text = "here",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            fontSize = 14.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(top = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(36.dp))

                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFECF1FF))
                        .align(Alignment.CenterHorizontally)
                        .clickable { /* Image picker logic will go here */ },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.user),
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "Experience",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                BasicTextField(
                    value = experience,
                    onValueChange = {newValue ->
                        // Only allow numbers and limit to 2 digits
                        if (newValue.text.isEmpty() || (newValue.text.all { it.isDigit() } && newValue.text.length <= 2)) {
                            experience = newValue
                        } },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier.height(35.dp).width(80.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(8.dp)
                    ),
                    maxLines = 1,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 13.dp)
                        ){
                            if (experience.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.align(Alignment.CenterStart),
                                    text = "eg: 25yrs",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 14.sp
                                )
                            }
                            Box(modifier = Modifier.align(Alignment.CenterStart)){
                                innerTextField()
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(text = "Focus",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                BasicTextField(
                    value = focus,
                    onValueChange = { newText ->
                        if (newText.text.length <= 250) {  // Limit to 250 characters
                            focus = newText
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(80.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = Int.MAX_VALUE,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 13.dp)
                        ){
                            if (focus.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.align(Alignment.TopStart).padding(top = 8.dp),
                                    text = "Which part you focus on?",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 14.sp
                                )
                            }
                            Box(modifier = Modifier.align(Alignment.TopStart).padding(top = 10.dp, end = 5.dp)){
                                innerTextField()
                            }
                        }
                    }
                )

                Text(
                    text = "${characterLimit - focus.text.length} characters left",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 2.dp, end = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "About",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                BasicTextField(
                    value = about,
                    onValueChange = { newText ->
                        if (newText.text.length <= 250) {  // Limit to 250 characters
                            about = newText
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(80.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = Int.MAX_VALUE,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 13.dp)
                        ){
                            if (about.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.align(Alignment.TopStart).padding(top = 8.dp),
                                    text = "Write about yourself",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 14.sp
                                )
                            }
                            Box(modifier = Modifier.align(Alignment.TopStart).padding(top = 10.dp, end = 5.dp)){
                                innerTextField()
                            }
                        }
                    }
                )

                Text(
                    text = "${characterLimit - about.text.length} characters left",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 2.dp, end = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = "Career Path",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                BasicTextField(
                    value = career_path,
                    onValueChange = { newText ->
                        if (newText.text.length <= 250) {  // Limit to 250 characters
                            career_path = newText
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(80.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = Int.MAX_VALUE,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 13.dp)
                        ){
                            if (career_path.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.align(Alignment.TopStart).padding(top = 8.dp),
                                    text = "Describe your career path",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 14.sp
                                )
                            }
                            Box(modifier = Modifier.align(Alignment.TopStart).padding(top = 10.dp, end = 5.dp)){
                                innerTextField()
                            }
                        }
                    }
                )

                Text(
                    text = "${characterLimit - career_path.text.length} characters left",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 2.dp, end = 8.dp)
                )

                Text(text = "Highlights",
                    color = Color.Black,
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                BasicTextField(
                    value = Highlights,
                    onValueChange = { newText ->
                        if (newText.text.length <= 250) {  // Limit to 250 characters
                            Highlights = newText
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(80.dp).background(Color(0xFFECF1FF),
                        RoundedCornerShape(13.dp)
                    ),
                    maxLines = Int.MAX_VALUE,
                    decorationBox = { innerTextField ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 13.dp)
                        ){
                            if (Highlights.text.isEmpty()) {
                                Text(
                                    modifier =  Modifier.align(Alignment.TopStart).padding(top = 8.dp),
                                    text = "Write your highlights",
                                    color = Color(0xFF809CFF),
                                    fontFamily = FontFamily(Font(R.font.leaguespartan_regular)),
                                    fontSize = 14.sp
                                )
                            }
                            Box(modifier = Modifier.align(Alignment.TopStart).padding(top = 10.dp, end = 5.dp)){
                                innerTextField()
                            }
                        }
                    }
                )

                Text(
                    text = "${characterLimit - Highlights.text.length} characters left",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 2.dp, end = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.fillMaxWidth()){
                    Column() {
                        Text(text = "Day",
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .height(39.dp)
                                .width(140.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .clickable { showDaySelectionDialog = true }
                                .background(color = Color(0xFFEAEFFF))
                                .drawBehind {
                                    drawRoundRect(color = Color.Blue, style = stroke)
                                }
                        ){
                            Text(text = selectedDays,
                                color = Color(0xFF2260FF),
                                fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                                fontSize = 14.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            if (showDaySelectionDialog) {
                                DaySelectionDialog(
                                    onDismiss = { showDaySelectionDialog = false },
                                    onConfirm = { startDay, endDay ->
                                        selectedDays = "$startDay - $endDay"
                                        showDaySelectionDialog = false
                                    }
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.width(40.dp))

                    Column() {
                        Text(text = "Time",
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .height(39.dp)
                                .width(125.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .clickable { showTimeSelectionDialog = true }
                                .background(color = Color(0xFFEAEFFF))
                                .drawBehind {
                                    drawRoundRect(color = Color.Blue, style = stroke)
                                }
                        ){
                            Text(text = selectedTime,
                                color = Color(0xFF2260FF),
                                fontFamily = FontFamily(Font(R.font.leaguespartan_light)),
                                fontSize = 14.sp,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            if (showTimeSelectionDialog) {
                                TimeSelectionDialog(
                                    onDismiss = { showTimeSelectionDialog = false },
                                    onConfirm = { startTime, endTime ->
                                        selectedTime = "$startTime - $endTime"
                                        showTimeSelectionDialog = false
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                Button(
                    onClick = {
                        when {
                            profile.text.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            experience.text.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            focus.text.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            about.text.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            career_path.text.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            Highlights.text.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            selectedDays.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            selectedTime.trim().isEmpty() -> {
                                Toast.makeText(context, "Fill all the details to proceed", Toast.LENGTH_SHORT).show()
                            }
                            selectedImageUri == null -> {
                                Toast.makeText(context, "Please upload your profile picture", Toast.LENGTH_SHORT).show()
                            }

                            else -> {
                                saveDoctorInformation()
                                navHostController.navigate(HomeScreenDoctor.route)
                            }
                        } },
                    colors = ButtonDefaults.buttonColors(button_Color.Blue),
                    modifier = Modifier
                        .width(180.dp)
                        .height(45.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Proceed",
                        fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                        fontSize = 24.sp
                    )
                }
            }
        }

        // Loading overlay
        if (isLoading) {
            Dialog(
                onDismissRequest = { }
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Blue,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}