package com.example.happydocx.ui.Screens.DoctorAppointments

import android.R.attr.onClick
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import kotlin.math.ceil

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



    // Fetch on compose (same as before)
    LaunchedEffect(Unit) {
        Log.d("DEBUG_SCREEN", "Token received in screen: $token")
        if (token.isNotBlank()) {
            viewModel.getDoctorAppointments(token,page =1, showCompleted = false)
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
                    val totalPages = ceil(
                        successState.total.toDouble() / (successState.limit ?: 10)
                    ).toInt()
                    val currentPage = successState.page ?: 1

                    Column(modifier = Modifier.fillMaxSize()) {
                        // Header showing count
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF5F5F5))
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Showing ${successState.appointments.size} of ${successState.total} appointments (Page $currentPage of $totalPages)",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xff4f61e3),
                                fontWeight = FontWeight.Bold
                            )
                        }
                        // Appointments List
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(successState.appointments) { appointment ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = appointment.patient.name ?: "Unknown Patient",
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text("Status: ${appointment.status ?: "N/A"}")
                                        Text("Date: ${appointment.date ?: "No date"}")
                                        Text("Patient ID: ${appointment.patient._id ?: "N/A"}")
                                    }
                                }
                            }
                        }

                        // Pagination Controls (only show if more than 1 page)
                        if (totalPages > 1) {
                            PaginationControls(
                                currentPage = currentPage,
                                totalPages = totalPages,
                                onPreviousClick = {
                                    viewModel.loadPreviousPage(token)
                                },
                                onNextClick = {
                                    viewModel.loadNextPage(token)
                                },
                                onPageClick = { page ->
                                    viewModel.loadSpecificPage(token, page)
                                }
                            )
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

@Composable
fun PaginationControls(
    currentPage: Int,
    totalPages: Int,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onPageClick: (Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color(0xFFF5F5F5),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous Button
            Button(
                onClick = onPreviousClick,
                enabled = currentPage > 1,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4f61e3),
                    disabledContainerColor = Color.LightGray
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text("← Previous", color = Color.White)
            }

            // Page Info
            Text(
                text = "$currentPage / $totalPages",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xff4f61e3),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Next Button
            Button(
                onClick = onNextClick,
                enabled = currentPage < totalPages,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4f61e3),
                    disabledContainerColor = Color.LightGray
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text("Next →", color = Color.White)
            }
        }
    }
}