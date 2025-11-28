package com.example.happydocx.ui.Screens.StartConsulting

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.ViewModels.StartConsulting.BasicInformationViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartConsultingScreen(
    modifier: Modifier = Modifier,
    viewModel: BasicInformationViewModel,
    navController: NavController,
    patientId:String,
    token:String,
    appointmentID:String,

) {
    StartConsultingScreenTabLayout(
        modifier = modifier,
        viewModel = viewModel,
        navController = navController,
        patientId = patientId,
        token=token,
        appointmentId = appointmentID,

    )
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartConsultingScreenTabLayout(
    modifier: Modifier = Modifier,
    viewModel: BasicInformationViewModel,
    navController: NavController,
    patientId:String,
    token:String,
    appointmentId:String,
) {
    var tabIndex = remember { mutableIntStateOf(0) }
    val options = listOf(
        "Basic Information",
        "Invoices",
        "patient Document",
        "Prescription"
    )

    // ADDED: Wrap everything in Scaffold to handle proper layout structure
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = Color(0xfff0f5ff), // ADDED: Background color
        topBar = {
            // TopAppBar with gradient background that extends to status bar
            TopAppBar(
                title = {
                    // ADDED: Put TabRow inside the TopAppBar title
                    TabRow(
                        selectedTabIndex = tabIndex.value,
                        containerColor = Color.Transparent, // Transparent to show gradient
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        options.forEachIndexed { index, title ->
                            Tab(
                                text = {
                                    Text(
                                        title,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                },
                                selected = tabIndex.value == index,
                                onClick = {
                                    tabIndex.value = index
                                }
                            )
                        }
                    }
                },
                // ADDED: Use TopAppBarDefaults with gradient colors
                // This automatically handles status bar color matching
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent, // Keep transparent
                    scrolledContainerColor = Color.Transparent
                ),
                // ADDED: Apply gradient background to entire TopAppBar
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = gradient_colors) // Gradient extends to status bar
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(Color(0xfff0f5ff))
        ) {
            when (tabIndex.value) {
                0 -> {
                    ConsultingMainScreen(
                        viewModel = viewModel,
                        navController = navController,
                        patientId = patientId,
                        token = token,
                        appointmentId = appointmentId,
                    )
                }
                1 -> {
                    InvoicesScreen()
                }
                2 -> {
                    PatientDocumentsUploading()
                }
                3 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Prescription - Coming Soon", color = Color.Black)
                    }
                }
            }
        }
    }
}