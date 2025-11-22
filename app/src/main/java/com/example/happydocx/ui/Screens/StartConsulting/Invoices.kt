package com.example.happydocx.ui.Screens.StartConsulting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun InvoicesScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xffffffff))
            .padding(18.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
           items(20){item->
                InvoicesCard()
            }
        }

    }
}


@Composable
fun InvoicesCard(
    date: String = "12-12-2023",
    time: String = "12:00 PM",
    invoiceId: String = "123456789",
    patientName: String = "John Doe",
    phone: String = "1234567890",
    doctor: String = "Dr. Smith",
    paymentMethod: String = "Cash",
    totalAmount: String = "100.00",
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xfff8fafc)
        ),
        shape = RoundedCornerShape(1.dp)
    ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(modifier = modifier.fillMaxWidth()) {
                Column {
                    Text(
                        patientName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text("ID: $invoiceId", color = Color(0xff69798f))
                }
                Spacer(Modifier.weight(1f))
                Column {
                    Text(
                        "₹${totalAmount}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xff3b82f6),
                        modifier = modifier.align(Alignment.End)
                    )
                    Text("$date , $time", color = Color(0xff69798f), fontSize = 14.sp)
                }
            }
            HorizontalDivider(Modifier.padding(vertical = 8.dp), color = Color(0xFF8A95A1))
            Row(modifier = modifier.fillMaxWidth()) {
                Text("Doctor:", color = Color(0xff69798f), fontSize = 16.sp)
                Spacer(Modifier.weight(1f))
                Text(doctor, fontSize = 16.sp)
            }
            Row(modifier = modifier.fillMaxWidth()) {
                Text("Phone:", color = Color(0xff69798f), fontSize = 16.sp)
                Spacer(Modifier.weight(1f))
                Text(phone, fontSize = 16.sp)
            }
            Row(modifier = modifier.fillMaxWidth()) {
                Text("Payment:", color = Color(0xff69798f), fontSize = 16.sp)
                Spacer(Modifier.weight(1f))
                Text(paymentMethod, fontSize = 16.sp)
            }
        }
    }
}