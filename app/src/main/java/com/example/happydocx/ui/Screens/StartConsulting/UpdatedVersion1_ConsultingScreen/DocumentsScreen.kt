package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happydocx.R

@Preview
@Composable
fun AllDocumentsScreen(
    modifier:Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().background(Color(0xffFAFAFA))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color(0xffFAFAFA))
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Medical Documents",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(Modifier.weight(1f))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff1D4ED8),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(4.dp),
            ) {
                Text(
                    "Upload",
                    color = Color.White
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(6.dp)
        ) {
            items(10){it->
              DocumentItem()
            }
        }
    }
}

@Preview
@Composable
fun DocumentItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.document),
                    contentDescription = "Document Icon",
                    tint = Color.Blue,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Ultrasound",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Blue
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dr. Aris Thorne",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Oct 24, 2023",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(R.drawable.eye),
                        contentDescription = "View Document",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                    ) {
                    Icon(
                        painter = painterResource(R.drawable.download),
                        contentDescription = "Download Document",
                        tint = Color.Blue,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

        }
    }
}


