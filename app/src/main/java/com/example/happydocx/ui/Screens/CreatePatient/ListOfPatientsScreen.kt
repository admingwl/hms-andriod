package com.example.happydocx.ui.Screens.CreatePatient

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.computeHorizontalBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.ViewModels.PatientViewModel.GetTimeSlotsForAppointmentViewModel
import com.example.happydocx.ui.ViewModels.PatientViewModel.GetTimeSlotsUiState
import com.example.happydocx.ui.ViewModels.PatientViewModel.PatientListUiState
import com.example.happydocx.ui.ViewModels.PatientViewModel.PatientListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.exp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    modifier: Modifier = Modifier,
    viewModel: PatientListViewModel,
    token: String,
    navController: NavController
) {

    val listState = viewModel._listState.collectAsStateWithLifecycle().value
    // get drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    // launch every time the user open the screen and triggering the patient fetching form api
    LaunchedEffect(key1 = token) {
        viewModel.getPatientList(token)
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
                                navController.popBackStack()
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
                                drawerState.close()
                            }
                        }
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        },

        ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    modifier = modifier.background(brush = gradient_colors),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    ),
                    title = {
                        Text(
                            "Patients",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                navController.navigate("AddNewPatientScreen/$token")
                            },
                            modifier = modifier.padding(end = 20.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.add),
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
        ) { paddingValues ->
            Column(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xffF8FAFC))
            ) {
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(brush = gradient_colors)
                ) {
                    MySearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { query ->
                            searchQuery = query
                        }
                    )
                }

                when (listState) {
                    is PatientListUiState.Loading -> {
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

                    is PatientListUiState.Success -> {
                        val successState =
                            listState as PatientListUiState.Success

                        // filter the list according to the search

                        val filterNames = if (searchQuery.isBlank()) {
                            successState.patientList
                        } else {
                            successState.patientList.filter { it ->
                                val fullName = "${it.first_name} ${it.last_name}"
                                fullName.contains(searchQuery, ignoreCase = true)
                            }
                        }

                        val totalPages = ceil(
                            successState.totalRecords.toDouble() / (successState.limit)
                        ).toInt()
                        val currentPage = successState.page
                        if (successState.patientList.isEmpty()) {
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
                                    "No Patient",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "adding patients first",
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
                                        painter = painterResource(R.drawable.add),
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                    Text(
                                        "Create Patient",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = modifier.fillMaxSize()
                            ) {
                                if (filterNames.isEmpty() && searchQuery.isNotEmpty()) {
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
                                    LazyColumn(
                                        modifier = modifier
                                            .weight(1f)
                                            .background(Color(0xFFC6D9E8)),
                                        contentPadding = PaddingValues(8.dp)
                                    ) {
                                        items(
                                            items = filterNames,
                                            key = { id -> id._id }) { patient ->
                                            PatientCard(
                                                name = "${patient.first_name} ${patient.middle_name} ${patient.last_name}",
                                                gender = patient.gender,
                                                phoneNumber = patient.contactNumber,
                                                address = patient.address?.addressLine1
                                                    ?: "No address provided",
                                                navController = navController,
                                                token = token
                                            )
                                        }
                                    }
                                }
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
                                            onClick = { viewModel.loadPreviousPage(token = token) },
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
                                            "PAGE $currentPage OF $totalPages",
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

                    is PatientListUiState.Error -> {

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
                                    viewModel.getPatientList(token = token)  // Retry
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
) {
//    var searchQuery by remember { mutableStateOf("") }
//    var isSearchActive by remember { mutableStateOf(false) }

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onSearch = {},
                expanded = false,
                onExpandedChange = { },
                placeholder = {
                    Text(
                        text = "Search patient",
                        color = Color(0xFF9CA3AF),
                        fontSize = 16.sp,
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF9CA3AF),
                        modifier = Modifier.size(20.dp)
                    )
                },
            )
        },
        expanded = false,
        onExpandedChange = { },
        shape = RoundedCornerShape(8.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFF3F4F6),
        ),
        tonalElevation = 0.dp
    ) {}
}


@Composable
fun PatientCard(
    modifier: Modifier = Modifier,
    name: String? = "Deepak",
    gender: String? = "Male",
    lastVisit: String? = "15 jan 2026",
    phoneNumber: String? = "1234567890",
    address: String? = "abc address is mine",
    navController: NavController,
    token: String
) {
    var isMenuOpen by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
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
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = modifier) {
                    if (name != null) {
                        Text(
                            text = name,
                            fontSize = 20.sp,
                            color = Color(0xff1F7BF6)
                        )
                    }
                    if (gender != null) {
                        Text(
                            text = gender,
                            fontSize = 15.sp,
                            color = Color(0xff8E9AA9)
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
                Column {
                    IconButton(
                        onClick = { isMenuOpen = !isMenuOpen }
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(
                        expanded = isMenuOpen,
                        onDismissRequest = { isMenuOpen = false },
                        containerColor = Color(0xffFFFFFF)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Schedule", color = Color.Black) },
                            onClick = {
                                // here i navigate to schedule patient page.
                                navController.navigate("scheduleAppointmentScreen/$token")
                                isMenuOpen = false
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(R.drawable.schedule),
                                    contentDescription = null,
                                    tint = Color(0xff42A5F5),
                                    modifier = modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            HorizontalDivider(
                color = Color(0xff8E9AA9)
            )
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.time),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text = "Last Visit: $lastVisit",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.phone_24),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    if (phoneNumber != null) {
                        Text(
                            text = phoneNumber,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.map),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    if (address != null) {
                        Text(
                            text = address,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun ScheduleAppointmentScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    token: String,
    viewModel: GetTimeSlotsForAppointmentViewModel
) {

    var openDatePicker_ScheduleAppointment = remember { mutableStateOf(false) }
    val datePickerState_ScheduleAppointment = rememberDatePickerState()
    val context = LocalContext.current
    //get ui state
    val uiState = viewModel._uiState.collectAsStateWithLifecycle().value
    // get network state
    val networkState = viewModel._networkState.collectAsStateWithLifecycle().value
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Schedule Appointment", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = modifier.background(gradient_colors),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(0xffFFFFFF))
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .background(color = Color(0xffFFFFFF))
            ) {
                Text(
                    text = "Patient Name",
                    fontSize = 24.sp,
                    color = Color(0xff1F7BF6),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "PAT0-1201343",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Date",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = uiState.dateState,
                    onValueChange = { viewModel.onDateChanged(it) },
                    modifier = modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    openDatePicker_ScheduleAppointment.value = !openDatePicker_ScheduleAppointment.value
                                    // not call api here because the date is still not selected
//                                    viewModel.getAllTimeSlots(
//                                        token = token,
//                                        date = uiState.dateState
//                                    )
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.calendar_days),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = modifier.size(20.dp)
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = "mm/dd/yyyy",
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
                if (openDatePicker_ScheduleAppointment.value) {
                    DatePickerDialog(
                        shape = RoundedCornerShape(30.dp),
                        colors = DatePickerDefaults.colors(
                            // add color to date picker dialog
                            containerColor = Color(0xffebedfc)
                        ),
                        onDismissRequest = { openDatePicker_ScheduleAppointment.value = false },
                        confirmButton = {
                            TextButton(onClick = {
                                openDatePicker_ScheduleAppointment.value = false
                                datePickerState_ScheduleAppointment.selectedDateMillis?.let { millis ->
                                    val formatted =
                                        SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
                                            .format(Date(millis))
                                    viewModel.onDateChanged(formatted)

                                    // after the date is selected then call the api .
                                    scope.launch{
                                        viewModel.getAllTimeSlots(
                                            token = token,
                                            date = formatted
                                        )
                                    }
                                }
                            }) { Text("OK", color = Color.Black) }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                openDatePicker_ScheduleAppointment.value = false
                            }) {
                                Text("Cancel", color = Color.Black)
                            }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState_ScheduleAppointment,
                            colors = DatePickerDefaults.colors(
                                containerColor = Color(0xffebedfc),
                                dayContentColor = Color.Black,
                                titleContentColor = Color.Black,
                                weekdayContentColor = Color.Black,
                                headlineContentColor = Color.Black,
                                navigationContentColor = Color.Black,
                                subheadContentColor = Color.Black,
                                dateTextFieldColors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White
                                )
                            )
                        )
                    }
                }
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Time",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                ExposedDropdownMenuBox(
                    expanded = uiState.isTimeExpanded,
                    onExpandedChange = {
                        //  Only allow opening if we have slots loaded
                        if (networkState is GetTimeSlotsUiState.Success) {
                            viewModel.onTimeExpandStateChanged(!uiState.isTimeExpanded)
                        }
                    },
                ) {
                    OutlinedTextField(
                        value = uiState.timeState,
                        onValueChange = {},
                        readOnly = true,
                        placeholder = {
                          when(networkState){
                              is GetTimeSlotsUiState.Loading ->{
                                  Row (verticalAlignment = Alignment.CenterVertically){
                                      CircularProgressIndicator(
                                          modifier = Modifier.size(20.dp),
                                          color = Color.Gray,
                                          strokeWidth = 2.dp
                                      )
                                      Spacer(Modifier.width(4.dp
                                      ))
                                      Text("Loading Slots...", color = Color.Gray)
                                  }
                              }
                              is GetTimeSlotsUiState.Success ->{
                                  Text("Select a time Slot")
                              }
                              is GetTimeSlotsUiState.Idle->{
                                  Text("Select a time Slot")
                              }
                              else -> {}
                          }

                                      },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = false)
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Black
                        ),
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = uiState.isTimeExpanded,
                        onDismissRequest = { viewModel.onTimeExpandStateChanged(!uiState.isTimeExpanded) },
                        containerColor = Color(0xffebedfc),
                        matchTextFieldWidth = true,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                        shape = RoundedCornerShape(30.dp)
                    ) {
                        // here comes the list of the suggestions
                        when (networkState) {
                            is GetTimeSlotsUiState.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                            }
                            is GetTimeSlotsUiState.Success ->{
                                val successState = networkState as GetTimeSlotsUiState.Success
                                if(successState.data.isEmpty()){
                                    DropdownMenuItem(
                                        text = { Text("No Slot Available for Selected Date.", color = Color.Black) },
                                        onClick = {
                                            viewModel.onTimeExpandStateChanged(false)
                                        }
                                    )
                                }else {
                                    successState.data.forEach { it ->
                                        DropdownMenuItem(
                                            text = { Text(it.slotTime, color = Color.Black, fontSize = 18.sp
                                            ) },
                                            onClick = {
                                                viewModel.onTimeStateChanged(it.slotTime)
                                                viewModel.onTimeExpandStateChanged(false)
                                            }
                                        )
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Reason for Visit",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(
                    value = uiState.reasonForVisitState,
                    onValueChange = { viewModel.onReasonForVisitChanged(it) },
                    modifier = modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            text = "eg. Annual Checkup, Fever, Consultation",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        unfocusedIndicatorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    )
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                OutlinedButton(
                    onClick = { Log.d("Cancel", "Cancel Button Clicked") },
                    modifier = modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xffFFFFFF)
                    )
                ) {
                    Text(
                        text = "Cancel",
                        color = Color.Black
                    )
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { Log.d("Schedule", "Schedule Button Clicked") },
                    modifier = modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff2563EB)
                    )
                ) {
                    Text(
                        text = "Schedule",
                        color = Color.White
                    )
                }
            }
        }
    }
}
