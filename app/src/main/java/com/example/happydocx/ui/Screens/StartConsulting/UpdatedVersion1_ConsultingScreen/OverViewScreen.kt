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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.ViewModels.StartConsulting.AllMedicalRecordUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel
import kotlinx.coroutines.coroutineScope

@Composable
fun OverViewScreen(
    modifier: Modifier = Modifier,
    token: String,
    appointmentId: String,
    startConsultingViewModel: StartConsultingViewModel,
    navController: NavController,
    patientId:String,
    doctorId:String

) {

    val startConsultingState =
        startConsultingViewModel._medicalRecordState.collectAsStateWithLifecycle().value
    LaunchedEffect(token) {
        startConsultingViewModel.getAllMedicalRecords(token, appointmentId)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xffFAFAFA))
            .verticalScroll(rememberScrollState())
    ) {

        when (val state = startConsultingState) {

            is AllMedicalRecordUiState.Idle -> {

            }

            is AllMedicalRecordUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is AllMedicalRecordUiState.Success -> {
                val vitals = state.data.data.vitals
                val date = state.data.data.date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Current Vitals",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Last Recorded: ${DateUtils.gettingOnlyDate(date)}",
                            color = Color(0xff647BAB),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = {navController.navigate("addNewVitalSigns/$token/$appointmentId/$patientId/$doctorId")},
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

                    val oxygenCondition = startConsultingViewModel.getOxygenCondition(vitals.oxygenSaturation)
                    val temperatureCondition = startConsultingViewModel.getCondition(vitals.temperature, 97, 99)
                    val respiratoryCondition = startConsultingViewModel.getCondition(vitals.respiratoryRate, 12, 20)
                    val weightCondition = startConsultingViewModel.getCondition(vitals.weight, 50, 80)
                    val hrCondition = startConsultingViewModel.getCondition(vitals.heartRate, 60, 100)
                    val bpCondition = startConsultingViewModel.getBloodPressureCondition(vitals.bpSystolic, vitals.bpDiastolic)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CurrentVitalCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            conditionColor = startConsultingViewModel.getConditionColor(hrCondition),
                            cardValue = "${vitals.heartRate} bpm",
                            condition = startConsultingViewModel.getCondition(
                                vitals.heartRate,
                                60,
                                100
                            ),
                            normalRange = "60-100",
                            cardName = "Heart Rate",
                            cardIcon = R.drawable.heart
                        )
                        CurrentVitalCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            cardValue = "${vitals.bpSystolic}/${vitals.bpDiastolic} mmHg",
                            condition = startConsultingViewModel.getBloodPressureCondition(
                                vitals.bpSystolic,
                                vitals.bpDiastolic
                            ),
                            conditionColor = startConsultingViewModel.getConditionColor(bpCondition),
                            normalRange = "120/80",
                            cardName = "Blood Pressure",
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CurrentVitalCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            cardValue = "${vitals.temperature} °C",
                            conditionColor = startConsultingViewModel.getConditionColor(temperatureCondition),
                            condition = startConsultingViewModel.getCondition(
                                vitals.temperature,
                                97,
                                99
                            ),
                            normalRange = "97-99",
                            cardName = "Temperature",
                            cardIcon = R.drawable.temperature
                        )
                        CurrentVitalCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            conditionColor = startConsultingViewModel.getConditionColor(respiratoryCondition),
                            cardValue = "${vitals.respiratoryRate} /min",
                            condition = startConsultingViewModel.getCondition(
                                vitals.respiratoryRate,
                                12,
                                20
                            ),
                            normalRange = "12-20",
                            cardName = "Respiration",
                            cardIcon = R.drawable.wind
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        CurrentVitalCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            cardValue = "${vitals.oxygenSaturation}%",
                            condition = startConsultingViewModel.getOxygenCondition(vitals.oxygenSaturation),
                            normalRange = ">95",
                            cardName = "OxygenSaturation",
                            conditionColor = startConsultingViewModel.getConditionColor(oxygenCondition),
                            cardIcon = R.drawable.blood_drop
                        )
                        CurrentVitalCard(
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            cardValue = "${vitals.weight} Kg",
                            condition = startConsultingViewModel.getCondition(
                                vitals.weight,
                                50,
                                80
                            ),
                            normalRange = "50-80",
                            cardName = "Weight",
                            conditionColor = startConsultingViewModel.getConditionColor(weightCondition),
                            cardIcon = R.drawable.weight
                        )
                    }
                }
            }

            else -> {}
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
                onClick = {

                },
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
    conditionColor: Color = Color.Black,

    ) {
    ElevatedCard(
        modifier = modifier.height(110.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .background(color = Color(0xffFFFFFF))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(cardIcon),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = conditionColor
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = cardName,
                    color = Color(0xff475595),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                    fontSize = 13.sp

                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = condition,
                    color = conditionColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    modifier = Modifier.wrapContentWidth()
                )

            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = cardValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xff1E293B)
            )
            Spacer(Modifier.height(4.dp))
            Text(normalRange, fontSize = 12.sp, color = Color(0xff94B3D8))
        }
    }
}

@Composable
fun CurrentMedicationCard(
    modifier: Modifier = Modifier,
    medicationName: String = "Uningym",
    medicationDose: String = "400mg",
    medicationFrequency: String = "Once Daily",
    medicationStarted: String = "Started: Jan 12, 2026",
    medicationStatus: String = "Active"
) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
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