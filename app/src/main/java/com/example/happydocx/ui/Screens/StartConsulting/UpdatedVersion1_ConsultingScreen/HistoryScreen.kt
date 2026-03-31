package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.ui.ViewModels.StartConsulting.HistoriesUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel
import kotlinx.coroutines.launch
import us.zoom.proguard.strategy
import kotlin.math.ceil

@Composable
fun AllHistoryList(
    token: String,
    patient: String,
    modifier: Modifier = Modifier,
    startConsultingViewModel: StartConsultingViewModel,
    navController: NavController
) {
    val historyState = startConsultingViewModel._historiesState.collectAsStateWithLifecycle().value
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(token) {
        startConsultingViewModel.getAllHistories(
            token = token,
            patient = patient
        )
    }
    when (val state = historyState) {
        is HistoriesUiState.Success -> {
            val historyState = state.data
            val totalPages = ceil(
                state.data.totalPages.toDouble() / (state.data.limit ?: 10)
            ).toInt()
            val currentPage = state.data.page ?: 1
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color(0xffFAFAFA))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffFAFAFA))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total Visits",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Box(
                        modifier = modifier
                            .background(Color.Red, shape = CircleShape)
                            .size(30.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = historyState.totalRecords.toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            Icons.Default.MoreVert,
                            tint = Color.Black,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    items(historyState.appointments) { it ->
                        HistoryItem(
                            visitId = it.appointmentId,
                            doctorName = "${it.doctor.first_name} ${it.doctor.middle_name} ${it.doctor.last_name}".trim(),
                            doctorSpecialization = it.department.departmentName,
                            date = DateUtils.gettingOnlyDate(it.appointmentDate),
                            status = it.status,
                            navController = navController
                        )
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
                            onClick = {
                                scope.launch {
                                    startConsultingViewModel.loadPreviousHistoryPage(
                                        token = token,
                                        patientId = patient
                                    )
                                }
                            },
                            border = BorderStroke(
                                width = 1.dp, color = Color.Black
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
                            onClick = {
                                scope.launch {
                                    startConsultingViewModel.loadNextHistoryPage(
                                        token = token,
                                        patientId = patient
                                    )
                                }
                            },
                            border = BorderStroke(
                                width = 1.dp, color = Color.Black
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

        is HistoriesUiState.Error -> {}
        is HistoriesUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is HistoriesUiState.Idle -> {}
    }
}


@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    visitId: String,
    doctorName: String,
    doctorSpecialization: String,
    date: String,
    status: String,
    navController: NavController
) {
    var expandActions by rememberSaveable { mutableStateOf(false) }
    val actionDropDownItem = listOf("View Past History","Edit","Delete")
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFFFFFFF))
                .padding(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Visit ID",
                            color = Color.Blue,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(
                                    Color(0xFFE6F0FF),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = visitId,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }

                    Box {
                        IconButton(onClick = {
                            expandActions = !expandActions
                        }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More Options",
                                tint = Color.Black
                            )
                        }
                        DropdownMenu(
                            expanded = expandActions,
                            onDismissRequest = { expandActions = false },
                            shape = RoundedCornerShape(8.dp),
                            containerColor = Color(0xffFFFFFF)
                        ) {
                            actionDropDownItem.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(item , color = Color.Black) },
                                    onClick = {
                                        expandActions = false
                                        when(item){
                                            "View Past History" -> {navController.navigate("visitHistoryScreen")}
                                            "Edit"             ->  { /* handle edit */ }
                                            "Delete"           ->  { /* handle delete */ }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.patientimage),
                        contentDescription = "Doctor Avatar",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = doctorName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = doctorSpecialization,
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
//                        Icon(
//                            painter = painterResource(R.drawable),
//                            contentDescription = "Date",
//                            tint = Color.Gray,
//                            modifier = Modifier.size(20.dp)
//                        )
                        //   Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = date,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFE6FFFA),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color.Green, shape = RoundedCornerShape(4.dp))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = status,
                                color = Color(0xFF00A86B),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

        }
    }
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun VisitHistoryScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberLazyListState()
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {Text("Visit History",color = Color.White)},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff2563EB)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                       Icon(
                           Icons.AutoMirrored.Filled.ArrowBack,
                           contentDescription = null,
                           tint =Color.White
                       )
                    }
                }
            )
        },
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(0xffFFFFFF))
        ) {
           LazyColumn(
               modifier = Modifier.fillMaxSize(),
               verticalArrangement = Arrangement.spacedBy(6.dp),
               state = scrollState
           ) {
               items(3){
                   HistoryCard()
               }
           }
        }
    }
}

@Composable
fun HistoryCard(modifier: Modifier = Modifier) {
    var visibility by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier.fillMaxWidth()
            .background(color = Color(0xffF8FAFC)).padding(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color(0xffF8FAFC)),
            verticalAlignment = Alignment.CenterVertically
        ) {
           Text(
               "Visit History 3 of 3",
               color = Color.Black,
               fontWeight = FontWeight.Bold
           )
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {visibility = !visibility}
            ) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        }
        AnimatedVisibility(
            visible = visibility
        ) {
            VisitHistoryCard()
        }
    }
}
@Composable
fun VisitHistoryCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
//            .padding(8.dp),
        colors = CardDefaults.cardColors(Color(0xffF8FAFC))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
        ) {

            // first heading
            Text(
                text = "Psychiatry",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Dec 17, 2025, 09:30 AM",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
           Spacer(Modifier.height(4.dp))
            // second heading
            Text(
                text = "Patient",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Deepak Guleria",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)

            )
            Spacer(Modifier.height(4.dp))
            // third heading
            Text(
                text = "Doctor",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "Dr. Brian Kamau",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)

            )
            Spacer(Modifier.height(4.dp))
            // fourth heading
            Text(
                text = "Reason for visit",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "sdfsd",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Spacer(Modifier.height(4.dp))
            // five heading
            Text(
                text = "Symptoms",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "N/A",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)

            )
            Spacer(Modifier.height(4.dp))
            // sixth heading
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xffF8FAFC))
                    .padding(4.dp)
            ) {
                Text(
                    text = "Vital Signs",
                    color = Color.Black,
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp
                )
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    maxItemsInEachRow = 3
                ) {
                    VitalSignsSurfaceItem()
                    VitalSignsSurfaceItem()
                    VitalSignsSurfaceItem()
                    VitalSignsSurfaceItem()
                    VitalSignsSurfaceItem()
                }
            }
            Spacer(Modifier.height(4.dp))
            // Seventh heading
            Text(
                text = "Assessment (Diagnosis)",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "No diagnosis details available.",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)

            )
            Spacer(Modifier.height(4.dp))
            // Eighth heading
            Text(
                text = "Medications",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "list of all medications.",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)

            )
            Spacer(Modifier.height(4.dp))
            // ninth heading
            Text(
                text = "Tests Ordered",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "list of test ordered",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)

            )
            Spacer(Modifier.height(4.dp))
             // tenth heading
            Text(
                text = "Notes",
                color = Color.Black,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                modifier = Modifier.padding(4.dp)
            )
            Text(
                text = "no notes available",
                color = Color.Black,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun VitalSignsSurfaceItem(
    modifier: Modifier = Modifier,
    title: String = "title",
    value: String = "val",
    unit: String = "Un"
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xffF8FAFC))
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Blood Pressure",
                fontSize = 12.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "44",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "mmHg",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}