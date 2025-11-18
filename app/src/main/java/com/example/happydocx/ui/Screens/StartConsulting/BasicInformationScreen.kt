package com.example.happydocx.ui.Screens.StartConsulting

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultingMainScreen(
    navController: NavController
) {
    val context = LocalContext.current
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
                TabScreen()
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
fun ClinicalAssessmentScreen(modifier: Modifier = Modifier) {
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
            expanded = false,
            onExpandedChange = {},
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Symptoms", color = Color(0xffa3aab5)) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff9fafb),
                    unfocusedContainerColor = Color(0xfff9fafb),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
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
            expanded = false,
            onExpandedChange = {},
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Diagnosis", color = Color(0xffa3aab5)) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff9fafb),
                    unfocusedContainerColor = Color(0xfff9fafb),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
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
            value = "",
            onValueChange = {},
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
            modifier = Modifier.padding(paddingValues = PaddingValues(0.dp)).align(Alignment.End),
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
        Column(modifier = Modifier.fillMaxWidth()){
            FilledTonalButton(
                onClick = {},
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.padding(paddingValues = PaddingValues(0.dp))
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
fun Medication(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp)) {
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
            expanded = false,
            onExpandedChange = {},
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Medication", color = Color(0xffa3aab5)) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff9fafb),
                    unfocusedContainerColor = Color(0xfff9fafb),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
            }
        }

        Spacer(Modifier.height(8.dp))
        FilledTonalButton(
            onClick = {},
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(paddingValues = PaddingValues(0.dp)).align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            Text("Submit", color = Color.White)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestInvestigation(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 16.dp)) {
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
            expanded = false,
            onExpandedChange = {},
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Medication", color = Color(0xffa3aab5)) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff9fafb),
                    unfocusedContainerColor = Color(0xfff9fafb),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {},
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
            }
        }
        Spacer(Modifier.height(8.dp))
        FilledTonalButton(
            onClick = {},
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier.padding(paddingValues = PaddingValues(0.dp)).align(Alignment.End),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1d4ed8))
        ) {
            Text("Submit", color = Color.White)
        }
    }
}


// Tab screen 
@Composable
fun TabScreen(modifier: Modifier = Modifier) {
    var tabIndex by remember { mutableStateOf(0) }
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
                            0 -> ClinicalAssessmentScreen()
                            1 -> VitalSignAndSymtoms()
                            2 -> Medication()
                            3 -> TestInvestigation()
                        }
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun Mypreview(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    ConsultingMainScreen(navController = navController)
}




