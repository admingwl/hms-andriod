package com.example.happydocx.ui.Screens.DoctorAppointments

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.Navigation.AppointmentTopBar
import com.example.happydocx.ui.ViewModels.AppointmentUiState
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModel
import com.example.happydocx.ui.theme.HappyDocxTheme
import kotlinx.coroutines.launch
import kotlin.math.ceil


val gradient_colors = Brush.linearGradient(
    listOf(
        Color(0xff586AE5), Color(0xff717FE8),
        Color(0xff7785E9)
    )
)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorAppointmentScreen(
    viewModel: DoctorAppointmentsViewModel,
    token: String,  // Non-nullable, as per your NavGraph
    navController: NavController,
) {

    val uiState =
        viewModel._uiState.collectAsStateWithLifecycle()  // State<AppointmentUiState> — keep this!


    val scrollBehaviour =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior() // for large top app bar use exitUntilCollapsedScrollBehaviour()

    // get drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // dialog state
    val showDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val tokenManger = TokenManager(context = context)

    val scope = rememberCoroutineScope()


    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }


    // Fetch on compose (same as before)
    LaunchedEffect(Unit) {
        Log.d("DEBUG_SCREEN", "Token received in screen: $token")
        if (token.isNotBlank()) {
            viewModel.getDoctorAppointments(token, page = 1, showCompleted = false)
        } else {
            Log.e("DEBUG_SCREEN", "Invalid token—setting error")
        }
    }

    // adding dialog for the user to logout
    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            textContentColor = Color.Black,
            titleContentColor = Color.Black,
            onDismissRequest = { showDialog.value = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(onClick = {
                    coroutineScope.launch {
                        // clear token and user data
                        tokenManger.clearToken()
                        // navigate back to login screen
                        navController.navigate("Login") {
                            // clear entire backStack
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                    showDialog.value = false
                }) {
                    Text("Yes", color = Color(0xff4f61e3))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("No", color = Color(0xff4f61e3))
                }
            }
        )
    }

    // add navigation drawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Transparent,
                drawerContentColor = Color.Transparent,
                modifier = Modifier.background(
                    color = Color(0xfff8fafc),
                    shape = RoundedCornerShape(30.dp)
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))

                    Text(
                        "HappyDocx",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 40.sp
                    )

                    HorizontalDivider()

                    Text(
                        "Section 1",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    NavigationDrawerItem(
                        label = { Text("Item 1") },
                        selected = false,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.Black
                        ),
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("Item 2") },
                        selected = false,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.Black
                        ),
                        onClick = { /* Handle click */ }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        "Section 2",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    NavigationDrawerItem(
                        label = { Text("Settings") },
                        selected = false,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.Black
                        ),
                        icon = {
                            Icon(
                                Icons.Outlined.Settings,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        },
                        badge = { Text("20") }, // Placeholder
                        onClick = { /* Handle click */ }
                    )
                    NavigationDrawerItem(
                        label = { Text("List") },
                        selected = false,
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.Black
                        ),
                        icon = {
                            Icon(
                                Icons.AutoMirrored.Outlined.List,
                                tint = Color.Black,
                                contentDescription = null
                            )
                        },
                        onClick = { /* Handle click */ },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        gesturesEnabled = true // enable gesture to enable drawer
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                /*In Compose, when you use a collapsing or moving TopAppBar (like enterAlwaysScrollBehavior()),
              you must attach the scroll behavior to the scrollable content using this modifier:*/
                .nestedScroll(scrollBehaviour.nestedScrollConnection),
            topBar = {
                AppointmentTopBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { searchQuery = it },
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },

                    onLogoutClick = { showDialog.value = true },
                    scrollBehavior = scrollBehaviour
                )
            },
            floatingActionButton = {
                if(uiState.value is AppointmentUiState.Success){
                    val SuccessState = uiState.value as AppointmentUiState.Success
                    if(SuccessState.appointments.isNotEmpty()){
                        // Only show FAB when there are appointments (not empty state)
                        FloatingActionButton(
                            onClick = {},
                            modifier = Modifier.padding(bottom = 80.dp, end = 10.dp),
                            containerColor = Color(0xff5a6de6)
                        ) {
                            IconButton(onClick = {}) {
                                Icon(
                                    painter = painterResource(R.drawable.addappointments),
                                    contentDescription = null,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize()
                    .background(Color(0xffebebeb)),  // Ensures visibility
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // add search bar

                when (uiState.value) {  // .value gives the actual AppointmentUiState
                    is AppointmentUiState.Loading -> {
                        Log.d("DEBUG_STATE", "Showing Loading state")
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
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
                            uiState.value as AppointmentUiState.Success

                        // Filter the list according to the search
                        val filteredAppointments = if (searchQuery.isBlank()) {
                            successState.appointments
                        } else {
                            successState.appointments.filter { appointment ->
                                val fullName = "${appointment.patient.first_name} ${appointment.patient.last_name}"
                                fullName.contains(searchQuery, ignoreCase = true)
                            }
                        }

                        val totalPages = ceil(
                            successState.total.toDouble() / (successState.limit ?: 10)
                        ).toInt()
                        val currentPage = successState.page ?: 1

                        // Case 1: No patients at all (new doctor)
                        if (successState.appointments.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = Color(0xfff8fafc)),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.to_do_list),
                                    contentDescription = null,
                                    modifier = Modifier.size(120.dp),
                                    colorFilter = ColorFilter.tint(Color(0xff4f61e3))
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    "No Appointments",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "adding your appointments first",
                                    fontSize = 16.sp,
                                    color = Color.Gray
                                )
                                Spacer(modifier = Modifier.height(24.dp))
                                Button(
                                    onClick = { /* Navigate to add patient screen */ },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xff4f61e3)
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.padding(horizontal = 32.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.addappointments),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        "Create Appointments",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        } else {
                            // Case 2 & 3: Has patients - show list or search results
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                            ) {
                                // Show message if no search results found
                                if (filteredAppointments.isEmpty() && searchQuery.isNotEmpty()) {
                                    Column(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .fillMaxWidth()
                                            .background(color = Color(0xfff8fafc)),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Image(
                                            painter = painterResource(R.drawable.usernotfound),
                                            contentDescription = null,
                                            modifier = Modifier.size(80.dp),
                                            colorFilter = ColorFilter.tint(Color.Gray)
                                        )
                                        Spacer(modifier = Modifier.height(16.dp))
                                        Text(
                                            "No patients found matching \"$searchQuery\"",
                                            color = Color.Gray,
                                            fontSize = 16.sp
                                        )
                                    }
                                } else {
                                    // Appointments List
                                    LazyColumn(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .background(color = Color(0xFFFFFFFF)),
                                        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp)
                                    ) {
                                        items(filteredAppointments) { appointment ->
                                            CardComponent(
                                                name = "${appointment.patient.first_name} ${appointment.patient.last_name}"
                                                    ?: "No Name",
                                                lastVisit = "last visit: ${
                                                    DateUtils.formatAppointmentDate(
                                                        appointment.patient.createdAt
                                                    )
                                                }",
                                                patientId = appointment.patient._id,
                                                navController = navController
                                            )
                                            HorizontalDivider(color = Color(0xffdbdbd9))
                                        }
                                    }
                                }

                                // Pagination Controls (hide during search, show only for full list)
                                if (totalPages > 1 && searchQuery.isBlank()) {
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
                    }

                    is AppointmentUiState.Error -> {
                        val errorState = uiState.value as AppointmentUiState.Error  //  Safe unwrap
                        Log.e("DEBUG_STATE", "Error: ${errorState.message}")
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White)
                        ) {

                            Image(
                                painter = painterResource(R.drawable.wifi),
                                contentScale = ContentScale.Crop,
                                contentDescription = null,
                                modifier = Modifier.size(100.dp),
                                colorFilter = ColorFilter.tint(Color.Gray)
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
        color = Color.Transparent,
        shadowElevation = 8.dp,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient_colors)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous Button
            TextButton(
                onClick = onPreviousClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(0.5f)
                    .padding(end = 8.dp)
                    .padding(paddingValues = PaddingValues(0.dp)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left_long_line),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Text(" Previous", color = Color.White)
            }

            // Page Info
            Text(
                text = "$currentPage / $totalPages",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Next Button
            TextButton(
                onClick = onNextClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 8.dp)
                    .padding(paddingValues = PaddingValues(0.dp)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(" Next", color = Color.White)
                Icon(
                    painter = painterResource(R.drawable.arrow_right_long_line),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}


@Composable
fun CardComponent(
    name: String,
    patientId: String,
    lastVisit: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .clickable { navController.navigate("ParticularPatientScreen/$patientId") }
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 15.dp)
        ) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 19.sp, color = Color.Black)
            Spacer(Modifier.height(8.dp))
            Text(lastVisit, color = Color.Black)
        }
    }
}




