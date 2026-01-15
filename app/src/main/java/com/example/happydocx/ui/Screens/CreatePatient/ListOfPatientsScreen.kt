package com.example.happydocx.ui.Screens.CreatePatient

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.computeHorizontalBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors


@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(modifier: Modifier = Modifier) {
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
                modifier = modifier.fillMaxWidth().height(95.dp).padding(bottom = 8.dp)
                    .background(brush = gradient_colors),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                MySearchBar()
            }

            LazyColumn(
                modifier = modifier.fillMaxSize().background(Color(0xffF8FAFC))
            ) {
                items(10){item->
                    PatientCard()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar() {
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(
                "Search by name or ID...",
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
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding( horizontal = 10.dp,),
        shape = RoundedCornerShape(12.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFF3F4F6),
            dividerColor = Color.Transparent
        ),
        tonalElevation = 0.dp
    ) {}
}


@Composable
fun PatientCard(
    modifier: Modifier = Modifier,
    name: String = "Deepak",
    gender: String = "Male",
    lastVisit:String = "15 jan 2026",
    phoneNumber:String = "1234567890",
    address:String = "abc address is mine"
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
            modifier = modifier.fillMaxWidth()
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
