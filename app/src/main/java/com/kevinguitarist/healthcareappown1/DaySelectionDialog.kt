package com.kevinguitarist.healthcareappown1

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
fun DaySelectionDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    var selectedStartDay by remember { mutableStateOf(days[0]) }
    var selectedEndDay by remember { mutableStateOf(days[5]) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Select Working Days",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Start Day Selection
                Text(
                    text = "From",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                days.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedStartDay = day }
                            .background(
                                if (day == selectedStartDay) Color(0xFFEAEFFF)
                                else Color.Transparent
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        color = if (day == selectedStartDay) Color(0xFF2260FF) else Color.Black,
                        fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // End Day Selection
                Text(
                    text = "To",
                    fontFamily = FontFamily(Font(R.font.leaguespartan_medium)),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )

                days.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedEndDay = day }
                            .background(
                                if (day == selectedEndDay) Color(0xFFEAEFFF)
                                else Color.Transparent
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                        color = if (day == selectedEndDay) Color(0xFF2260FF) else Color.Black,
                        fontFamily = FontFamily(Font(R.font.leaguespartan_light))
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

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
                        onClick = { onConfirm(selectedStartDay, selectedEndDay) },
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