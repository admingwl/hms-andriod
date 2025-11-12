package com.example.happydocx.ui.Screens.Patient

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.happydocx.R

@Preview
@Composable
fun ParticularPatientScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff8fafc)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ElevatedCard(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xffffffff)),
            shape = RoundedCornerShape(30.dp),
          //  elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)){
                Text("Niku Sahu", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = Color(0xff3b82f6))
                Text("Male", color = Color(0xff707f94))

                Spacer(Modifier.height(8.dp))
                Row {
                    Text("Last Visit:", fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(4.dp))
                    Text("10 Nov 2025(1 day(s) ago", color = Color(0xff707f94))
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 24.dp),
                    color = Color(0xffdbdbd9)
                )

                // Getting Number Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xffdbeafe),
                        modifier = Modifier
                            .size(40.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.phone_24),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = Color(0xff488af7)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            "Contact Number",
                            color = Color(0xff707f94)
                        )
                        Spacer(Modifier.height(1.dp))
                        Text("8968788813", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(8.dp))
                // getting visit type
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xffdcfce7),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(paddingValues = PaddingValues(0.dp)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.visit),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = Color(0xff45cf77)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            "Visit Type",
                            color = Color(0xff707f94)
                        )
                        Spacer(Modifier.height(1.dp))
                        Text("Follow-Up", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(8.dp))
                // Appointment slot
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xfff3e8ff),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(paddingValues = PaddingValues(0.dp)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.addappointments),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = Color(0xffb875f9)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            "Appointment Slot",
                            color = Color(0xff707f94)
                        )
                        Spacer(Modifier.height(1.dp))
                        Text("07:34, 18/11/2025", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.height(8.dp))
                // Current Status
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xfffef3c7),
                        modifier = Modifier
                            .size(40.dp)
                            .padding(paddingValues = PaddingValues(0.dp)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.currentstatus),
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = Color(0xfff59e0b)
                        )
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(
                            "Current Status",
                            color = Color(0xff707f94)
                        )
                        Spacer(Modifier.height(1.dp))
                        Text("In Consultation", fontWeight = FontWeight.Bold, color = Color(0xfff59e0b))
                    }
                }

                Spacer(Modifier.height(50.dp))
                FilledTonalButton(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff3b82f6)),
                    modifier = Modifier.padding(paddingValues = PaddingValues(0.dp))
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "Start Consulting",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 22.dp)
                    )
                }
                Spacer(Modifier.height(10.dp))
                FilledTonalButton(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffe1e7ef)),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.padding(paddingValues = PaddingValues(0.dp))
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Cancel",
                        color = Color(0xff566274),
                        modifier = Modifier.padding(horizontal = 54.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            //elevated card end
        }
    }
}