package com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.CreatePatient.ChildTabLayoutPatientInfo

@Preview
@Composable
fun GeneralScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = Color.Transparent, shape = CircleShape)
                .size(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.patientimage),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(shape = CircleShape)
                    .background(Color.LightGray)
                    .size(120.dp)

            )

            IconButton(
                onClick = {},
                modifier = modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(R.drawable.photo_camera),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = modifier
                        .size(30.dp)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        CustomTextField(placeHolderValue = " Contact Number")
        CustomTextField(placeHolderValue = " Email")
        CustomTextField(placeHolderValue = " FirstName")
        CustomTextField(placeHolderValue = " MiddleName")
        CustomTextField(placeHolderValue = " LastName")
        CustomTextField(placeHolderValue = " PreviousLastName")
        CustomTextField(placeHolderValue = " Date of Birth")
        CustomTextField(placeHolderValue = " Gender")
        CustomTextField(placeHolderValue = " Marital Status")
        CustomTextField(placeHolderValue = " PreviousLastName")
        CustomTextField(placeHolderValue = " Religion")
        CustomTextField(placeHolderValue = " Race")
        CustomTextField(placeHolderValue = " Blood Group")
        CustomTextField(placeHolderValue = " LandLine Number")
        CustomTextField(placeHolderValue = " Age")
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


        Spacer(Modifier.height(4.dp))

        ChildTabLayoutPatientInfo()
    }
}


@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    state: String = "",
    onValueChange: (String) -> Unit = {},
    placeHolderValue: String = "Enter Name"
) {
    OutlinedTextField(
        value = state,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        placeholder = {
            Text(
                text = placeHolderValue,
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        )
    )
}