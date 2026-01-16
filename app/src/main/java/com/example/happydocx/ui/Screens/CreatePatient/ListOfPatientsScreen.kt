package com.example.happydocx.ui.Screens.CreatePatient

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.computeHorizontalBounds
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.ViewModels.PatientViewModel.PatientListUiState
import com.example.happydocx.ui.ViewModels.PatientViewModel.PatientListViewModel
import kotlin.math.exp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    modifier: Modifier = Modifier,
    viewModel: PatientListViewModel,
    token: String?
) {

    val listState = viewModel._listState.collectAsStateWithLifecycle().value

    // launch every time the user open the screen and triggering the patient fetching form api
    LaunchedEffect(key1 = token) {
        viewModel.getPatientList(token)
    }
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
                        onClick = {},
                        modifier = modifier.padding(end = 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = modifier.size(25.dp)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xffF8FAFC))
        ) {
            Column(
                modifier = modifier.fillMaxWidth().background(brush = gradient_colors)
            ){ MySearchBar() }

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
                        Box(
                            modifier = modifier.fillMaxSize()
                        ) {
                            LazyColumn(
                                modifier = modifier
                                    .fillMaxSize()
                                    .background(Color(0xFFC6D9E8)),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(
                                    items = successState.patientList,
                                    key = { id -> id._id }) { patient ->
                                    PatientCard(
                                        name = "${patient.first_name} ${patient.middle_name} ${patient.last_name}",
                                        gender = patient.gender,
                                        phoneNumber = patient.contactNumber,
                                        address = patient.address?.addressLine1
                                            ?: "No address provided"
                                    )
                                }
                            }
                            Column(
                                modifier = modifier.
                                fillMaxWidth().
                                background(Color.White)
                                    .align(Alignment.BottomCenter),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                               Row(
                                   modifier = modifier.fillMaxWidth().padding(10.dp).background(Color(0xffFEFEFF)),
                                   verticalAlignment = Alignment.CenterVertically,
                                   horizontalArrangement = Arrangement.SpaceAround
                               ) {
                                   OutlinedButton(
                                       onClick = {},
                                       border = BorderStroke(width = 1.dp, color = Color.Black),
                                       shape = RoundedCornerShape(8.dp),
                                       modifier = modifier.padding(6.dp).weight(1f)
                                       ){
                                       Text("Previous",color = Color.Black)
                                   }
                                   Text("PAGE 1 OF 8",color = Color.Black,modifier = modifier.padding(horizontal = 10.dp))
                                   OutlinedButton(
                                       onClick = {},
                                       border = BorderStroke(width = 1.dp, color = Color.Black),
                                       shape = RoundedCornerShape(8.dp),
                                       modifier = modifier.padding(6.dp).weight(1f)
                                   ){
                                       Text("Next",color = Color.Black)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { isSearchActive = false },
                expanded = isSearchActive,
                onExpandedChange = {  },
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
        expanded = isSearchActive,
        onExpandedChange = { },
        shape = RoundedCornerShape(8.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFF3F4F6),
        ),
        tonalElevation = 0.dp
    ){}
}


@Composable
fun PatientCard(
    modifier: Modifier = Modifier,
    name: String = "Deepak",
    gender: String = "Male",
    lastVisit: String = "15 jan 2026",
    phoneNumber: String = "1234567890",
    address: String = "abc address is mine"
) {
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
                .padding(8.dp)
                .background(Color(0xffFFFFFF))
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = modifier) {
                    Text(
                        text = name,
                        fontSize = 20.sp,
                        color = Color(0xff1F7BF6)
                    )
                    Text(
                        text = gender,
                        fontSize = 15.sp,
                        color = Color(0xff8E9AA9)
                    )
                }
                Spacer(Modifier.weight(1f))
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color.Black
                )
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
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(3.dp))
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
                        modifier = modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(3.dp))
                    Text(
                        text = phoneNumber,
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(3.dp))
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
