package com.example.happydocx.ui.Screens.DoctorAppointments

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.AppointmentUiState
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorAppointmentScreen(
    viewModel: DoctorAppointmentsViewModel = viewModel(),
    token: String,  // Non-nullable, as per your NavGraph
    navController: NavController
) {
    val uiState = viewModel._uiState.collectAsStateWithLifecycle()  // State<AppointmentUiState> — keep this!
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val gradient_colors = Brush.linearGradient(
        listOf(
            Color(0xff586AE5), Color(0xff717FE8),
            Color(0xff7785E9)
        )
    )
    // dialog state
    val showDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val tokenManger = TokenManager(context = context)


    // Log state changes for debug (optional, remove later)
    LaunchedEffect(uiState.value) {
        Log.d("DEBUG_COMPOSE", "UI State changed to: ${uiState.value}")
    }

    // Fetch on compose (same as before)
    LaunchedEffect(Unit) {
        Log.d("DEBUG_SCREEN", "Token received in screen: $token")
        if (token.isNotBlank()) {
            viewModel.getDoctorAppointments(token, showCompleted = false)
        } else {
            Log.e("DEBUG_SCREEN", "Invalid token—setting error")
        }
    }

    // adding dialog for the user to logout
    if(showDialog.value){
        AlertDialog(
            containerColor = Color.White,
            textContentColor = Color.Black,
            titleContentColor = Color.Black,
            onDismissRequest = {showDialog.value = false},
            title = {Text("Logout")},
            text = {Text("Are you shure you want to logout?")},
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        // clear token and user data
                        tokenManger.clearToken()
                        // navigate back to login screen
                        navController.navigate("Login"){
                            // clear entire backStack
                            popUpTo(0){inclusive = true  }
                            launchSingleTop = true
                        }
                    }
                    showDialog.value = false
                }) {
                    Text("Yes", color = Color(0xff4f61e3))
                }
            },
            dismissButton = {TextButton(onClick = { showDialog.value = false }) {
                Text("No", color = Color(0xff4f61e3))
            }}
        )
    }

    Scaffold(

        modifier = Modifier.fillMaxSize()
        /*In Compose, when you use a collapsing or moving TopAppBar (like enterAlwaysScrollBehavior()),
          you must attach the scroll behavior to the scrollable content using this modifier:*/
        .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {Text("Appointments")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent // keep the same gradient
                ),
                modifier = Modifier.background(brush = gradient_colors),
                scrollBehavior = scrollBehaviour,
                actions = {
                    IconButton(
                        onClick = {
                              showDialog.value = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.outline_logout_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(Color.White),  // Ensures visibility
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState.value) {  // .value gives the actual AppointmentUiState
                is AppointmentUiState.Loading -> {
                    Log.d("DEBUG_STATE", "Showing Loading state")
                    Column(modifier = Modifier.fillMaxSize(),horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator(
                            color = Color(0xff4f61e3),
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Loading appointments...", color = Color(0xff4f61e3))
                    }
                }

                is AppointmentUiState.Success -> {
                    val successState =
                        uiState.value as AppointmentUiState.Success  //  Safe: .value is Success
                    Log.d(
                        "DEBUG_STATE",
                        "Success! Total: ${successState.total}, Appointments: ${successState.appointments.size}"
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Text(
//                            text = "Total Appointments: ${successState.total}",
//                            color = Color.White
//                        )
//                        Text(
//                            text = "Loaded ${successState.appointments.size} items",
//                            style = MaterialTheme.typography.bodyMedium,
//                            color = Color.White
//                        )
//                        Text(
//                            "appointment page limit: ${successState.limit}",
//                            color = Color.White
//                        )
//                        Text(
//                            "appointment page: ${successState.page}",
//                            color = Color.White
//                        )
                        // Quick list preview (expand later)
                        LazyColumn(
                            modifier = Modifier.padding(top = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(successState.appointments) { appointment ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(0.8f)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text("Status: ${appointment.status}")
                                        Text("patient ID: ${appointment.patient._id}")
                                        Text("Date: ${appointment.date}")
                                        Text("patient name: ${appointment.patient.name}")
                                        Text("Company Id: ${appointment.companyId}")
                                        Text("appointment ID: ${appointment.id}")

                                    }
                                }
                            }
                        }
                    }
                }

                is AppointmentUiState.Error -> {
                    val errorState = uiState.value as AppointmentUiState.Error  //  Safe unwrap
                    Log.e("DEBUG_STATE", "Error: ${errorState.message}")
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                            .background(Color.White)) {

                        Image(
                            painter = painterResource(R.drawable.wifi),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            colorFilter = ColorFilter.tint(Color.Black)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            contentPadding = PaddingValues(0.dp),
                            onClick = {
                            viewModel.getDoctorAppointments(token)  // Retry
                        },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xff4f61e3),
                                contentColor = Color.Black
                            ),
                            shape = RoundedCornerShape(8.dp),
                            ) {
                            Text("Retry", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}