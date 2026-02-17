package com.example.happydocx.Data.ZoomMeetingManager

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.happydocx.Data.ZoomMeetingManager.ZoomMeetingManager.initCall

@Composable
fun ZoomCallScreen(context: ComponentActivity) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "HappyDocx Telehealth", modifier = Modifier.padding(16.dp))

        Button(onClick = {
            // Button click par call start hogi
//            initCall(
//                context,
//            )
        }) {
            Text(text = "Start Consultation (Host)")
        }
    }
}