package com.example.happydocx.ui.Screens.CreatePatient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens.AppointmentScreen
import com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens.GeneralScreen
import com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens.InvoicesScreen
import com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens.PatientDocumentScreen
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.ViewModels.PatientViewModel.SavePatientViewModel


@Composable
fun ParentTabLayoutAddPatientInfo(modifier: Modifier = Modifier,viewModel: SavePatientViewModel,token:String) {

    // first create the tab list.
    val tabs = listOf<String>(
        "General",
        "Appointment",
        "Invoices",
        "Patient Docs"
    )
    // initial state of the tab.
    var initialSelectedTab by rememberSaveable{ mutableIntStateOf(0) }

    TabRow(
        selectedTabIndex = initialSelectedTab,
        containerColor = Color.Transparent,
        modifier = modifier.background(brush = gradient_colors)
    ) {
        tabs.forEachIndexed { index, label ->
            Tab(
                text = {
                    Text(
                        text = label,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                onClick = {
                    initialSelectedTab = index
                },
                selected = initialSelectedTab == index
            )
        }
    }

    Box(
        modifier = modifier.fillMaxSize()
    ){
        when(initialSelectedTab){
            0 -> GeneralScreen(viewModel = viewModel, token = token)
            1 -> AppointmentScreen()
            2 -> InvoicesScreen()
            3 -> PatientDocumentScreen()

        }
    }
}