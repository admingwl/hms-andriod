package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.ViewModels.StartConsulting.CurrentMedicationListUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel


@Composable
fun AllMedicationList(
    modifier: Modifier = Modifier,
    startConsultingViewModel: StartConsultingViewModel,
    token:String,
    appointmentId:String
) {
    val allMedicationListState = startConsultingViewModel._currentMedicationState.collectAsStateWithLifecycle().value
    LaunchedEffect(token){
        startConsultingViewModel.getCurrentMedications(
            token = token,
            appointmentId = appointmentId
        )
    }
    when(val state = allMedicationListState) {
        is CurrentMedicationListUiState.Idle -> {}
        is CurrentMedicationListUiState.Loading -> {
            Box(
                modifier  = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator()
            }
        }
        is CurrentMedicationListUiState.Success -> {
            val successState = state.data.data
            Column(
                modifier = modifier.fillMaxWidth().background(Color(0xffFAFAFA))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color(0xffFAFAFA))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Current Medication",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff1D4ED8),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Text(
                            "Add",
                            color = Color.White
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    items(successState){it->
                        CurrentMedicationCard(
                            medicationName = it.name,
                            medicationDose = it.dosage,
                            medicationFrequency = it.frequency,
                            medicationStarted = "Started: ${DateUtils.gettingOnlyDate(it.startDate)}",
                            medicationStatus = if (it.active) "Active" else "Inactive"
                        )
                    }
                }
            }
        }

        else -> {}
    }
}