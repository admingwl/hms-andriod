package com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ChildTabScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens.CustomTextField

@Composable
fun InsuranceInfoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomTextField(placeHolderValue = "Name")
        CustomTextField(placeHolderValue = "Policy Number")
        CustomTextField(placeHolderValue = "Card Number")
        CustomTextField(placeHolderValue = "Facility Code")
        CustomTextField(placeHolderValue = "Initial Balance")


        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {},
            modifier = modifier
                .padding(18.dp)
                .align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff16a34a),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text("SAVE", color = Color.White, modifier = modifier.padding(horizontal = 30.dp))
        }
    }
}