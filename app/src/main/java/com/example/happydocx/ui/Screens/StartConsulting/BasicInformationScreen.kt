package com.example.happydocx.ui.Screens.StartConsulting

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.ViewModels.StartConsulting.BasicInformationViewModel
import com.example.happydocx.ui.uiStates.StartConsulting.StartConsultingUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultingMainScreen(
    navController: NavController,
    viewModel: BasicInformationViewModel
) {
    val context = LocalContext.current
    val state = viewModel._state.collectAsStateWithLifecycle().value
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Patient Detail") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                modifier = Modifier.background(brush = gradient_colors),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(color = Color(0xfff0f5ff)),
            content = {
                ImageCard(
                    image = R.drawable.patientimage,
                    patientName = "Deepak Guleria",
                    patientId = "7985678430",
                    context = context
                )
                // information cards
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    InformationCard(
                        label = "Date of Birth",
                        labelValue = "09-06-1992",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.weight(0.2f))

                    InformationCard(
                        label = "Age",
                        labelValue = "33 years",
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
                        labelValue = "Male",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(Modifier.weight(0.2f))
                    InformationCard(
                        label = "Department",
                        labelValue = "Psychiatry",
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(Modifier.height(4.dp))
                InformationCard(
                    label = "Physician",
                    labelValue = "Dr Brian L. Kamau",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(4.dp))
                InformationCard(
                    label = "Reason for visit",
                    labelValue = "Cold",
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
                    labelValue = "22-11-2025 08:22 AM",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(4.dp))
                ScheduleDate(
                    label = "Next Schedule Date",
                    labelValue = "dd-MM-yyyy",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
                TabScreen(state = state, viewModel = viewModel)
            }
        )
    }
}


// Image Card
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: Int,
    patientName: String,
    patientId: String,
    context: Context
) {
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
            // adding patient Id
            Text(
                text = "Patient Id: $patientId",
                fontSize = 14.sp,
                color = Color(0xff7a808d)
            )
        }
    }
}

// information card

@Composable
fun InformationCard(
    modifier: Modifier = Modifier,
    label: String,
    labelValue: String,
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
                    Text(labelValue, color = Color.Black)
                }
            }
        }
    )
}


@Composable
fun ScheduleDate(
    modifier: Modifier = Modifier,
    label: String,
    labelValue: String,
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
                    Text(labelValue, color = Color.Black)
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
    state: StartConsultingUiState
) {
    // filter list
    val filteredList = viewModel.Symtoms.filter { it ->
        it.contains(state.symptomsSearchQuery, ignoreCase = true)
    }
    val diagnosisFilteredList = viewModel.diagnosis.filter { it ->
        it.contains(state.symptomsSearchQuery, ignoreCase = true)
    }
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {

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
                selectedItem = state.selectedSymptoms,
                onRemoveItem = { it ->
                    viewModel.onSymptomRemove(it)
                },
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
        Spacer(Modifier.height(4.dp))

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
                selectedItem = state.selectedDiagnosis,
                onRemoveItem = { it ->
                    viewModel.onDiagnosisRemove(it)
                },
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
        // section 3
        Spacer(Modifier.height(4.dp))
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
            onClick = {},
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(paddingValues = PaddingValues(0.dp))
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            Text("Submit", color = Color.White)
        }
    }
}

@Composable
fun VitalSignAndSymtoms(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Text("No vital sign recorded")
        Spacer(Modifier.height(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            FilledTonalButton(
                onClick = {},
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(paddingValues = PaddingValues(0.dp))
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
            ) {
                Text("Submit", color = Color.White)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Medication(
    modifier: Modifier = Modifier,
    state: StartConsultingUiState,
    viewModel: BasicInformationViewModel
) {
    val filteredList = viewModel.medication.filter { it ->
        it.contains(state.medicationSearchQuery, ignoreCase = true)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
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
                selectedItem = state.selectedMedication,
                onRemoveItem = { it ->
                    viewModel.onMedicationRemoved(it)
                },
                modifier = Modifier
                    .menuAnchor()
                    .onFocusChanged { it ->
                        viewModel.onMedicationDropdownToggle(false)
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

        Spacer(Modifier.height(8.dp))
        FilledTonalButton(
            onClick = {},
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(paddingValues = PaddingValues(0.dp))
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            Text("Submit", color = Color.White)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestInvestigation(
    modifier: Modifier = Modifier,
    state: StartConsultingUiState,
    viewModel: BasicInformationViewModel
) {

    // filter the list of test based on the search query from viewModel
    val filteredList = viewModel.testInvestigation.filter { it ->
        // logic for filtering the list
        it.contains(state.testSearchQuery, ignoreCase = true)
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
                selectedItem = state.selectedTest,
                onRemoveItem = { test ->
                    viewModel.onTestInvestigationRemoved(test = test)
                },
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
        FilledTonalButton(
            onClick = {},
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .padding(paddingValues = PaddingValues(0.dp))
                .align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            Text("Submit", color = Color.White)
        }
    }
}


// Tab screen 
@Composable
fun TabScreen(
    modifier: Modifier = Modifier,
    state: StartConsultingUiState,
    viewModel: BasicInformationViewModel
) {
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
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tabs[tabIndex],
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
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
                            0 -> ClinicalAssessmentScreen(state = state, viewModel = viewModel)
                            1 -> VitalSignAndSymtoms()
                            2 -> Medication(state = state, viewModel = viewModel)
                            3 -> TestInvestigation(state = state, viewModel = viewModel)
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





