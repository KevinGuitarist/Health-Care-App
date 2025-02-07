package com.kevinguitarist.healthcareappown1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun TimeSelectionDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val times = listOf(
        "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM",
        "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM",
        "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM"
    )
    var selectedStartTime by remember { mutableStateOf(times[0]) }
    var selectedEndTime by remember { mutableStateOf(times[8]) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp), // Set a fixed height for the card
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Working Hours",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Start Time Section
                Text(
                    text = "From",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(times) { time ->
                        Text(
                            text = time,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedStartTime = time }
                                .background(
                                    if (time == selectedStartTime) Color(0xFFEAEFFF)
                                    else Color.Transparent
                                )
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            color = if (time == selectedStartTime) Color(0xFF2260FF) else Color.Black,
                            fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // End Time Section
                Text(
                    text = "To",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(times) { time ->
                        Text(
                            text = time,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedEndTime = time }
                                .background(
                                    if (time == selectedEndTime) Color(0xFFEAEFFF)
                                    else Color.Transparent
                                )
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            color = if (time == selectedEndTime) Color(0xFF2260FF) else Color.Black,
                            fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = "Cancel",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium))
                        )
                    }

                    Button(
                        onClick = { onConfirm(selectedStartTime, selectedEndTime) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2260FF)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "Confirm",
                            fontFamily = FontFamily(Font(R.font.leaguespartan_medium))
                        )
                    }
                }
            }
        }
    }
}