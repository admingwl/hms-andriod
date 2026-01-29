package com.example.happydocx.ui.Screens.DoctorAppointments

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.happydocx.Data.Model.DoctorAppointment.Appointment
import com.example.happydocx.Data.Network.ConnectivityObserver
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.Navigation.AppointmentSearchBar
import com.example.happydocx.ui.ViewModels.AppointmentUiState
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModel
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModelFactory
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
    token: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: DoctorAppointmentsViewModel,
) {

    val context = LocalContext.current
    val uiState =
        viewModel._uiState.collectAsStateWithLifecycle()  // State<AppointmentUiState> — keep this!

    // get the network state
    val networkStatus = viewModel.status.collectAsStateWithLifecycle().value

    // Snackbar host state
    val snackbarHostState = remember { SnackbarHostState() }

    // get drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // dialog state
    val showDialog = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val tokenManger = TokenManager(context = context)

    val scope = rememberCoroutineScope()


    var searchQuery by remember { mutableStateOf("") }
    var searchActive by remember { mutableStateOf(false) }

    // Track previous network status to detect changes
    var previousNetworkStatus by remember { mutableStateOf<ConnectivityObserver.Status?>(null) }

    // Show Snackbar when network status changes
    LaunchedEffect(networkStatus) {
        if (previousNetworkStatus != null && previousNetworkStatus != networkStatus) {
            when (networkStatus) {
                ConnectivityObserver.Status.Available -> {
                    snackbarHostState.showSnackbar(
                        message = "✓ Internet Connected",
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                }

                ConnectivityObserver.Status.Lost,
                ConnectivityObserver.Status.Unavailable -> {
                    snackbarHostState.showSnackbar(
                        message = "⚠ No Internet Connection",
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                }

                ConnectivityObserver.Status.Losing -> {
                    snackbarHostState.showSnackbar(
                        message = "⚠ Connection Unstable",
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                }
            }
        }
        previousNetworkStatus = networkStatus
    }

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
                    shape = RoundedCornerShape(10.dp)
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

//                    Text(
//                        "Appointments",
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Black
//                    )
                    NavigationDrawerItem(
                        label = { Text("Appointments") },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.schedule),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.Black
                        ),
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Patients") },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.user_avatar),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp),
                                tint = Color.Black
                            )
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.Black
                        ),
                        onClick = {
                            scope.launch {
                                navController.navigate("patientScreen/$token") {
                                    launchSingleTop = true
                                }
                                drawerState.close()
                            }
                        }
                    )


//                    Text(
//                        "Section 2",
//                        modifier = Modifier.padding(16.dp),
//                        style = MaterialTheme.typography.titleMedium,
//                        color = Color.Black
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("Settings") },
//                        selected = false,
//                        colors = NavigationDrawerItemDefaults.colors(
//                            selectedTextColor = Color.Black,
//                            unselectedTextColor = Color.Black
//                        ),
//                        icon = {
//                            Icon(
//                                Icons.Outlined.Settings,
//                                contentDescription = null,
//                                tint = Color.Black
//                            )
//                        },
//                        badge = { Text("20") }, // Placeholder
//                        onClick = { /* Handle click */ }
//                    )
//                    NavigationDrawerItem(
//                        label = { Text("List") },
//                        selected = false,
//                        colors = NavigationDrawerItemDefaults.colors(
//                            selectedTextColor = Color.Black,
//                            unselectedTextColor = Color.Black
//                        ),
//                        icon = {
//                            Icon(
//                                Icons.AutoMirrored.Outlined.List,
//                                tint = Color.Black,
//                                contentDescription = null
//                            )
//                        },
//                        onClick = { /* Handle click */ },
//                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    Snackbar(
                        snackbarData = data,
                        containerColor = when {
                            data.visuals.message.contains("Connected") -> Color(0xFF4CAF50)
                            else -> Color(0xFFF44336)
                        },
                        contentColor = Color.White,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            },
            topBar = {
                TopAppBar(
                    modifier = modifier.background(brush = gradient_colors),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),

                    title = {
                        Text(
                            "Appointments",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { showDialog.value = true },
                            modifier = modifier
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.outline_logout_24),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = modifier.size(25.dp)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            modifier = modifier.padding(end = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = modifier.size(25.dp)
                            )
                        }
                    }
                )

            },
//            floatingActionButton = {
//                if(uiState.value is AppointmentUiState.Success){
//                    val SuccessState = uiState.value as AppointmentUiState.Success
//                    if(SuccessState.appointments.isNotEmpty()){
//                        // Only show FAB when there are appointments (not empty state)
//                        FloatingActionButton(
//                            onClick = {},
//                            modifier = Modifier.padding(bottom = 80.dp, end = 10.dp),
//                            containerColor = Color(0xff5a6de6)
//                        ) {
//                            IconButton(onClick = {}) {
//                                Icon(
//                                    painter = painterResource(R.drawable.addappointments),
//                                    contentDescription = null,
//                                    modifier = Modifier.padding(8.dp)
//                                )
//                            }
//                        }
//                    }
//                }
//            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .fillMaxSize()
                    .background(Color(0xffebebeb)),  // Ensures visibility
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // add search bar
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(brush = gradient_colors)
                ) {
                    // add doctor Appointments Search bar here.
                    AppointmentSearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it }
                    )
                }

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
                                val fullName =
                                    "${appointment.patient.first_name} ${appointment.patient.last_name}"
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
                                            "No Appointment found matching \"$searchQuery\"",
                                            color = Color.Gray,
                                            fontSize = 16.sp
                                        )
                                    }
                                } else {
                                    // Appointments List
                                    Column(
                                        modifier = modifier
                                            .fillMaxSize()

                                    ) {
                                        LazyColumn(
                                            modifier = Modifier
                                                .weight(1f)
                                                .background(color = Color(0xFFC6D9E8)),
                                            contentPadding = PaddingValues(
                                                horizontal = 2.dp,
                                                vertical = 6.dp
                                            )
                                        ) {
                                            items(
                                                items = filteredAppointments,
                                                // Providing a unique key for each item is a crucial performance optimization.
                                                // It allows Compose to uniquely identify each item across recompositions.
                                                // When the list changes (e.g., an item is added, removed, or moved),
                                                // Compose uses these keys to understand which items are new and which have just moved,
                                                // preventing it from recreating every item and preserving the state of existing ones.
                                                // Here, `it.id` is a perfect candidate for a key as it's a unique identifier
                                                // for each appointment.
                                                key = { it ->
                                                    it.id
                                                }
                                            ) { appointment ->
                                                DoctorAppointmentCard(
                                                    appointment = appointment,
                                                    token = token,
                                                    navController = navController,
                                                    patientId = appointment.patient._id,
                                                    appointmentId = appointment.id,
                                                )

                                            }
                                        }
                                        // column for the page controls .
                                        Column(
                                            modifier = modifier
                                                .fillMaxWidth()
                                                .background(Color.White),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Row(
                                                modifier = modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp)
                                                    .background(Color(0xffFEFEFF)),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceAround
                                            ) {
                                                OutlinedButton(
                                                    onClick = { viewModel.loadPreviousPage(token) },
                                                    border = BorderStroke(
                                                        width = 1.dp,
                                                        color = Color.Black
                                                    ),
                                                    shape = RoundedCornerShape(8.dp),
                                                    modifier = modifier
                                                        .padding(6.dp)
                                                        .weight(1f)
                                                ) {
                                                    Text("Previous", color = Color.Black)
                                                }
                                                Text(
                                                    "$currentPage / $totalPages",
                                                    color = Color.Black,
                                                    modifier = modifier.padding(horizontal = 10.dp)
                                                )
                                                OutlinedButton(
                                                    onClick = { viewModel.loadNextPage(token = token) },
                                                    border = BorderStroke(
                                                        width = 1.dp,
                                                        color = Color.Black
                                                    ),
                                                    shape = RoundedCornerShape(8.dp),
                                                    modifier = modifier
                                                        .padding(6.dp)
                                                        .weight(1f)
                                                ) {
                                                    Text("Next", color = Color.Black)
                                                }
                                            }
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DoctorAppointmentCard(
    modifier: Modifier = Modifier,
    appointment: Appointment,
    token: String,
    navController: NavController,
    patientId: String,
    appointmentId: String,
) {
    val scope = rememberCoroutineScope()
    var isExpanded by remember { mutableStateOf(false) }

    val surfaceText = when (appointment.status) {
        "Confirmed" -> "Scheduled"
        "Waiting" -> "Checked In"
        "In Consultation" -> "In Consultation"
        else -> {}
    }

    val surfaceBackgroundColor = when (surfaceText) {
        "In Consultation" ->
            Color(0xffDCFCE7)

        "Scheduled" ->
            Color(0xffDFF1FD)

        "Checked In" ->
            Color(0xffF3E8FF)

        else -> Color(0xffF5F5F5)
    }

    val surfaceContentColor = when (surfaceText) {
        "In Consultation" ->
            Color(0xff15805C)

        "Scheduled" ->
            Color(0xff0369A1)

        "Checked In" ->
            Color(0xff7E22CE)

        else ->
            Color(0xff616161)
    }


    Card(
        modifier = modifier
            .clickable {
                scope.launch {
                    navController.navigate("SartConsultationScreen/$patientId/$token/$appointmentId")
                }
            }
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffFFFFFF)
        )
    ) {
        // parent column
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color(0xffFFFFFF))
        ) {

            // row 1
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = modifier) {
                    Text(
                        text = "${appointment.patient.first_name} ${appointment.patient.middle_name} ${appointment.patient.last_name}".trim(),
                        fontSize = 20.sp,
                        color = Color(0xff1F7BF6)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = appointment.patient.gender,
                        fontSize = 15.sp,
                        color = Color(0xff8E9AA9)
                    )
                }
                Spacer(Modifier.weight(1f))
                Column {
                    IconButton(
                        onClick = { isExpanded = !isExpanded }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false },
                        containerColor = Color(0xffFFFFFF)
                    ) {
                        when (surfaceText) {
                            "In Consultation" -> {
                                DropdownMenuItem(
                                    text = { Text("Rescheduled", color = Color.Black) },
                                    onClick = { isExpanded = false },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.schedule),
                                            contentDescription = null,
                                            modifier = modifier.size(18.dp),
                                            tint = Color.Black
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Continue", color = Color.Black) },
                                    onClick = {
                                        scope.launch {
                                            navController.navigate("SartConsultationScreen/$patientId/$token/$appointmentId")
                                        }
                                    },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.arrow_right_long_line),
                                            contentDescription = null,
                                            modifier = modifier.size(18.dp),
                                            tint = Color.Black
                                        )
                                    }
                                )
                            }

                            "Checked In" -> {
                                DropdownMenuItem(
                                    text = { Text("Reschedule", color = Color.Black) },
                                    onClick = { isExpanded = false },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.schedule),
                                            contentDescription = null,
                                            modifier = modifier.size(18.dp),
                                            tint = Color.Black
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("prepare", color = Color.Black) },
                                    onClick = { isExpanded = false },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.to_do_list),
                                            contentDescription = null,
                                            modifier = modifier.size(18.dp),
                                            tint = Color.Black
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Start Consultation", color = Color.Black) },
                                    onClick = { isExpanded = false },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.stethoscope_medical_tool),
                                            contentDescription = null,
                                            modifier = modifier.size(18.dp),
                                            tint = Color.Black
                                        )
                                    }
                                )
                            }

                            "Scheduled" -> {
                                DropdownMenuItem(
                                    text = { Text("Reschedule", color = Color.Black) },
                                    onClick = { isExpanded = false },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.schedule),
                                            contentDescription = null,
                                            modifier = modifier.size(18.dp),
                                            tint = Color.Black
                                        )
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Check In", color = Color.Black) },
                                    onClick = { isExpanded = false },
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(R.drawable.user_avatar),
                                            contentDescription = null,
                                            modifier = modifier.size(18.dp),
                                            tint = Color.Black
                                        )
                                    }
                                )
                            }

                            else -> {}
                        }
                    }
                }

            }
            Spacer(Modifier.height(8.dp))
            // row 2
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier.weight(1f)
                ) {
                    Text(
                        "CONTACT",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff93A3B8)
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        appointment.patient.contactNumber,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Column(modifier = modifier.weight(1f)) {
                    Text(
                        "VISIT TYPE",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff93A3B8)
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(appointment.visitType, fontWeight = FontWeight.Bold, color = Color.Black)
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(
                color = Color(0xffF1F4F8),
                modifier = modifier.padding(end = 20.dp)
            )
            Spacer(Modifier.height(10.dp))
            // 3 row
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = modifier.weight(1f)
                ) {
                    Text(
                        "SLOT",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff93A3B8)
                    )
                    Text(
                        appointment.appointmentTime,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Column(modifier = modifier.weight(1f)) {
                    Surface(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(30.dp),
                        color = surfaceBackgroundColor,
                        contentColor = surfaceContentColor,
                    ) {
                        Text(
                            surfaceText.toString(),
                            fontWeight = FontWeight.Bold,
                            modifier = modifier.padding(6.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
            // row 4
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.time),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = modifier.size(15.dp)
                )
                Spacer(Modifier.width(4.dp))
                DateUtils.formatAppointmentDate(appointment.patient.updatedAt)?.let {
                    Text(
                        text = "Last Visit: $it",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color(0xff93A3B8)
                    )
                }
            }
        }
    }
}


