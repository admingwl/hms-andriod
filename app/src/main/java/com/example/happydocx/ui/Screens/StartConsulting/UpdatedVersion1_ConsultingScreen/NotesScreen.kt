package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.UploadNotesUiState
import com.example.happydocx.ui.uiStates.StartConsulting.ConsultationNotesUpdate1
import com.example.happydocx.ui.uiStates.StartConsulting.MedicationItem
import kotlinx.coroutines.launch

@Suppress("ViewModelForwarding")
@Composable
fun NotesScreen(
    modifier: Modifier = Modifier,
    startConsultingViewModel: StartConsultingViewModel,
    token:String,
    appointmentId:String
) {
    val notesUi =
        startConsultingViewModel._consultationNotesState.collectAsStateWithLifecycle().value
    val uploadNoteState = startConsultingViewModel._uploadNotes.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    var isExpanded by remember { mutableStateOf(false) }
    var isExpandedOne by remember { mutableStateOf(false) }
    var isExpandedTwo by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(uploadNoteState) {
        when(uploadNoteState){
            is UploadNotesUiState.Success -> {
                Toast.makeText(context, "Notes saved successfully!", Toast.LENGTH_SHORT).show()
                startConsultingViewModel.resetConsultationNotesState()  // clear fields
                startConsultingViewModel.resetUploadNotesState()        // reset network state
            }
            is UploadNotesUiState.Error -> {
                Toast.makeText(context, uploadNoteState.message, Toast.LENGTH_LONG).show()
                startConsultingViewModel.resetUploadNotesState()        // reset network state
            }
            else -> {}
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xffFAFAFA))
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffFAFAFA))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Consultation Notes",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    scope.launch {
                        startConsultingViewModel.onUploadNotesClicked(
                            token = token,
                            appointmentId  = appointmentId
                        )
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff22C55E),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
            ) {
                if (uploadNoteState is UploadNotesUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Save", color = Color.White)
                }
            }
        }
        // first card
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
                    Text("Clinical Notes", color = Color.Black)
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { isExpanded = !isExpanded }
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isExpanded
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        ClinicalNoteContent(
                            viewModel = startConsultingViewModel,
                            noteUiState = notesUi
                        )
                    }
                }
            }
        }

        // elevated card 2
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
                    Text("Prescriptions", color = Color.Black)
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { isExpandedOne = !isExpandedOne }
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isExpandedOne
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Prescriptions(
                            viewModel = startConsultingViewModel,
                            noteUiState = notesUi
                        )
                    }
                }
            }
        }
        // elevated card 3
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
                    Text("Orders", color = Color.Black)
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = { isExpandedTwo = !isExpandedTwo }
                    ) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isExpandedTwo
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        NotesOrders(
                            viewModel = startConsultingViewModel,
                            noteUiState = notesUi
                        )
                    }
                }
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClinicalNoteContent(
    viewModel: StartConsultingViewModel,
    noteUiState: ConsultationNotesUpdate1,
    modifier: Modifier = Modifier,
) {
    val followUpItem = listOf<String>(
        "1 Week",
        "2 Week",
        "1 Month",
        "3 Month",
        "PRN (AS NEEDED)"
    )
    val priorityItem = listOf(
        "routine",
        "urgent",
        "emergency"
    )
    var followUpExpandState by remember { mutableStateOf(false) }
    var priorityExpandState by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xffFFFFFF))
    ) {
        // one
        Text(
            text = "Chief Complaint",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.chiefComplaint,
            onValueChange = { viewModel.onChiefComplaintChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Patient's primary reason for visit", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        // two
        Text(
            text = "History of Present Illness (HPI)",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.historyOfPresentIllness,
            onValueChange = { viewModel.historyOfPresentIllnessChange(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Detailed Description", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        // three
        Text(
            text = "Physical Examination",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.physicalExamination,
            onValueChange = { viewModel.onPhysicalExaminationChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Findings", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        // four
        Text(
            text = "Assessment & Diagnosis ",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.assessmentAndDiagnosis,
            onValueChange = { viewModel.onAssessmentAndDiagnosisChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Clinical Assessment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        // five
        Text(
            text = "Treatment Plan",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.treatmentPlan,
            onValueChange = { viewModel.onTreatmentPlanChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Recommended Treatment", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        // follow up section
        Text(
            text = "Follow-Up",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = followUpExpandState,
            onExpandedChange = { followUpExpandState = !followUpExpandState },
        ) {
            OutlinedTextField(
                value = noteUiState.followUp,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Follow-Up Period", color = Color(0xffF5EEEF)) },
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
                expanded = followUpExpandState,
                onDismissRequest = { followUpExpandState = !followUpExpandState },
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                followUpItem.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onFollowUpChanged(it)
                            followUpExpandState = false
                        }
                    )
                }
            }

        }//....

        // Priority Section
        Text(
            text = "Priority",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = priorityExpandState,
            onExpandedChange = { priorityExpandState = !priorityExpandState },
        ) {
            OutlinedTextField(
                value = noteUiState.priority,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Follow-Up Period", color = Color(0xffF5EEEF)) },
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
                expanded = priorityExpandState,
                onDismissRequest = { priorityExpandState = !priorityExpandState },
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                priorityItem.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onPriorityChanged(it)
                            priorityExpandState = false
                        }
                    )
                }
            }

        }
    }
}

@Suppress("ViewModelForwarding")
@Composable
fun Prescriptions(
    viewModel: StartConsultingViewModel,
    noteUiState: ConsultationNotesUpdate1,
    modifier: Modifier = Modifier
) {

    val dropDownLanguageItem = listOf<String>("Hindi", "English", "Punjabi", "Marathi", "Telugu")
    var nextId by remember { mutableIntStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xffFFFFFF))
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffF8FAFC))
        ) {
            // language selection row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xffF8FAFC)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .background(color = Color(0xffF8FAFC))
                        .padding(4.dp)
                ) {
                    Text(
                        "Prescription Language",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        "Select the language for printing medication instructions",
                        fontSize = 8.sp,
                        color = Color(0xff8F748B)
                    )
                }
                Spacer(Modifier.weight(1f))
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xffF8FAFC)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Language", fontSize = 14.sp
                        )
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = false,
                            onDismissRequest = {}
                        ) {
                            dropDownLanguageItem.forEach { it ->
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {}
                                )
                            }
                        }
                    }
                }
            }

            // render medication for each count
            noteUiState.medications.forEachIndexed { index, medicationItem ->
                MedicationData(
                    medicationItem = medicationItem,        //  pass individual item
                    medicationNumber = index + 1,
                    showDelete = noteUiState.medications.size > 1,
                    onDelete = { viewModel.removeMedication(medicationItem.id) },
                    onAddMedication = { viewModel.addMedication() },
                    onFieldChange = { updated ->            //  single callback
                        viewModel.updateMedication(medicationItem.id) { updated }
                    }
                )
                if (index < noteUiState.medications.size - 1) {
                    HorizontalDivider(
                        color = Color(0xffE2E8F0)
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationData(
    modifier: Modifier = Modifier,
    medicationItem: MedicationItem,
    medicationNumber: Int = 1,
    showDelete: Boolean = false,
    onAddMedication: () -> Unit,
    onDelete: () -> Unit = {},
    onFieldChange: (MedicationItem) -> Unit
) {
    val frequencyItem = listOf(
        "1 Week",
        "2 Week",
        "1 Month",
        "3 Month",
        "At Bed Time"
    )
    val mealTimeItem = listOf(
        "Before Meal",
        "After Meal",
        "With Meal",
        "Empty Stomach",
        "At Bed Time"
    )
    var frequencyExpandState by remember { mutableStateOf(false) }
    var mealTimeExpandState by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xffF8FAFC))
            .padding(4.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffF8FAFC))
        ) {
            Text(
                "Medication $medicationNumber",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.weight(1f))
            if (showDelete) {
                IconButton(onClick = { onDelete() }) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = Color.Black
                    )
                }
            }
        }

        // Medication Name
        Text(
            text = "Medication Name",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = medicationItem.medicationName,
            onValueChange = { onFieldChange(medicationItem.copy(medicationName = it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter medication name", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        // Dosage
        Text(
            text = "Dosage",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = medicationItem.dosage,
            onValueChange = { onFieldChange(medicationItem.copy(dosage = it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("e.g. 500mg", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        // Duration
        Text(
            text = "Duration",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = medicationItem.duration,
            onValueChange = { onFieldChange(medicationItem.copy(duration = it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("e.g. 7 days", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        // Special Instructions
        Text(
            text = "Special Instructions (for this medication)",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = medicationItem.specialInstructions,
            onValueChange = { onFieldChange(medicationItem.copy(specialInstructions = it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Any special instructions", color = Color(0xffF5EEEF)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )

        // Frequency Dropdown
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
                value = medicationItem.frequency,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Frequency", color = Color(0xffF5EEEF)) },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Black
                    )
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
                expanded = frequencyExpandState,
                onDismissRequest = { frequencyExpandState = false },
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                frequencyItem.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item, color = Color.Black) },
                        onClick = {
                            onFieldChange(medicationItem.copy(frequency = item))
                            frequencyExpandState = false
                        }
                    )
                }
            }
        }

        // Meal Timing Dropdown
        Text(
            text = "Meal Timing",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = mealTimeExpandState,
            onExpandedChange = { mealTimeExpandState = !mealTimeExpandState },
        ) {
            OutlinedTextField(
                value = medicationItem.mealTime,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Meal Timing", color = Color(0xffF5EEEF)) },
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Black
                    )
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
                expanded = mealTimeExpandState,
                onDismissRequest = { mealTimeExpandState = false },
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                mealTimeItem.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item, color = Color.Black) },
                        onClick = {
                            onFieldChange(medicationItem.copy(mealTime = item))
                            mealTimeExpandState = false
                        }
                    )
                }
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffF8FAFC))
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onAddMedication() },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xffEFF6FF)
                )
            ) {
                Text("ADD MEDICATION", color = Color.Black)
            }
            Spacer(Modifier.width(4.dp))
            Button(
                onClick = {},
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff3B82F6)
                )
            ) {
                Text("Send To Pharmacy", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesOrders(
    viewModel: StartConsultingViewModel,
    noteUiState: ConsultationNotesUpdate1,
    modifier: Modifier = Modifier
) {
    var urgencyExpandState by remember { mutableStateOf(false) }
    val urgencyListItems = listOf(
        "routine",
        "urgent",
        "stat"
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(Color(0xffFFFFFF))
    ) {

        Text(
            text = "Laboratory Tests",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.labTest,
            onValueChange = { viewModel.onLabTestChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        Text(
            text = "Imaging Studies",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.imagingStudies,
            onValueChange = { viewModel.onImagingStudiesChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        Text(
            text = "Referrals",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.referrals,
            onValueChange = { viewModel.onReferralsChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        Text(
            text = "Expected Timeline",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = noteUiState.expectedTimeline,
            onValueChange = { viewModel.onExpectedTimeLineChanged(it) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xffF8FAFC),
                unfocusedContainerColor = Color(0xffF8FAFC),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            )
        )
        Text(
            text = "Urgency",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.padding(8.dp)
        )
        ExposedDropdownMenuBox(
            expanded = urgencyExpandState,
            onExpandedChange = { urgencyExpandState = !urgencyExpandState },
        ) {
            OutlinedTextField(
                value = noteUiState.urgency,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Follow-Up Period", color = Color(0xffF5EEEF)) },
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
                expanded = urgencyExpandState,
                onDismissRequest = { urgencyExpandState = !urgencyExpandState },
                containerColor = Color(0xffF8FAFC),
                matchTextFieldWidth = true,
            ) {
                urgencyListItems.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onUrgencyChanged(it)
                            urgencyExpandState = false
                        }
                    )
                }
            }
        }
    }
}
