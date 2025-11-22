package com.example.happydocx.ui.Screens.Patient

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.ViewModels.AppointmentUiState
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ParticularPatientScreen(
    patientId:String,
    viewModel: DoctorAppointmentsViewModel,
    navController: NavController
) {

    val uiState = viewModel._uiState.collectAsStateWithLifecycle()

    // Find the full data by ID (from loaded list)
    val selectedAppointment by remember(uiState) {
        derivedStateOf {
            (uiState.value as? AppointmentUiState.Success)?.appointments?.find { appointment ->
                appointment.patient._id == patientId  // find on response data
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff0f5ff)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when {
            uiState.value is AppointmentUiState.Loading ->
                CircularProgressIndicator(color = Color(0xff3b82f6))

            selectedAppointment != null -> {
                // !! by this we insure that the selecttedAppointment is not be null its our gurante
                val patient = selectedAppointment!!.patient  // Full Patient data
                val appointment = selectedAppointment  // Full Appointment data


                ElevatedCard(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xffffffff)),
                    shape = RoundedCornerShape(30.dp),
                    //  elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            "${patient.first_name} ${patient.last_name}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = Color(0xff3b82f6)
                        )
                        Text(patient.gender, color = Color(0xff707f94))

                        Spacer(Modifier.height(8.dp))
                        Row {
                            Text("Last Visit:", fontWeight = FontWeight.Bold)
                            Spacer(Modifier.width(4.dp))
                            Text(DateUtils.formatAppointmentDate(patient.createdAt), color = Color(0xff707f94))
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 24.dp),
                            color = Color(0xffdbdbd9)
                        )

                        // Getting Number Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color(0xffdbeafe),
                                modifier = Modifier
                                    .size(40.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.phone_24),
                                    contentDescription = null,
                                    modifier = Modifier.padding(8.dp),
                                    tint = Color(0xff488af7)
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                                Text(
                                    "Contact Number",
                                    color = Color(0xff707f94)
                                )
                                Text(patient.contactNumber, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        // getting visit type
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color(0xffdcfce7),
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(paddingValues = PaddingValues(0.dp)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.visit),
                                    contentDescription = null,
                                    modifier = Modifier.padding(8.dp),
                                    tint = Color(0xff45cf77)
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(
                                    "Visit Type",
                                    color = Color(0xff707f94)
                                )
                                Text(appointment?.visitType ?: "null", fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        // Appointment slot
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color(0xfff3e8ff),
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(paddingValues = PaddingValues(0.dp)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.addappointments),
                                    contentDescription = null,
                                    modifier = Modifier.padding(8.dp),
                                    tint = Color(0xffb875f9)
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(
                                    "Appointment Slot",
                                    color = Color(0xff707f94)
                                )
                                Text(DateUtils.formatAppointmentDate(appointment?.date ?: ""), fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(Modifier.height(8.dp))

                        // Current Status
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = Color(0xfffef3c7),
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(paddingValues = PaddingValues(0.dp)),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.currentstatus),
                                    contentDescription = null,
                                    modifier = Modifier.padding(8.dp),
                                    tint = Color(0xfff59e0b)
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(
                                    "Current Status",
                                    color = Color(0xff707f94)
                                )
                                // here i add the color according to the status
                                val statusColor = when(appointment?.status){
                                    "Confirmed" -> Color(0xff10b981)
                                    "In Consultation","Consultation" -> Color(0xffef4444)
                                    else -> {
                                        // other wise the default color
                                        Color(0xfff59e0b)
                                    }
                                }
                                Text(
                                    appointment?.status ?: "null",
                                    fontWeight = FontWeight.Bold,
                                    color = statusColor
                                )
                            }
                        }

                        Spacer(Modifier.height(50.dp))
                        FilledTonalButton(
                            onClick = {navController.navigate("SartConsultationScreen")},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3b82f6)),
                            modifier = Modifier
                                .padding(paddingValues = PaddingValues(0.dp))
                                .align(Alignment.CenterHorizontally),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                "Start Consulting",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 22.dp)
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                        FilledTonalButton(
                            onClick = {navController.popBackStack()},
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xffe1e7ef)),
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .padding(paddingValues = PaddingValues(0.dp))
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                "Cancel",
                                color = Color(0xff566274),
                                modifier = Modifier.padding(horizontal = 54.dp),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } // card finish here
            }
        }
    }
}