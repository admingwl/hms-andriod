package com.example.happydocx.ui.Screens.CreatePatient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.ViewModels.PatientViewModel.SavePatientViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddNewPatientScreen(navController: NavController, modifier: Modifier = Modifier,viewModel: SavePatientViewModel,token:String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Add Patients",color = Color.White)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = modifier.background(brush = gradient_colors),
                navigationIcon = {
                    IconButton(
                        onClick = {
                             navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null,tint = Color.White)
                    }


                }
            )
        }
    ) {paddingValues->
        Column(
            modifier = modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ParentTabLayoutAddPatientInfo(viewModel = viewModel, token = token)
        }
    }
}