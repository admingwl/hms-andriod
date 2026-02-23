package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import android.graphics.Paint
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happydocx.R
import us.zoom.proguard.FONT_WEIGHT_BOLD


@Composable
fun BasicInfoOfPatient(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            ParticularPatientAppointmentInfoTopAppBar(
                onArrowBackClicked = {},
                onMenuBarIconClicked = {})
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xffF1F5F9)),
        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xffFFFFFF)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PatientImage()
                    Spacer(Modifier.width(4.dp))
                    PatientInfoRow()
                    Spacer(Modifier.weight(1f))
                    BloodGroupComponent()
                }
                ContactInfoOfPatient()
                ActiveAllergiesSection()
            }
            Spacer(Modifier.height(8.dp))
            PatientAppointmentInfoTabScreen()
        }
    }
}


// create my topAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticularPatientAppointmentInfoTopAppBar(
    modifier: Modifier = Modifier,
    onArrowBackClicked: () -> Unit,
    onMenuBarIconClicked: () -> Unit
) {
    TopAppBar(
        title = { Text("Appointment Detail", color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xff2563EB)
        ),
        navigationIcon = {
            IconButton(
                onClick = { onArrowBackClicked() },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "arrow back",
                    tint = Color.White,
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onMenuBarIconClicked() },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "arrow back",
                    tint = Color.White,
                )
            }
        }
    )
}

@Composable
fun PatientImage(
    modifier: Modifier = Modifier,
    patientName: String = "Deepak Guleria",
) {
    Image(
        painter = painterResource(R.drawable.patientimage),
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .background(color = Color(0xffFAFAFA))
            .size(70.dp)
            .clip(shape = CircleShape)
            .padding(8.dp)
    )
}

@Composable
fun PatientInfoRow(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.background(color = Color(0xffFFFFFF))
    ) {
        Text(
            text = "Samruddhi Panda",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
//        Surface(
//            modifier = Modifier,
//            shape = RoundedCornerShape(16.dp),
//            color = Color(0xffF1F5F9)
//        ) {
        Text(
            text = "PAT-20251212-00002",
            fontSize = 12.sp,
            color = Color(0xff47556E)
        )
//        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color(0xffFFFFFF))
        ) {
            Text(
                text = "10 yrs, Female",
                fontSize = 12.sp,
                color = Color(0xff64748B)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "2/18/2026, 9:35:39 AM",
                fontSize = 12.sp,
                color = Color(0xff64748B)
            )
        }
    }
}


@Composable
fun BloodGroupComponent(modifier: Modifier = Modifier) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = Color(0xffFEF2F2),
        modifier = modifier
            .padding(8.dp)
            .border(width = 1.dp, color = Color(0xffFEE2E2))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.blood_drop),
                contentDescription = null,
                tint = Color(0xffEF4444),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "A+",
                fontSize = 13.sp,
                color = Color(0xffB91C1C)
            )
        }
    }
}


@Composable
fun ActiveAllergiesSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color(0xffFFFFFF))
            .padding(8.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "ALLERGIES :",
            fontSize = 14.sp,
            color = Color(0xff94A3B8)
        )
        Spacer(Modifier.width(4.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            items(6) {
                SuggestionChip(
                    onClick = {},
                    label = { Text("Penicilin") },
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = Color(0xffFEF2F2),
                        labelColor = Color(0xffDC264E),
                    ),
                    border = BorderStroke(width = 1.dp, color = Color(0xffFEE2E2))
                )
            }
        }
    }
}

@Preview
@Composable
fun ContactInfoOfPatient(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffFFFFFF))
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.phone_24),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xffB1BCCB)
                )
                Spacer(Modifier.width(4.dp))
                Text("1234567890", color = Color.Black)
            }
            Spacer(Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.MailOutline,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color(0xffB1BCCB)
                )
                Spacer(Modifier.width(4.dp))
                Text("deepak@gmail.com", color = Color.Black)
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(6.dp)) {
            Icon(
                painter = painterResource(R.drawable.map),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color(0xffB1BCCB)
            )
            Spacer(Modifier.width(4.dp))
            Text("Pathankot, Punjab", color = Color.Black)
        }
    }
}

@Composable
fun PatientAppointmentInfoTabScreen(modifier: Modifier = Modifier) {

    val tabsOptions = listOf<String>(
        "Overview", "Vitals", "Medications", "LabResult", "History", "Documents", "Notes"
    )
    var tabIndex by rememberSaveable { mutableStateOf(0) }
    var showFullScreen by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    Column(modifier = modifier.fillMaxSize()) {
        SecondaryScrollableTabRow(
            selectedTabIndex = tabIndex,
            scrollState = scrollState,
            containerColor = Color(0xffFFFFFF),
            contentColor = Color(0xff1E293B),
            edgePadding = 3.dp
        ) {
            tabsOptions.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    selected = tabIndex == index,
                    selectedContentColor = Color(0xff2563EB),
                    unselectedContentColor = Color(0xff727C86),

                    onClick = {
                        tabIndex = index
                        showFullScreen = true  // Open full screen when tab clicked
                    }
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when (tabIndex) {
                0 -> OverViewScreen()
                1 -> AllVitalsScreen()
                2 -> AllMedicationList()
                3 -> LabResultScreen()
                4 -> AllHistoryList()
                5 -> AllDocumentsScreen()
                6 -> NotesScreen()
            }
        }
    }
}





