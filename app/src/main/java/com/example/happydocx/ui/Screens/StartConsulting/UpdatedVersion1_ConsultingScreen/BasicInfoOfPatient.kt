package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.StartConsulting.SaveVital_SignsUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun BasicInfoOfPatient(
    modifier: Modifier = Modifier,
    token: String,
    appointmentId: String,
    startConsultingViewModel: StartConsultingViewModel,
    navController: NavController,
    patientId: String,
    doctorId: String
) {
    Scaffold(
        topBar = {
            ParticularPatientAppointmentInfoTopAppBar(
                onArrowBackClicked = {
                    navController.popBackStack()
                },
                onMenuBarIconClicked = {})
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xffF1F5F9)),
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xffFFFFFF)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PatientImage()
                    Spacer(Modifier.width(4.dp))
                    PatientInfoRow()
                    Spacer(Modifier.weight(1f))
                    BloodGroupComponent()
                }
                ContactInfoOfPatient()
                ActiveAllergiesSection()
            }
            Spacer(Modifier.height(8.dp))
            PatientAppointmentInfoTabScreen(
                token = token,
                appointmentId = appointmentId,
                startConsultingViewModel = startConsultingViewModel,
                navController = navController,
                patientId = patientId,
                doctorId = doctorId
            )
        }
    }
}


// create my topAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticularPatientAppointmentInfoTopAppBar(
    modifier: Modifier = Modifier,
    onArrowBackClicked: () -> Unit,
    onMenuBarIconClicked: () -> Unit
) {
    TopAppBar(
        title = { Text("Appointment Detail", color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xff2563EB)
        ),
        navigationIcon = {
            IconButton(
                onClick = { onArrowBackClicked() },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "arrow back",
                    tint = Color.White,
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onMenuBarIconClicked() },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "arrow back",
                    tint = Color.White,
                )
            }
        }
    )
}

@Composable
fun PatientImage(
    modifier: Modifier = Modifier,
    patientName: String = "Deepak Guleria",
) {
    Image(
        painter = painterResource(R.drawable.patientimage),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .background(color = Color(0xffFAFAFA))
            .size(70.dp)
            .clip(shape = CircleShape)
            .padding(8.dp)
    )
}

@Composable
fun PatientInfoRow(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.background(color = Color(0xffFFFFFF))
    ) {
        Text(
            text = "Samruddhi Panda",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
//        Surface(
//            modifier = Modifier,
//            shape = RoundedCornerShape(16.dp),
//            color = Color(0xffF1F5F9)
//        ) {
        Text(
            text = "PAT-20251212-00002",
            fontSize = 12.sp,
            color = Color(0xff47556E)
        )
//        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color(0xffFFFFFF))
        ) {
            Text(
                text = "10 yrs, Female",
                fontSize = 12.sp,
                color = Color(0xff64748B)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "2/18/2026, 9:35:39 AM",
                fontSize = 12.sp,
                color = Color(0xff64748B)
            )
        }
    }
}


@Composable
fun BloodGroupComponent(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xffFEF2F2),
        modifier = modifier
            .padding(8.dp)
            .border(width = 1.dp, color = Color(0xffFEE2E2))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.blood_drop),
                contentDescription = null,
                tint = Color(0xffEF4444),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "A+",
                fontSize = 13.sp,
                color = Color(0xffB91C1C)
            )
        }
    }
}


@Composable
fun ActiveAllergiesSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xffFFFFFF))
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "ALLERGIES :",
            fontSize = 14.sp,
            color = Color(0xff94A3B8)
        )
        Spacer(Modifier.width(4.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            items(6) {
                SuggestionChip(
                    onClick = {},
                    label = { Text("Penicilin") },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color(0xffFEF2F2),
                        labelColor = Color(0xffDC264E),
                    ),
                    border = BorderStroke(width = 1.dp, color = Color(0xffFEE2E2))
                )
            }
        }
    }
}

@Preview
@Composable
fun ContactInfoOfPatient(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffFFFFFF))
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.phone_24),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xffB1BCCB)
                )
                Spacer(Modifier.width(4.dp))
                Text("1234567890", color = Color.Black)
            }
            Spacer(Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.MailOutline,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xffB1BCCB)
                )
                Spacer(Modifier.width(4.dp))
                Text("deepak@gmail.com", color = Color.Black)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(6.dp)) {
            Icon(
                painter = painterResource(R.drawable.map),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color(0xffB1BCCB)
            )
            Spacer(Modifier.width(4.dp))
            Text("Pathankot, Punjab", color = Color.Black)
        }
    }
}

@Composable
fun PatientAppointmentInfoTabScreen(
    modifier: Modifier = Modifier,
    token: String,
    appointmentId: String,
    startConsultingViewModel: StartConsultingViewModel,
    navController: NavController,
    patientId: String,
    doctorId: String
) {

    val tabsOptions = listOf<String>(
        "Overview", "Vitals", "Medications", "LabResult", "History", "Documents", "Notes"
    )
    var tabIndex by rememberSaveable { mutableStateOf(0) }
    var showFullScreen by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Column(modifier = modifier.fillMaxSize()) {
        SecondaryScrollableTabRow(
            selectedTabIndex = tabIndex,
            scrollState = scrollState,
            containerColor = Color(0xffFFFFFF),
            contentColor = Color(0xff1E293B),
            edgePadding = 3.dp
        ) {
            tabsOptions.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = tabIndex == index,
                    selectedContentColor = Color(0xff2563EB),
                    unselectedContentColor = Color(0xff727C86),

                    onClick = {
                        tabIndex = index
                        showFullScreen = true  // Open full screen when tab clicked
                    }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when (tabIndex) {
                0 -> OverViewScreen(
                    token = token,
                    appointmentId = appointmentId,
                    startConsultingViewModel = startConsultingViewModel,
                    navController = navController,
                    patientId = patientId,
                    doctorId = doctorId
                )

                1 -> AllVitalsScreen()
                2 -> AllMedicationList()
                3 -> LabResultScreen()
                4 -> AllHistoryList()
                5 -> AllDocumentsScreen()
                6 -> NotesScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewVitalSignsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    startConsultingViewModel: StartConsultingViewModel,
    patientId: String,
    appointmentId: String,
    token: String,
    doctorId: String
) {
    val startConsultingState =
        startConsultingViewModel._startConsultationfUpdatedVersion.collectAsStateWithLifecycle().value
    // get the network state
    val saveVitalsState = startConsultingViewModel._saveVitals.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(saveVitalsState) {
        when (saveVitalsState) {
            is SaveVital_SignsUiState.Success -> {
                Toast.makeText(context, "Vitals saved successfully!", Toast.LENGTH_SHORT).show()
                navController.popBackStack() // Go back to Overview after saving
            }

            is SaveVital_SignsUiState.Error -> {
                Toast.makeText(context, "Error: ${saveVitalsState.message}", Toast.LENGTH_LONG)
                    .show()
            }

            else -> {}
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Vital Signs") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff2563EB)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        containerColor = Color(0xffFBFCFD)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(12.dp)
                .fillMaxSize()
                .background(Color(0xffFBFCFD))
        ) {
            Text(
                text = "Heart Rate",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = startConsultingState.heartRate,
                onValueChange = { startConsultingViewModel.on_Heart_RateChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("72 bpm", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Oxygen Saturation",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = startConsultingState.oxygenSaturation,
                onValueChange = { startConsultingViewModel.on_Oxygen_Saturation_Changed(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("98 %", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Systolic BP",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = startConsultingState.systolicBp,
                onValueChange = { startConsultingViewModel.on_SystolicBpChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("mmHg", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Diastolic BP",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = startConsultingState.diastolicBp,
                onValueChange = { startConsultingViewModel.on_DystolicBpChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("mmHg", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Respiratory Rate",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = startConsultingState.respiratoryRate,
                onValueChange = { startConsultingViewModel.on_RespiratoryRateChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("/min", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Temperature",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = startConsultingState.temperature,
                onValueChange = { startConsultingViewModel.on_TemperatureChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("°C", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Weight",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = startConsultingState.weight,
                onValueChange = { startConsultingViewModel.on_WeightChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("KG", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black

                )
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1D4ED8)
                    )
                ) {
                    Text("Cancel", color = Color.White)
                }
                Spacer(Modifier.width(4.dp))
                Button(
                    onClick = {
                        scope.launch {
                            startConsultingViewModel.Save_patient_Vitals(
                                token = token,
                                doctor = doctorId,
                                patient = patientId,
                                appointment = appointmentId
                            )
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1D4ED8)
                    )
                ) {
                    Text("Save Vitals", color = Color.White)
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewMedicationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    startConsultingViewModel: StartConsultingViewModel
) {
    val addMedicationUiState =
        startConsultingViewModel._addMedicationUpdateVersion.collectAsStateWithLifecycle().value

    val frequencyList = listOf(
        "Once daily",
        "Twice daily",
        "Three times daily",
        "Every four hrs",
        "As needed"
    )
    val routesList = listOf(
        "Oral(PO)",
        "Intravenous(IV)",
        "Intramuscular(IM)",
        "Subcutaneous(SC)",
        "Topical",
    )

    val timingList = listOf<String>(
        "Before Meal",
        "After Meal",
        "With Food",
        "Empty Stomach",
        "At BedTime"
    )
    var datePickerState by rememberSaveable { mutableStateOf(false) }
    val datePickerStateRemember = rememberDatePickerState()
    var frequencyExpandState by rememberSaveable { mutableStateOf(false) }
    var routExpandState by rememberSaveable { mutableStateOf(false) }
    var timingExpandState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Medications") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff2563EB)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        containerColor = Color(0xffFBFCFD)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(12.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xffFBFCFD))
        ) {
            Text(
                text = "Medication Name",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = addMedicationUiState.medicationName,
                onValueChange = { startConsultingViewModel.onMedicationNameChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("eg. Amoxicillin", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Dosage",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = addMedicationUiState.medicationDosage,
                onValueChange = { startConsultingViewModel.onDosageChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("72 bpm", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Text(
                text = "Frequency",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = frequencyExpandState,
                onExpandedChange = { frequencyExpandState = !frequencyExpandState },
            ) {
                OutlinedTextField(
                    value = addMedicationUiState.medicationFrequency,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Twice Daily", color = Color(0xffF5EEEF)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffF8FAFC),
                        unfocusedContainerColor = Color(0xffF8FAFC),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                )
                ExposedDropdownMenu(
                    expanded = frequencyExpandState,
                    onDismissRequest = { frequencyExpandState = !frequencyExpandState },
                    containerColor = Color(0xffF8FAFC),
                    matchTextFieldWidth = true,
                ) {
                    frequencyList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                startConsultingViewModel.onFrequencyChanged(it)
                                frequencyExpandState = false
                            }
                        )
                    }
                }
            }
            Text(
                text = "Duration",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = addMedicationUiState.medicationDuration,
                onValueChange = { startConsultingViewModel.onDurationChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("72 bpm", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Text(
                text = "Route",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = routExpandState,
                onExpandedChange = { routExpandState = !routExpandState },
            ) {
                OutlinedTextField(
                    value = addMedicationUiState.medicationRoute,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select Route", color = Color(0xffF5EEEF)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffF8FAFC),
                        unfocusedContainerColor = Color(0xffF8FAFC),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                )
                ExposedDropdownMenu(
                    expanded = routExpandState,
                    onDismissRequest = { routExpandState = !routExpandState },
                    containerColor = Color(0xffF8FAFC),
                    matchTextFieldWidth = true,
                ) {
                    routesList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                startConsultingViewModel.onRouteChanged(it)
                                routExpandState = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Timing",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            ExposedDropdownMenuBox(
                expanded = timingExpandState,
                onExpandedChange = { timingExpandState = !timingExpandState },
            ) {
                OutlinedTextField(
                    value = addMedicationUiState.medicationTiming,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select Timing", color = Color(0xffF5EEEF)) },
                    trailingIcon = {

                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffF8FAFC),
                        unfocusedContainerColor = Color(0xffF8FAFC),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                )
                ExposedDropdownMenu(
                    expanded = timingExpandState,
                    onDismissRequest = { timingExpandState = !timingExpandState },
                    containerColor = Color(0xffF8FAFC),
                    matchTextFieldWidth = true,
                ) {
                    timingList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                startConsultingViewModel.onTimingChanged(it)
                                timingExpandState = false
                            }
                        )
                    }
                }
            }
            Text(
                text = "Start Date",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = addMedicationUiState.medicationDate,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Timing", color = Color(0xffF5EEEF)) },
                trailingIcon = {
                    IconButton(
                        onClick = { datePickerState = !datePickerState }
                    ) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (datePickerState) {
                DatePickerDialog(
                    shape = RoundedCornerShape(30.dp),
                    colors = DatePickerDefaults.colors(
                        // add color to date picker dialog
                        containerColor = Color(0xffebedfc)
                    ),
                    onDismissRequest = { datePickerState = false },
                    confirmButton = {
                        TextButton(onClick = {
                            // Convert milliseconds to formatted date string
                            val selectedMillis = datePickerStateRemember.selectedDateMillis
                            if (selectedMillis != null) {
                                val formattedDate =
                                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                        .format(Date(selectedMillis))
                                startConsultingViewModel.onDateChanged(formattedDate) //  Save to ViewModel
                            }
                            datePickerState = false
                        }) { Text("OK", color = Color.Black) }
                    },
                    dismissButton = {
                        TextButton(onClick = { datePickerState = false }) {
                            Text("Cancel", color = Color.Black)
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerStateRemember,
                        colors = DatePickerDefaults.colors(
                            containerColor = Color(0xffebedfc),
                            dayContentColor = Color.Black,
                            titleContentColor = Color.Black,
                            weekdayContentColor = Color.Black,
                            headlineContentColor = Color.Black,
                            navigationContentColor = Color.Black,
                            subheadContentColor = Color.Black,
                            dateTextFieldColors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            )
                        )
                    )
                }
            }


            Text(
                text = "Additional Information",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = addMedicationUiState.medicationNotes,
                onValueChange = { startConsultingViewModel.onNotesChanged(it) },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Any Special Instruction", color = Color(0xffF5EEEF)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp)
            ){
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1D4ED8)
                    )
                ) {
                    Text("Add Prescription", color = Color.White,modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(4.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1D4ED8)
                    )
                ) {
                    Text("Cancel", color = Color.White,modifier = Modifier.padding(4.dp),fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AddNewLabResultScreen(modifier: Modifier = Modifier) {

}





