package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happydocx.R

@Composable
fun AllVitalsScreen(
    modifier: Modifier = Modifier
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xffFAFAFA))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Current Vitals",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1D4ED8)
                ),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "Add Vitals",
                    color = Color.White
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(10) {
                VitalCard()
            }
        }
    }
}

// vital cards
@Composable
fun VitalCard() {
    var isExpanded by remember { mutableStateOf(false) }
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xffFFFFFF)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xffFFFFFF))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                    .padding(8.dp)
                    .background(Color(0xffFFFFFF))
            ) {
                Text("Date: 1/14/2026, 4:30:49 PM", color = Color.Black)
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    PatientVitalSignsInsideCard()
                }
            }
        }
    }
}


@Preview
@Composable
fun PatientVitalSignsInsideCard(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp) // increased from 4.dp
            .background(Color(0xffFFFFFF)),
        verticalArrangement = Arrangement.spacedBy(4.dp) // increased from 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp) // consistent spacing
        ) {
            CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.heart, normalRange = "Normal: 60-100")
            CurrentVitalCard(modifier = Modifier.weight(1f) ,cardName = "BP", normalRange = "Normal: 120-80")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp) // consistent spacing
        ) {
            CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.temperature ,cardName = "Temperature", normalRange = "Normal: 97-99")
            CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.wind,cardName = "Respiration", normalRange = "Normal: 12-20")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp) // consistent spacing
        ) {
            CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.blood_drop,cardName = "Oxygen", normalRange = "Normal: >95")
            CurrentVitalCard(modifier = Modifier.weight(1f), cardIcon = R.drawable.weight,cardName = "Weight", normalRange = "Normal: 50-80")
        }
    }
}


