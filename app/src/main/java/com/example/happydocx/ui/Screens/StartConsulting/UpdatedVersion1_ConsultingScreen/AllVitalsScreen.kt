package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.VitalHistory.Data
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.ViewModels.StartConsulting.AllMedicalRecordUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.PatientVitalHistoryUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel


@Composable
fun AllVitalsScreen(
    modifier: Modifier = Modifier,
    startConsultingViewModel: StartConsultingViewModel,
    token:String,
    patientId:String
) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    val optionsSegmented by rememberSaveable {
        mutableStateOf(
            listOf(
                "Current",
                "History",
                "Trends"
            )
        )
    }

    val patientHistoryState  = startConsultingViewModel._patientVitalHistory.collectAsStateWithLifecycle().value
    LaunchedEffect(token) {
        startConsultingViewModel.getPatientVitalHistory(
            token = token,
            patientId = patientId
        )
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xffFAFAFA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Current Vitals",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1D4ED8)
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Add Vitals",
                    color = Color.White
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
            // here I use the single segmented Button
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ){
               optionsSegmented.forEachIndexed { index, string ->
                    SegmentedButton(
                        onClick = { selectedIndex = index},
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = optionsSegmented.size
                        ),
                        selected = selectedIndex == index,
                        label = {Text(string)}
                    )
               }
            }
        }

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ){

            when(selectedIndex){
                0 -> VitalCard(startConsultingViewModel)
                1 ->VitalHistorySection(patientHistoryState = patientHistoryState)
                2 -> Text("Trends ")
            }
        }

    }
}


@Composable
fun VitalHistorySection(patientHistoryState: PatientVitalHistoryUiState) {
    when (patientHistoryState) {

        is PatientVitalHistoryUiState.Idle,
        is PatientVitalHistoryUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xff1D4ED8))
            }
        }

        is PatientVitalHistoryUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = patientHistoryState.message, color = Color.Red)
            }
        }

        is PatientVitalHistoryUiState.Success -> {
            val historyList = patientHistoryState.data.data

            if (historyList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No vital history found", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(
                        items = historyList,
                        key = { it._id } //  prevents the remember recycling bug
                    ) { data ->
                            VitalHistoryCard(data = data)
                    }
                }
            }
        }
    }
}

// vital cards
@Composable
fun VitalCard(
    startConsultingViewModel: StartConsultingViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }
    val startConsultingViewModelNetworkState = startConsultingViewModel._medicalRecordState.collectAsStateWithLifecycle().value

    when(startConsultingViewModelNetworkState){
        is AllMedicalRecordUiState.Loading -> {
            // Show loading state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color(0xff1D4ED8),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        is AllMedicalRecordUiState.Success -> {
            // Show the vital card with data
            // we can use the data from the success state to populate the card, for now I will just show a static card
            val data = startConsultingViewModelNetworkState.data
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xffFFFFFF)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffFFFFFF))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .padding(8.dp)
                            .background(Color(0xffFFFFFF))
                    ) {
                        DateUtils.formatAppointmentDate(data.data.date)?.let { Text(it, color = Color.Black) }
                        Spacer(Modifier.weight(1f))
                        IconButton(
                            onClick = { isExpanded = !isExpanded }
                        ){
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = isExpanded
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            PatientVitalSignsInsideCard(data = data)
                        }
                    }
                }
            }
        }
        is AllMedicalRecordUiState.Error -> {
            // Show error state
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Error loading medical record")
            }
        }
        else -> {}
    }
}



@Composable
fun PatientVitalSignsInsideCard(
    modifier: Modifier = Modifier,
    data: AllVitalSignsResponse
    ) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp) // increased from 4.dp
            .background(Color(0xffFFFFFF)),
        verticalArrangement = Arrangement.spacedBy(4.dp) // increased from 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(4.dp) // consistent spacing
       ) {
           CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.heart, normalRange = "Normal: 60-100", cardValue = "${data.data.vitals?.heartRate}")
          CurrentVitalCard(modifier = Modifier.weight(1f) ,cardName = "BP", normalRange = "Normal: 120-80", cardValue = "${data.data.vitals?.bpSystolic}" + "/" + "${data.data.vitals?.bpDiastolic}")
       }
      Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
       ) {
           CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.temperature ,cardName = "Temperature", normalRange = "Normal: 97-99", cardValue = "${data.data.vitals?.temperature}")
            CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.wind,cardName = "Respiration", normalRange = "Normal: 12-20", cardValue = "${data.data.vitals?.respiratoryRate}")
      }
       Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.spacedBy(4.dp) // consistent spacing
       ) {
           CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.blood_drop,cardName = "Oxygen", normalRange = "Normal: >95", cardValue = "${data.data.vitals?.oxygenSaturation}")
            CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.weight,cardName = "Weight", normalRange = "Normal: 50-80", cardValue = "${data.data.vitals?.weight}")
       }
    }
}


@Composable
fun VitalHistoryCard(data: Data) {
    var isExpanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {

            // Header — date + expand toggle
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DateUtils.formatAppointmentDate(data.recordedAt)?.let { //  recordedAt not date
                    Text(
                        text = it,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Expandable vitals grid
            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        CurrentVitalCard(
                            modifier = Modifier.weight(1f),
                            cardIcon = R.drawable.heart,
                            cardName = "Heart Rate",
                            normalRange = "Normal: 60-100",
                            cardValue = "${data.heartRate}" //  directly on Data
                        )
                        CurrentVitalCard(
                            modifier = Modifier.weight(1f),
                            cardName = "BP",
                            normalRange = "Normal: 120/80",
                            cardValue = "${data.bpSystolic}/${data.bpDiastolic}" //  directly on Data
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        CurrentVitalCard(
                            modifier = Modifier.weight(1f),
                            cardIcon = R.drawable.temperature,
                            cardName = "Temperature",
                            normalRange = "Normal: 97-99",
                            cardValue = "${data.temperature}" //  Double
                        )
                        CurrentVitalCard(
                            modifier = Modifier.weight(1f),
                            cardIcon = R.drawable.wind,
                            cardName = "Respiration",
                            normalRange = "Normal: 12-20",
                            cardValue = "${data.respiratoryRate}"
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        CurrentVitalCard(
                            modifier = Modifier.weight(1f),
                            cardIcon = R.drawable.blood_drop,
                            cardName = "Oxygen",
                            normalRange = "Normal: >95",
                            cardValue = "${data.oxygenSaturation}"
                        )
                        CurrentVitalCard(
                            modifier = Modifier.weight(1f),
                            cardIcon = R.drawable.weight,
                            cardName = "Weight",
                            normalRange = "Normal: 50-80",
                            cardValue = "${data.weight}"
                        )
                    }
                }
            }
        }
    }
}