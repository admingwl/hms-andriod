package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import android.widget.GridLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happydocx.R

@Composable
fun OverViewScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xffFAFAFA))
    ) {
       Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
           Column {
               Text(
                   text = "Current Vitals",
                   fontWeight = FontWeight.Bold,
                   fontSize = 20.sp,
                   color = Color.Black,
                   modifier = Modifier.padding(8.dp)
               )
               Text(
                   text = "Last Recorded: 1/14/2026, 4:30:49 PM",
                   color = Color(0xff647BAB),
                   fontSize = 14.sp,
                   modifier = Modifier.padding(horizontal = 8.dp)
               )
           }
           Spacer(Modifier.weight(1f))
           Button(
               onClick = {},
               colors = ButtonDefaults.buttonColors(
                   containerColor = Color(0xff1D4ED8),
                   contentColor = Color.White
               ),
               shape = RoundedCornerShape(4.dp),
               modifier = Modifier.padding(end = 8.dp)
           ) {
               Text(
                   text = "Add Vitals"
               )
           }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)) { CurrentVitalCard()
                Spacer(Modifier.weight(1f))
                CurrentVitalCard()
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
                CurrentVitalCard()
                Spacer(Modifier.weight(1f))
                CurrentVitalCard()
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(4.dp)) {
                CurrentVitalCard()
                Spacer(Modifier.weight(1f))
                CurrentVitalCard()
            }
        }



        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = "Current Medication",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1D4ED8),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = "Add"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
                    CurrentMedicationCard()
                    CurrentMedicationCard()
                    CurrentMedicationCard()
                    CurrentMedicationCard()
        }

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(
                    text = "Lab Results",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1D4ED8),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(
                    text = "Add Result"
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            LabResultCard()
            LabResultCard()
            LabResultCard()
            LabResultCard()
            LabResultCard()
        }

    }
}


@Composable
fun CurrentVitalCard(
    modifier: Modifier = Modifier,
    cardIcon: Int = R.drawable.heart,
    cardName: String = "Heart Rate",
    condition: String = "Warning",
    cardValue: String = "85 bpm",
    normalRange: String = "Normal: 60-100",
) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .background(color = Color(0xffFFFFFF))

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(cardIcon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xff16A34A)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = cardName,
                    color = Color(0xff475595),

                )
                Spacer(Modifier.width(20.dp))
                    Text(
                        text = condition,
                        color = Color(0xff15803D),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )

            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = cardValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color =Color(0xff1E293B)
            )
            Spacer(Modifier.height(4.dp))
            Text(normalRange, fontSize = 12.sp, color = Color(0xff94B3D8))
        }
    }
}

@Composable
fun CurrentMedicationCard(
    modifier: Modifier = Modifier,
    medicationName:String = "Uningym",
    medicationDose:String = "400mg",
    medicationFrequency:String = "Once Daily",
    medicationStarted:String="Started: Jan 12, 2026",
    medicationStatus:String = "Active"
) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Row {
                Column() {
                    Text(
                        text = medicationName,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = medicationDose,
                        color = Color(0xff6474A8),
                        fontSize = 12.sp
                    )

                }
                Spacer(Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFE6F9F0),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = medicationStatus,
                        color = Color(0xff15803D),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
            Spacer(Modifier.height(8.dp))
            Row() {
                Text(
                    text = medicationFrequency,
                    fontSize = 12.sp,
                    color = Color(0xff6474A8)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = medicationStarted,
                    fontSize = 12.sp,
                    color = Color(0xff6474A8)
                )
            }
        }
    }
}

@Preview
@Composable
fun LabResultCard(
    modifier: Modifier = Modifier,
    testName: String = "Complete Blood Count (CBC)",
    department: String = "Hematology Department",
    status: String = "NORMAL",
    resultValue: String = "13.5",
    resultUnit: String = "g/dL",
    normalRange: String = "12.0 – 16.0",
    date: String = "Dec 20, 2025"
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = testName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Text(
                        text = department,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFE6F9F0),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = status,
                        color = Color(0xFF15803D),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Result", fontSize = 14.sp, color = Color.Gray)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = resultValue,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = resultUnit,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Normal Range", fontSize = 14.sp, color = Color.Gray)
                    Text(
                        text = normalRange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Standard Reference",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
           Text(
               text = date,
               fontSize = 12.sp,
               color = Color.Gray
           )

        }
    }
}