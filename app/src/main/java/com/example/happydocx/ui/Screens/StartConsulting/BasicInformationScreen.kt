package com.example.happydocx.ui.Screens.StartConsulting

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.happydocx.Data.Model.StartConsulting.ParticularPatient
import com.example.happydocx.Data.Model.StartConsulting.PrescriptionRecord
import com.example.happydocx.PdfGeneration.PrescriptionPdfGenerator
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.ViewModels.StartConsulting.BasicInformationUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.BasicInformationViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.MedicalRecordUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.MedicationUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.SaveVitalSignsUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.SubmitDiagnosisNotesSymptomsUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.TestAndInvestigation
import com.example.happydocx.ui.ViewModels.StartConsulting.UpdateAppointmentStatusUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.VitalSignAndSymptomsList
import com.example.happydocx.ui.uiStates.StartConsulting.InvestigationEntry
import com.example.happydocx.ui.uiStates.StartConsulting.MedicalEntry
import com.example.happydocx.ui.uiStates.StartConsulting.MedicationEntry
import com.example.happydocx.ui.uiStates.StartConsulting.StartConsultingUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultingMainScreen(
    navController: NavController,
    viewModel: BasicInformationViewModel,
    token: String,
    patientId: String,
    appointmentId: String,

    ) {
    val context = LocalContext.current
    val state = viewModel._state.collectAsStateWithLifecycle().value
    // api state
    val apiState = viewModel._apiState.collectAsStateWithLifecycle().value
    val submissionState = viewModel._submitState.collectAsStateWithLifecycle().value

    LaunchedEffect(patientId) {
        // pass patient id here through navigation
        viewModel.onStartConsultingClicked(appointmentId = appointmentId, token = token)
    }


    // when ever we use the sealed class no need to add the else branch in the when expression
    when (apiState) {
        is BasicInformationUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is BasicInformationUiState.Success -> {
            // first cast the data
            val data = apiState.data
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(color = Color(0xfff0f5ff)),
                content = {
                    ImageCard(
                        image = R.drawable.patientimage,
                        patientName = "${data.message.patient.firstName} ${data.message.patient.lastName}",
                        context = context,
                        patientId = data.message.id,
                        token = token,
                        appointmentId = appointmentId,
                        viewModel = viewModel,
                        appointmentStatus = data.message.status
                    )
                    // information cards
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        InformationCard(
                            label = "Date of Birth",
                            labelValue = DateUtils.gettingOnlyDate(data.message.patient.dateOfBirth),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.weight(0.2f))

                        InformationCard(
                            label = "Age",
                            labelValue = data.message.patient.ageFormatted,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        InformationCard(
                            label = "Gender",
                            labelValue = data.message.patient.gender,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.weight(0.2f))
                        InformationCard(
                            label = "Department",
                            labelValue = data.message.department.departmentName,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(Modifier.height(4.dp))
                    InformationCard(
                        label = "Physician",
                        labelValue = "${data.message.doctor.salutation} ${data.message.doctor.firstName} ${data.message.doctor.lastName}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    InformationCard(
                        label = "Reason for visit",
                        labelValue = data.message.reason,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Schedule Appointment", color = Color.Black,
                        fontWeight = FontWeight.Bold, fontSize = 20.sp,
                        modifier = Modifier.padding(start = 18.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    ScheduleDate(
                        label = "Date & Time",
                        labelValue = DateUtils.formatAppointmentDate(data.message.appointmentDate),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    ScheduleDate(
                        label = "Next Schedule Date",
                        labelValue = DateUtils.formatAppointmentDate(data.message.nextAppointmentDateTime),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    TabScreen(
                        state = state,
                        viewModel = viewModel,
                        navController = navController,
                        submitionState = submissionState,
                        patientId = patientId,
                        appointmentId = appointmentId,
                        token = token
                    )

                }
            )
        }

        is BasicInformationUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                // Show actual error message
                Text(
                    apiState.message,
                    fontSize = 14.sp,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}


// Image Card
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Int,
    patientId: String,
    appointmentId: String,
    viewModel: BasicInformationViewModel,
    patientName: String,
    context: Context,
    token: String,
    appointmentStatus: String = ""// Add this parameter to receive status from API
) {
    var scope = rememberCoroutineScope()
    // Observe status update state
    var currentStepIndex by rememberSaveable(appointmentStatus) {
        mutableStateOf(
            when (appointmentStatus) {
                "Confirmed" -> 0
                "Waiting" -> 1
                "In Consultation" -> 2
                "Completed" -> 3
                else -> -1 //-1 means nothing is selected yet (All Gray)
            }
        )
    }

    val steps = listOf(
        "Confirmed",
        "Waiting",
        "In Consultation",
        "Completed"
    )

    // State for prescription dialog
    var showPrescriptionDialog by remember { mutableStateOf(false) }
    var prescriptionRecords by remember { mutableStateOf<List<PrescriptionRecord>>(emptyList()) }

    // helper function .
    fun dismissDialog() {
        showPrescriptionDialog = false
        prescriptionRecords = emptyList()
    }
    // Observe medical records state (using your existing state)
    val medicalRecordsState = viewModel._medicalRecordsState.collectAsStateWithLifecycle()
    // know see the state from the viewModel
    val statusUpdateState = viewModel._upateStatusState.collectAsStateWithLifecycle()
    // handle the status update response
    LaunchedEffect(statusUpdateState.value) {
        when (val state = statusUpdateState.value) {
            is UpdateAppointmentStatusUiState.Success -> {
                Toast.makeText(
                    context,
                    state.apiResponse.message,
                    Toast.LENGTH_SHORT
                ).show()
                // important to not show toast again and again
                viewModel.resetUpdateStatusState()
            }

            is UpdateAppointmentStatusUiState.Error -> {

                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.resetUpdateStatusState()
            }

            else -> {}
        }
    }


    // Handle medical records fetch response
    LaunchedEffect(medicalRecordsState.value) {
        when (val state = medicalRecordsState.value) {
            is MedicalRecordUiState.Success -> {
                if (state.data.isNotEmpty()) {
                    prescriptionRecords = state.data
                    showPrescriptionDialog = true
                } else {
                    Toast.makeText(
                        context,
                        "No prescription records found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                viewModel.resetMedicalRecordsState()
            }

            is MedicalRecordUiState.Error -> {
                Toast.makeText(
                    context,
                    "Error fetching prescription: ${state.message}",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.resetMedicalRecordsState()
            }

            else -> {}
        }
    }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // adding image
            Image(
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .size(80.dp)
                    .background(Color(0xffe5e7eb))
                    .clickable {
                        Toast.makeText(context, "Pick image to upload", Toast.LENGTH_SHORT).show()
                    }
            )
            // adding patient name
            Text(
                text = patientName,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                color = Color.Black,
            )
            // Status Stepper
            // show stepper always. even if the user click on completed .
            HorizontalStatusStepper(
                steps = steps,
                currentStepIndex = currentStepIndex,
                onStepClick = { index ->
                    // Update the current step when clicked
                    currentStepIndex = index
                    // map index to api status value
                    val statusValue = when (index) {
                        0 -> "Confirmed"
                        1 -> "Waiting"
                        2 -> "In Consultation"
                        3 -> "Completed"
                        else -> return@HorizontalStatusStepper
                    }
                    Toast.makeText(context, "update Status...", Toast.LENGTH_SHORT).show()

                    // TODO: Here I can also call an API to update the status on the server
                    viewModel.updateAppointmentStatus(
                        token = token,
                        appointmentId = appointmentId,
                        status = statusValue
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (currentStepIndex == 3) {
                // Button to show when all 4 steps are completed successfully
                FilledTonalButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .padding(paddingValues = PaddingValues(0.dp)),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff4f61e3),
                        contentColor = Color.White
                    ),
                    onClick = {
                        scope.launch {
                            // here the actual api will called for get the prescription pdf.
                            // for know show simple toast message when user press the button.
                            viewModel.getMedicalRecordsByAppointment(
                                token = token,
                                appointmentId = appointmentId
                            )
                            Log.d("Appointment ID:", appointmentId)
                        }
                    }
                ) {
                    if (medicalRecordsState.value is MedicalRecordUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Loading...", fontWeight = FontWeight.Bold)
                    } else {
                        Icon(
                            painter = painterResource(android.R.drawable.ic_menu_save),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Get Prescription", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
        if(showPrescriptionDialog && prescriptionRecords.isNotEmpty()){
            PrescriptionPreviewDialog(
                prescriptionRecords.firstOrNull(),
                onDismiss = ::dismissDialog
            )
        }

}


// information card

@Composable
fun InformationCard(
    modifier: Modifier = Modifier,
    label: String,
    labelValue: String?,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier,
        // add card content
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .background(Color.White)
                ) {
                    // add label
                    Text(label, color = Color(0xff858b96))
                    // add label value
                    if (labelValue != null) {
                        Text(labelValue, color = Color.Black)
                    }
                }
            }
        }
    )
}


@Composable
fun ScheduleDate(
    modifier: Modifier = Modifier,
    label: String,
    labelValue: String?,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier,
        // add card content
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .background(Color.White)
                ) {
                    // add label
                    Text(label, color = Color(0xff858b96))
                    // add label value
                    if (labelValue != null) {
                        Text(labelValue, color = Color.Black)
                    }
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.calendar_days),
                        contentDescription = null,
                        modifier = Modifier.padding(10.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicalAssessmentScreen(
    modifier: Modifier = Modifier,
    viewModel: BasicInformationViewModel,
    state: StartConsultingUiState,
    submitionState: SubmitDiagnosisNotesSymptomsUiState,
    patientId: String,
    appointmentId: String,
    token: String
) {

    val apiResponse = viewModel._apiState.collectAsStateWithLifecycle().value
    val physicianId = remember(apiResponse) {
        (apiResponse as? BasicInformationUiState.Success)?.data?.message?.doctor?.id ?: ""
    }
    val context = LocalContext.current

    // launched effect for submittion state
    LaunchedEffect(submitionState) {
        when (submitionState) {
            is SubmitDiagnosisNotesSymptomsUiState.Success -> {
                Toast.makeText(context, "Successfully Submitted", Toast.LENGTH_LONG).show()
                // 1. Clear the inputs
                viewModel.clearClinicalAssessmentFields()

                // 2. Reset the state so the Toast doesn't trigger again
                viewModel.resetSubmissionState()
            }

            is SubmitDiagnosisNotesSymptomsUiState.Error -> {
                Toast.makeText(
                    context,
                    (submitionState as SubmitDiagnosisNotesSymptomsUiState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            SubmitDiagnosisNotesSymptomsUiState.Idle -> {}
            SubmitDiagnosisNotesSymptomsUiState.Loading -> {
                // show loading
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }
    val scope = rememberCoroutineScope()
    // filter list
    val filteredList = viewModel.Symtoms.filter { it ->
        it.contains(state.symptomsSearchQuery, ignoreCase = true)
    }
    val diagnosisFilteredList = viewModel.diagnosis.filter { it ->
        it.contains(state.diagnosisSearchQuery, ignoreCase = true)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        // section 1
        Row(verticalAlignment = Alignment.CenterVertically) {
            // symptom icon
            Icon(
                painter = painterResource(R.drawable.symtoms),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xffa855f7)
            )
            Spacer(Modifier.width(4.dp))
            // name
            Text("Symptom", color = Color.Black, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(4.dp))
        //Search bar
        ExposedDropdownMenuBox(
            expanded = state.symptomsExpandingState && filteredList.isNotEmpty(),
            onExpandedChange = {},
        ) {
            ChipInputTextField(
                value = state.symptomsSearchQuery,
                onValueChange = {
                    viewModel.onSymptomSearchQueryChanged(it)
                    viewModel.onSymptomDropdownToggle(it.isNotEmpty())
                },
                placeHolder = "Start typing symptoms...",
                selectedItem = emptyList(), // not want the chip inside the text field
                onRemoveItem = {}, // no chip to remove here
                modifier = Modifier
                    .menuAnchor()
                    .onFocusChanged { it ->
                        viewModel.onTestDropdownToggle(false)
                    }
            )
            ExposedDropdownMenu(
                expanded = state.symptomsExpandingState,
                onDismissRequest = { viewModel.onSymptomDropdownToggle() },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
                viewModel.Symtoms.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onSymptomSelected(it)
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        //Render the list of selected symptoms BELOW the search bar
        state.selectedSymptoms.forEachIndexed { index, entry ->
            MedicalEntryInputRow(
                entry = entry,
                onDurationChange = { viewModel.onSymptomDetailUpdate(index, duration = it) },
                onSeverityChange = { viewModel.onSymptomDetailUpdate(index, severity = it) },
                onRemove = { viewModel.onSymptomRemove(entry) }
            )
        }
        Spacer(Modifier.height(16.dp))
        // section 2
        Row(verticalAlignment = Alignment.CenterVertically) {
            // symptom icon
            Icon(
                painter = painterResource(R.drawable.diagnosis),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Red
            )
            Spacer(Modifier.width(4.dp))
            // name
            Text("Diagnosis", color = Color.Black, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(4.dp))
        ExposedDropdownMenuBox(
            expanded = state.diagnosisExpandingState && diagnosisFilteredList.isNotEmpty(),
            onExpandedChange = { viewModel.onDiagnosisDropdownToggle() },
        ) {
            ChipInputTextField(
                value = state.diagnosisSearchQuery,
                onValueChange = {
                    viewModel.onDiagnosisSearchQueryChanged(it)
                    viewModel.onDiagnosisDropdownToggle(it.isNotEmpty())
                },
                placeHolder = "Start typing Diagnosis...",
                selectedItem = emptyList(),
                onRemoveItem = {},
                modifier = Modifier
                    .menuAnchor()
                    .onFocusChanged { it ->
                        viewModel.onDiagnosisDropdownToggle(false)
                    }
            )
            ExposedDropdownMenu(
                expanded = state.diagnosisExpandingState && diagnosisFilteredList.isNotEmpty(),
                onDismissRequest = { viewModel.onDiagnosisDropdownToggle() },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                diagnosisFilteredList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onDiagnosisSelected(it)
                        }
                    )
                }
            }
        }
        //  Render the list of selected diagnosis BELOW the search bar
        Spacer(Modifier.height(8.dp))
        state.selectedDiagnosis.forEachIndexed { index, entry ->
            MedicalEntryInputRow(
                entry = entry,
                onDurationChange = { viewModel.onDiagnosisDetailsUpdated(index, duration = it) },
                onSeverityChange = { viewModel.onDiagnosisDetailsUpdated(index, severity = it) },
                onRemove = { viewModel.onDiagnosisRemove(entry) }
            )
        }
        Spacer(Modifier.height(16.dp))
        // section 3
        Row(verticalAlignment = Alignment.CenterVertically) {

            Spacer(Modifier.width(4.dp))
            // name
            Text("Notes", color = Color.Black, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(4.dp))
        TextField(
            value = state.notes,
            onValueChange = { viewModel.onNotes(it) },
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xfff9fafb))
                .fillMaxWidth(),
            placeholder = { Text("Notes", color = Color(0xffa3aab5)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xfff9fafb),
                unfocusedContainerColor = Color(0xfff9fafb),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(Modifier.height(8.dp))
        FilledTonalButton(
            onClick = {
                // here you need to call submit api.
                scope.launch {
                    viewModel.onSubmitClicked(
                        patientId = patientId,
                        appointmentId = appointmentId,
                        physicianId = physicianId, // here i have to pass the physician id
                        token = token
                    )
                }
            },
            shape = RoundedCornerShape(4.dp),
            enabled = submitionState !is SubmitDiagnosisNotesSymptomsUiState.Loading,
            modifier = Modifier
                .padding(paddingValues = PaddingValues(0.dp))
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            if (submitionState is SubmitDiagnosisNotesSymptomsUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Blue,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Submit", color = Color.White)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VitalSignAndSymtoms(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: BasicInformationViewModel,
    patientId: String,
    appointmentId: String,
    physicianId: String,
    token: String,
) {
    val listState = viewModel._listOfVitalSignAndSymptoms.collectAsStateWithLifecycle().value


    LaunchedEffect(patientId) {
        if (patientId.isNotEmpty()) {
            viewModel.getListOfSymptomsAndVitalSigns(token = token, patientId = patientId)
        }
    }

    when (val state = listState) {
        is VitalSignAndSymptomsList.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is VitalSignAndSymptomsList.Success -> {
            if (state.data.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No records found for this patient.",
                    )
                }
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(state.data) { item ->
                        VitalSignSymptomResponseCard(
                            patient = item,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }

        is VitalSignAndSymptomsList.Error -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Error: ${listState.message}",
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    viewModel.getListOfSymptomsAndVitalSigns(token, patientId)
                }) {
                    Text("Retry")
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Medication(
    modifier: Modifier = Modifier,
    state: StartConsultingUiState,
    viewModel: BasicInformationViewModel,
    token: String,
    patientId: String,
    appointmentId: String,
    physicianId: String
) {

    val ViewModelState = viewModel._sendMedication.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val filteredList = viewModel.medication.filter { it ->
        it.contains(state.medicationSearchQuery, ignoreCase = true)
    }
    //handle medication submission state
    LaunchedEffect(ViewModelState.value) {
        when (val castState = ViewModelState.value) {
            is MedicationUiState.Success -> {
                Toast.makeText(
                    context,
                    castState.data.message,
                    Toast.LENGTH_SHORT
                ).show()

                // clear the text field after successfully submission
                viewModel.clearMedicationFields()
                // reset state
                viewModel.resetSendMedicationState()
            }

            is MedicationUiState.Error -> {
                Toast.makeText(
                    context,
                    castState.message,
                    Toast.LENGTH_LONG
                ).show()

                viewModel.resetSendMedicationState()
            }

            else -> {
                // left case of loading and idle
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // symptom icon
            Icon(
                painter = painterResource(R.drawable.first_aid_kit),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xff9250cd)
            )
            Spacer(Modifier.width(4.dp))
            // name
            Text("Medication", color = Color.Black, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(4.dp))

        ExposedDropdownMenuBox(
            expanded = state.medicationExpandingState && filteredList.isNotEmpty(),
            onExpandedChange = { viewModel.onMedicationDropdownToggle() },
        ) {
            ChipInputTextField(
                value = state.medicationSearchQuery,
                onValueChange = {
                    viewModel.onMedicationSearchQueryChanged(it)
                    viewModel.onMedicationDropdownToggle(it.isNotEmpty())
                },
                placeHolder = "Start typing medication...",
                selectedItem = emptyList(),
                onRemoveItem = {},
                modifier = Modifier
                    .menuAnchor()
                    .onFocusChanged { it ->
                        viewModel.onMedicationDropdownToggle(isExpanded = false)
                    }
            )
            ExposedDropdownMenu(
                expanded = state.medicationExpandingState,
                onDismissRequest = { viewModel.onMedicationDropdownToggle() },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
                filteredList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onMedicationSelected(it)
                        }
                    )
                }
            }
        }
        // here i render the list of selected medication
        Spacer(Modifier.height(8.dp))
        state.selectedMedication.forEachIndexed { index, entry ->
            MedicationInputRow(
                entry = entry,
                onDurationChange = { viewModel.onMedicationUpdated(index, duration = it) },
                onStorageChange = { viewModel.onMedicationUpdated(index, storage = it) },
                onRemove = { viewModel.onMedicationRemoved(entry) }
            )
        }
        FilledTonalButton(
            onClick = {
                viewModel.onSendMedicationClicked(
                    token = token,
                    patientId = patientId,
                    appointmentId = appointmentId,
                    physicianId = physicianId
                )
            },
            enabled = ViewModelState.value !is MedicationUiState.Loading &&
                    state.selectedMedication.isNotEmpty(),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(paddingValues = PaddingValues(0.dp))
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            if (ViewModelState.value is MedicationUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Spacer(Modifier.width(8.dp))
            }
            Text("Submit", color = Color.White)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestInvestigation(
    modifier: Modifier = Modifier,
    state: StartConsultingUiState,
    viewModel: BasicInformationViewModel,
    token: String,
    appointmentId: String,
    physicianId: String,
    patientId: String
) {
    val context = LocalContext.current
    // get viewModel Statelllakite
    val testState = viewModel._testAndInvestigation.collectAsStateWithLifecycle().value
    // filter the list of test based on the search query from viewModel
    val filteredList = viewModel.testInvestigation.filter { it ->
        // logic for filtering the list
        it.contains(state.testSearchQuery, ignoreCase = true)
    }

    LaunchedEffect(testState) {
        when (val testState = testState) {
            is TestAndInvestigation.Success -> {
                Toast.makeText(
                    context,
                    "Test & Investigation submitted successfully!",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.clearTestInvestigationFields()
                viewModel.resetTestInvestigationState()
            }

            is TestAndInvestigation.Error -> {
                Toast.makeText(
                    context,
                    "Error: ${testState.message}",
                    Toast.LENGTH_LONG
                ).show()
                viewModel.resetTestInvestigationState()
            }

            else -> {
                // loading and idle case
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // symptom icon
            Icon(
                painter = painterResource(R.drawable.test_tube),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color(0xff9250cd)
            )
            Spacer(Modifier.width(4.dp))
            // name
            Text("Test/Investigation", color = Color.Black, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(4.dp))
        ExposedDropdownMenuBox(
            expanded = state.testInvestigationExpandingState && filteredList.isNotEmpty(),
            onExpandedChange = {},
        ) {
            ChipInputTextField(
                value = state.testSearchQuery,
                onValueChange = {
                    viewModel.onTestSearchQueryChanged(it)
                    viewModel.onTestDropdownToggle(it.isNotEmpty())
                },
                placeHolder = "Search for Medication...",
                selectedItem = emptyList(),
                onRemoveItem = {},
                modifier = Modifier
                    .menuAnchor()
                    .onFocusChanged { it ->
                        viewModel.onTestDropdownToggle(false)
                    }
            )
            ExposedDropdownMenu(
                expanded = state.testInvestigationExpandingState && filteredList.isNotEmpty(),
                onDismissRequest = { viewModel.onTestDropdownToggle(false) },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                // iterate over the Filtered list , not the whole list
                filteredList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.ontestInvestigationSelected(it)
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        // render the tests
        state.selectedTest.forEachIndexed { index, entry ->
            TestInputRow(
                entry = entry,
                onNotesChange = { viewModel.onTestInvestigationUpdated(index, reason = it) },
                onRemove = { viewModel.onTestInvestigationRemoved(entry) }
            )
        }
        Spacer(Modifier.height(8.dp))
        FilledTonalButton(
            onClick = {
                // here i call the logic to submit the test
                if (state.selectedTest.isNotEmpty()) {
                    viewModel.sendTestAndInvestigationRepo(
                        token = token,
                        physicianId = physicianId,
                        appointmentId = appointmentId,
                        patientId = patientId
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Please add at least one test",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            enabled = testState !is TestAndInvestigation.Loading,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(paddingValues = PaddingValues(0.dp))
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            if (testState is TestAndInvestigation.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Blue,
                    strokeWidth = 2.dp
                )
                Spacer(Modifier.width(8.dp))
            }
            Text("Submit", color = Color.White)
        }
    }
}


// Tab screen 
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TabScreen(
    modifier: Modifier = Modifier,
    state: StartConsultingUiState,
    viewModel: BasicInformationViewModel,
    navController: NavController,
    submitionState: SubmitDiagnosisNotesSymptomsUiState,
    patientId: String,
    appointmentId: String,
    token: String,
) {
    val apiResponse = viewModel._apiState.collectAsStateWithLifecycle().value
    val physicianId = remember(apiResponse) {
        (apiResponse as? BasicInformationUiState.Success)?.data?.message?.doctor?.id ?: ""
    }
    var tabIndex by remember { mutableIntStateOf(0) }
    var showFullScreen by remember { mutableStateOf(false) }

    val tabs = listOf(
        "Clinical Assessment",
        "Vital Sign & Symptoms",
        "Medication",
        "Test/Investigation"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent,
            modifier = Modifier.background(brush = gradient_colors),
            indicator = {}
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            title,
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                        showFullScreen = true  // Open full screen when tab clicked
                    }
                )
            }
        }
    }

    // Full-screen dialog
    if (showFullScreen) {
        Dialog(
            onDismissRequest = { showFullScreen = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Top bar with close button
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xfff0f5ff))
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tabs[tabIndex],
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Spacer(Modifier.weight(1f))
                        //  i only want to show this icon to the vital sign and symptom
                        if (tabIndex == 1) {
                            IconButton(onClick = {
                                navController.navigate("addSymptoms/$token/$patientId/$appointmentId")
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.add_file),
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = modifier.padding(8.dp)
                                )
                            }
                        }
                        IconButton(onClick = { showFullScreen = false }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Close",
                                tint = Color.Black
                            )
                        }
                    }

                    // Content
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (tabIndex) {
                            0 -> ClinicalAssessmentScreen(
                                state = state,
                                viewModel = viewModel,
                                submitionState = submitionState,
                                patientId = patientId,
                                appointmentId = appointmentId,
                                token = token
                            )

                            1 -> VitalSignAndSymtoms(
                                navController = navController,
                                patientId = patientId,
                                appointmentId = appointmentId,
                                token = token,
                                physicianId = physicianId,
                                viewModel = viewModel
                            )

                            2 -> Medication(
                                state = state, viewModel = viewModel,
                                token = token,
                                patientId = patientId,
                                appointmentId = appointmentId,
                                physicianId = physicianId
                            )

                            3 -> TestInvestigation(
                                state = state,
                                viewModel = viewModel,
                                token = token,
                                patientId = patientId,
                                appointmentId = appointmentId,
                                physicianId = physicianId
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
@OptIn(ExperimentalLayoutApi::class)
fun ChipInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    selectedItem: List<String>,
    onRemoveItem: (String) -> Unit
) {
    // We build the text field from scratch using a Surface to draw the border and background.
    // This avoids the layout issues of putting a FlowRow inside an OutlinedTextField.
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium, // Or RoundedCornerShape(4.dp)
        color = Color(0xfff9fafb),
        border = BorderStroke(1.dp, Color.Gray.copy(alpha = 0.5f))
    ) {
        FlowRow(
            // Use standard padding to mimic a text field's internal spacing.
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 1. Display a chip for each selected item
            selectedItem.forEach { itemText ->
                key(itemText) {
                    InputChip(
                        selected = false,
                        onClick = {},
                        label = { Text(itemText, fontSize = 14.sp, color = Color.Black) },
                        colors = InputChipDefaults.inputChipColors(containerColor = Color(0xfff0f5ff)),
                        shape = MaterialTheme.shapes.small,
                        border = null,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Remove $itemText",
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { onRemoveItem(itemText) },
                                tint = Color.Black
                            )
                        },
                    )
                }
            }

            // 2. The actual text input field
            Box(
                modifier = Modifier
                    .weight(1f) // Takes remaining space on the current line
                    .padding(vertical = 2.dp) // Ensures cursor has some vertical space
                    .align(Alignment.CenterVertically)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(LocalContentColor.current),
                    decorationBox = { innerTextField ->
                        // This is where the placeholder is shown
                        if (value.isEmpty() && selectedItem.isEmpty()) {
                            Text(
                                text = placeHolder,
                                color = Color(0xffa3aab5)
                            )
                        }
                        innerTextField() // This is the actual text input area
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSymptomScreen(
    modifier: Modifier = Modifier,
    viewModel: BasicInformationViewModel,
    navController: NavController,
    patinetId: String,
    appointmentId: String,
    token: String,
) {

    val state = viewModel._state.collectAsStateWithLifecycle().value
    val apiState = viewModel._apiState.collectAsStateWithLifecycle().value
    val data = apiState as BasicInformationUiState.Success
    val physicianId = data.data.message.doctor.id
    val vitalSignAndSymptom = viewModel._saveVitalSignState.collectAsStateWithLifecycle().value
    val context = LocalContext.current


    // add launched effect to observe the save state
    LaunchedEffect(vitalSignAndSymptom) {
        when (vitalSignAndSymptom) {
            is SaveVitalSignsUiState.Success -> {
                Toast.makeText(context, "Successfully Submitted", Toast.LENGTH_LONG).show()
                // after that i going to clear all the fields
                viewModel.clearVitalSignField()

                // Reset the API state so we can save again if needed without re-triggering this block immediately
                viewModel.resetSaveVitalSignState()

                // IMPORTANT: Navigate back to list screen
                delay(300) // Small delay so toast is visible
                navController.popBackStack()
            }

            is SaveVitalSignsUiState.Error -> {
                Toast.makeText(context, vitalSignAndSymptom.message, Toast.LENGTH_SHORT).show()
                viewModel.resetSaveVitalSignState()
            }

            else -> {
                // leave the loading and idle for know
            }
        }
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = Color(0xfff0f5ff),
        topBar = {
            TopAppBar(
                title = { Text("Add Vital Signs") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                modifier = modifier
                    .background(brush = gradient_colors),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .padding(18.dp)
                .background(color = Color(0xfff0f5ff))
                .verticalScroll(rememberScrollState())
        ) {
            Text("Blood Pressure")
            SymptomsWritingTextField(
                modifier = modifier,
                placeHolder = "eg. 120/80",
                state = state.bloodPressure,
                onValueChange = {
                    viewModel.onBloodPressureAdded(it)
                }
            )

            Spacer(Modifier.height(12.dp))

            Text("Heart Rate")
            SymptomsWritingTextField(
                modifier = modifier,
                placeHolder = "eg. 75",
                state = state.heartRate,
                onValueChange = {
                    viewModel.onHeartRateAdded(it)
                }
            )

            Spacer(Modifier.height(12.dp))

            Text("Temperature (°F)")
            SymptomsWritingTextField(
                modifier = modifier,
                placeHolder = "eg. 98.6",
                state = state.temperature,
                onValueChange = {
                    viewModel.onTempratureAdded(it)
                }
            )

            Spacer(Modifier.height(12.dp))

            Text("Oxygen Saturation (%)")
            SymptomsWritingTextField(
                modifier = modifier,
                placeHolder = "eg. 98%",
                state = state.oxygenSaturation,
                onValueChange = {
                    viewModel.onOxygenSaturationAdded(it)
                }
            )

            Spacer(Modifier.height(12.dp))

            Text("Height (cm)")
            SymptomsWritingTextField(
                modifier = modifier,
                placeHolder = "eg. 170",
                state = state.height,
                onValueChange = {
                    viewModel.onHeightAdded(it)
                }
            )

            Spacer(Modifier.height(12.dp))


            Text("Weight (kg)")
            SymptomsWritingTextField(
                modifier = modifier,
                placeHolder = "eg. 70",
                state = state.weight,
                onValueChange = {
                    viewModel.onWeightAdded(it)
                }
            )

            Spacer(Modifier.height(20.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                FilledTonalButton(
                    onClick = { navController.popBackStack() },
                    modifier = modifier.padding(paddingValues = PaddingValues(0.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffcbd5e1)
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Cancel", fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(Modifier.width(6.dp))
                FilledTonalButton(
                    onClick = {
                        // send data to server through the api call
                        viewModel.onSaveClicked(
                            token = token,
                            physicianId = physicianId,
                            appointmentId = appointmentId,
                            patientId = patinetId
                        )
                    },

                    enabled = state.bloodPressure.isNotEmpty()
                            && state.heartRate.isNotEmpty()
                            && state.temperature.isNotEmpty()
                            && state.oxygenSaturation.isNotEmpty()
                            && state.height.isNotEmpty()
                            && state.weight.isNotEmpty()
                            && vitalSignAndSymptom !is SaveVitalSignsUiState.Loading, // Check loading here

                    modifier = modifier.padding(paddingValues = PaddingValues(0.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff1d4ed8)
                    ),
                    shape = RoundedCornerShape(4.dp)


                ) {
                    // show circular progress indicator if loading else show text
                    if (vitalSignAndSymptom is SaveVitalSignsUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.Blue,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Save", fontWeight = FontWeight.Bold, color = Color.White)
                    }
                }
            }

        }

    }

}

@Composable
fun SymptomsWritingTextField(
    modifier: Modifier = Modifier,
    placeHolder: String,
    state: String,
    // make onValueChange as event so that SymptomWritingTextFiled as reusable function
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = state,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        placeholder = { Text(placeHolder, color = Color.Gray) },
        colors = TextFieldDefaults.colors(
            cursorColor = Color.Black,
            focusedContainerColor = Color(0xfff6f6f6),
            unfocusedContainerColor = Color(0xfff6f6f6),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            errorIndicatorColor = Color.Red
        )
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalEntryInputRow(
    entry: MedicalEntry,
    onDurationChange: (String) -> Unit,
    onSeverityChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val severityOptions = listOf("Mild", "Moderate", "Severe")

    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header: Name and Remove Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onRemove() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Inputs: Duration and Severity
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Duration TextField
                OutlinedTextField(
                    value = entry.duration,
                    onValueChange = onDurationChange,
                    label = { Text("Duration", fontSize = 12.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xff1d4ed8),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    )
                )

                // Severity Dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = entry.severity,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Severity", fontSize = 12.sp) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color(0xff1d4ed8),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        severityOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option, color = Color.Black) },
                                onClick = {
                                    onSeverityChange(option)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationInputRow(
    entry: MedicationEntry,
    onDurationChange: (String) -> Unit,
    onStorageChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Name and Remove
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    entry.medicationName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onRemove() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // All three fields as simple TextFields in one Row
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Duration - Simple TextField
                OutlinedTextField(
                    value = entry.duration,
                    onValueChange = onDurationChange,
                    label = { Text("Duration", fontSize = 10.sp) },
                    placeholder = { Text("5 days", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xff1d4ed8)
                    ),
                    textStyle = TextStyle(
                        color = Color.Black
                    )
                )
                // storage - Simple TextField
                OutlinedTextField(
                    value = entry.storage,
                    onValueChange = onStorageChange,
                    label = { Text("Frequency", fontSize = 10.sp) },
                    placeholder = { Text("10mg", fontSize = 10.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xff1d4ed8)
                    ),
                    textStyle = TextStyle(
                        color = Color.Black
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestInputRow(
    entry: InvestigationEntry,
    onNotesChange: (String) -> Unit,
    onRemove: () -> Unit
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.Center) {
            // Header: Name and Remove
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.testInvestigationName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Remove",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onRemove() }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {  // Note Input Field
                OutlinedTextField(
                    value = entry.testInvestigationReason,
                    onValueChange = onNotesChange,
                    placeholder = { Text("Reason", fontSize = 12.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xff1d4ed8),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    )
                )
                OutlinedTextField(
                    value = "mg/dL",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xff1d4ed8),
                        unfocusedTextColor = Color.Black,
                        focusedTextColor = Color.Black
                    )
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VitalSignSymptomResponseCard(
    modifier: Modifier = Modifier,
    patient: ParticularPatient,
    viewModel: BasicInformationViewModel
) {
    // take recorded as unique key
    val key = patient.recordedAt ?: "unknown_${patient.hashCode()}"
    val state = viewModel._state.collectAsStateWithLifecycle().value
    val isExpanded = state.VitalSignSymptomsCardExpandState[key] ?: false
    val vitalSignAndSymptom = viewModel._saveVitalSignState.collectAsStateWithLifecycle().value

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xfff0f5ff)
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    DateUtils.formatAppointmentDate("${patient.recordedAt}") ?: "NA",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    viewModel.onVitalSignSymptomResponseCardClicked(
                        key,
                        isExpanded = isExpanded
                    )
                }
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessMedium
                )

            ),
        ) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xfff0f5ff)
                )
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // row 1
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Blood Pressure",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                patient.bloodPressure ?: "NA",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                        Spacer(Modifier.weight(1f))
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "Heart Rate",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(patient.heartRate ?: "NA", fontSize = 14.sp, color = Color.Black)
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    // row 2
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Temperature",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(patient.temperature ?: "NA", fontSize = 14.sp, color = Color.Black)
                        }
                        Spacer(Modifier.weight(1f))
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "Oxygen Saturation",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                patient.oxygenSaturation ?: "NA",
                                fontSize = 14.sp,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    // row 3
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Height",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(patient.height ?: "NA", fontSize = 14.sp)
                        }
                        Spacer(Modifier.weight(1f))
                        Column(
                            modifier = modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                "Weight",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(patient.weight ?: "NA", fontSize = 14.sp, color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}


data class StepItem(
    val label: String,
    val status: StepStatus
)

enum class StepStatus {
    COMPLETED,
    WAITING,
    IN_CONSULTATION,
    PENDING
}

@Composable
fun ConsultationPulseIndicator(
    color: Color = Color(0xFF2196F3),
    size: Dp = 32.dp,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(990, easing = FastOutLinearInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Outer pulsing circle
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = color.copy(alpha = alpha),
                radius = this.size.minDimension / 2 * scale
            )
        }

        // Inner static circle
        Box(
            modifier = Modifier
                .size(size * 0.6f)
                .background(color, CircleShape)
        )
    }
}

@Composable
fun HorizontalStatusStepper(
    steps: List<String>,
    currentStepIndex: Int,
    onStepClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val circleSize = 30.dp
    val lineThickness = 4.dp
    val circleCenterOffset = circleSize / 2 // 17.5.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // --- LAYER 1: CONNECTING LINES ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(circleSize)
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    // Logic: Line is green if the NEXT step is reached or completed
                    val isLineActive = index < currentStepIndex
                    if (index < steps.lastIndex) {
                        // Draw line from the center of THIS step to the center of the NEXT step
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(lineThickness)
                                // Offset the line so it starts at the center of the circle
                                .graphicsLayer { translationX = size.width / 2 }
                        ) {
                            drawLine(
                                color = if (index < currentStepIndex) Color(0xFF4CAF50) else Color(
                                    0xFFE0E0E0
                                ),
                                start = Offset(0f, size.height / 2),
                                end = Offset(size.width, size.height / 2),
                                strokeWidth = lineThickness.toPx(),
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }
        }

        // --- LAYER 2: CIRCLES AND LABELS ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { index, label ->
                // 1. A step is "Active/Pulsing" ONLY if it is current AND NOT the final "Completed" step
                val isActivePulse = index == currentStepIndex && index != steps.lastIndex

                // 2. A step is "Done/Tick" if it is previous OR if it is the current one AND it's the final step
                val isDone =
                    index < currentStepIndex || (index == currentStepIndex && index == steps.lastIndex)


                val isCompleted = index < currentStepIndex
                val isCurrent = index == currentStepIndex
                val isPending = index > currentStepIndex

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    // Circle Container
                    Box(
                        modifier = Modifier
                            .size(circleSize)
                            .then(
                                if (isActivePulse) Modifier.border(
                                    width = 1.dp,
                                    color = Color(0xff28a745),
                                    shape = CircleShape
                                ) else Modifier
                            )
                            .clickable { onStepClick(index) }
                            .background(
                                color = when {
                                    isDone -> Color(0xff28a745) // Green Fill
                                    isActivePulse -> Color.Transparent // Transparent for Pulse
                                    else -> Color.LightGray
                                },
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            isDone -> {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Completed",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            isActivePulse -> {
                                // Only show pulse for steps 0, 1, 2
                                ConsultationPulseIndicator(
                                    color = Color(0xff28a745),
                                    size = circleSize
                                )
                            }

                            isPending -> {
                                // future steps
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Label
                    Text(
                        text = label,
                        // If it's done or active, make it black/bold. Else gray.
                        color = if (isDone || isActivePulse) Color.Black else Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = if (isDone || isActivePulse) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        lineHeight = 12.sp
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrescriptionPreviewDialog(
    prescriptionRecord: PrescriptionRecord?,
    onDismiss: () -> Unit,
    context: Context = LocalContext.current
) {
    var pdfFile by remember { mutableStateOf<File?>(null) }
    var isGenerating by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // Auto-generate PDF when dialog opens
    LaunchedEffect(prescriptionRecord) {
        if (prescriptionRecord != null && pdfFile == null) {
            isGenerating = true
            scope.launch(Dispatchers.IO) {
                try {
                    val generator = PrescriptionPdfGenerator(context)
                    val file = generator.generatePdf(prescriptionRecord)
                    withContext(Dispatchers.Main) {
                        pdfFile = file
                        isGenerating = false
                        if (file != null) {
                            Toast.makeText(
                                context,
                                "Prescription PDF generated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Failed to generate PDF",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        isGenerating = false
                        Toast.makeText(
                            context,
                            "Error: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xfff0f5ff)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xff1d4ed8))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Prescription Preview",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = onDismiss,
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Close"
                        )
                    }
                }

                // Content Area
                if (isGenerating) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(48.dp),
                                color = Color(0xff1d4ed8),
                                strokeWidth = 4.dp
                            )
                            Text(
                                text = "Generating prescription PDF...",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                } else if (pdfFile != null) {
                    // PDF Info Display
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Success Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xffdcfce7)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    painter = painterResource(android.R.drawable.ic_menu_save),
                                    contentDescription = null,
                                    tint = Color(0xff16a34a),
                                    modifier = Modifier.size(32.dp)
                                )
                                Column {
                                    Text(
                                        text = "PDF Generated Successfully!",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color(0xff16a34a)
                                    )
                                    Text(
                                        text = "Your prescription is ready",
                                        fontSize = 14.sp,
                                        color = Color(0xff166534)
                                    )
                                }
                            }
                        }

                        // Prescription Details Card
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = "Prescription Details",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = Color.Black
                                )
                                HorizontalDivider(color = Color.LightGray)

                                PrescriptionDetailRow(
                                    label = "Patient Name",
                                    value = "${prescriptionRecord?.patient?.firstName ?: ""} ${prescriptionRecord?.patient?.lastName ?: ""}"
                                )
                                PrescriptionDetailRow(
                                    label = "Patient ID",
                                    value = prescriptionRecord?.patient?.id ?: "N/A"
                                )
                                PrescriptionDetailRow(
                                    label = "Physician",
                                    value = "Dr. ${prescriptionRecord?.physician?.firstName ?: ""} ${prescriptionRecord?.physician?.lastName ?: ""}"
                                )
                                PrescriptionDetailRow(
                                    label = "Date",
                                    value = prescriptionRecord?.encounterDate ?: "N/A"
                                )
                                PrescriptionDetailRow(
                                    label = "Status",
                                    value = prescriptionRecord?.status ?: "N/A"
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                // Medications Count
                                Text(
                                    text = "Medications: ${prescriptionRecord?.medicationOrders?.size ?: 0}",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )

                                // File Path
                                Text(
                                    text = "Saved to: ${pdfFile?.name}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Failed to generate PDF",
                            fontSize = 16.sp,
                            color = Color.Red
                        )
                    }
                }

                // Action Buttons (Always visible at bottom)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Preview PDF Button
                        Button(
                            onClick = {
                                pdfFile?.let { file ->
                                    try {
                                        val uri = FileProvider.getUriForFile(
                                            context,
                                            "${context.packageName}.provider",
                                            file
                                        )
                                        val intent = Intent(Intent.ACTION_VIEW).apply {
                                            setDataAndType(uri, "application/pdf")
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        }
                                        context.startActivity(intent)
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "Error opening PDF: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            },
                            enabled = pdfFile != null && !isGenerating,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff1d4ed8)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_view),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Preview PDF",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Share PDF Button
                        OutlinedButton(
                            onClick = {
                                pdfFile?.let { file ->
                                    try {
                                        val uri = FileProvider.getUriForFile(
                                            context,
                                            "${context.packageName}.provider",
                                            file
                                        )
                                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                            type = "application/pdf"
                                            putExtra(Intent.EXTRA_STREAM, uri)
                                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                        }
                                        context.startActivity(
                                            Intent.createChooser(shareIntent, "Share Prescription")
                                        )
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "Error sharing PDF: ${e.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            },
                            enabled = pdfFile != null && !isGenerating,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xff1d4ed8)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            Icon(
                               imageVector = Icons.Default.Share,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Share PDF",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Download Again Button
                        TextButton(
                            onClick = {
                                isGenerating = true
                                scope.launch(Dispatchers.IO) {
                                    try {
                                        if (prescriptionRecord != null) {
                                            val generator = PrescriptionPdfGenerator(context)
                                            val file = generator.generatePdf(prescriptionRecord)
                                            withContext(Dispatchers.Main) {
                                                pdfFile = file
                                                isGenerating = false
                                                Toast.makeText(
                                                    context,
                                                    "PDF regenerated successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            isGenerating = false
                                            Toast.makeText(
                                                context,
                                                "Error: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            },
                            enabled = !isGenerating,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Download Again",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PrescriptionDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Normal
        )
    }
}