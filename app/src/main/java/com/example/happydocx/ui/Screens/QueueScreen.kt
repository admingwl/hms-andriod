package com.example.happydocx.ui.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun QueueScreen(modifier: Modifier = Modifier) {
    var searchText by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        // Search Bar
        SearchBar(
            query = searchText,
            onQueryChange = { searchText = it },
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text("Search patient name or ID...", color = Color(0xFFB0B8C1)) },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {}
        Spacer(modifier = Modifier.height(16.dp))
        // Filter and Sort Buttons
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp,end = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            FilterSortButton(text = "Filter")
            Spacer(Modifier.weight(0.5f))
            FilterSortButton(text = "Sort")
        }
        Spacer(modifier = Modifier.height(24.dp))
        // Header
        Text(
            text = "Today's Queue",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A2A4B)
        )
        Text(
            text = "Track waiting patients and queue movement.",
            fontSize = 16.sp,
            color = Color(0xFF6B7683),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // Status Cards
        QueueStatusCards()
        Spacer(modifier = Modifier.height(32.dp))
        // Patient Card Example
        QueuePatientCard(
            number = "1",
            name = "sdfghjk",
            patientId = "7676111095",
            status = "In Consultation",
            statusColor = Color(0xFF1A9E9E),
            statusBg = Color(0xFFE6FAFB),
            checkedIn = "08:50 am",
            type = "Appointment",
            estWait = "Now",
            estWaitColor = Color(0xFF1A9E9E),
            modifier = Modifier.padding(bottom = 24.dp)
        )
        // Empty State
        QueueEmptyState()
    }
}

@Composable
fun FilterSortButton(text: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(44.dp)
            .width(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(android.R.drawable.ic_menu_sort_by_size),
                contentDescription = null,
                tint = Color(0xFF6B7683),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, color = Color(0xFF6B7683), fontSize = 16.sp)
        }
    }
}

@Composable
fun QueueStatusCards(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusCard(title = "WAITING", value = "0", bgColor = Color(0xFFE6FAFB), modifier = Modifier.weight(1f))
            StatusCard(title = "IN PROGRESS", value = "0", bgColor = Color(0xFFE6FAFB), modifier = Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusCard(title = "COMPLETED", value = "0", bgColor = Color(0xFFE6FAFB), modifier = Modifier.weight(1f))
            StatusCard(title = "AVG. WAIT", value = "0 min", bgColor = Color(0xFFE6FAFB), modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun StatusCard(title: String, value: String, bgColor: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = value, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A2A4B))
            Text(text = title, fontSize = 14.sp, color = Color(0xFF6B7683))
        }
    }
}

@Composable
fun QueueEmptyState(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .padding(vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                // Placeholder for image
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE9ECF2)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_gallery),
                        contentDescription = null,
                        tint = Color(0xFFB0B8C1),
                        modifier = Modifier.size(36.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .offset(x = 24.dp, y = 24.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_delete),
                        contentDescription = null,
                        tint = Color(0xFFE05C5C),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "No queue records found",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A2A4B),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "It looks like the waiting room is clear. New check-ins will appear here automatically.",
                fontSize = 15.sp,
                color = Color(0xFF6B7683),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun QueuePatientCard(
    number: String,
    name: String,
    patientId: String,
    status: String,
    statusColor: Color,
    statusBg: Color,
    checkedIn: String,
    type: String,
    estWait: String,
    estWaitColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF174A5A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF1A2A4B)
                    )
                    Text(
                        text = patientId,
                        fontSize = 14.sp,
                        color = Color(0xFF6B7683)
                    )
                }
                // Status pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(statusBg)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(statusColor)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = status,
                            color = statusColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color(0xFFE9ECF2), thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text("CHECKED IN", fontSize = 12.sp, color = Color(0xFFB0B8C1))
                    Text(checkedIn, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A2A4B))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("TYPE", fontSize = 12.sp, color = Color(0xFFB0B8C1))
                    Text(type, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF1A2A4B))
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("EST. WAIT", fontSize = 12.sp, color = Color(0xFFB0B8C1))
                    Text(estWait, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = estWaitColor)
                }
            }
        }
    }
}
