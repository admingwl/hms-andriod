package com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ChildTabScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens.CustomTextField
import com.example.happydocx.ui.Screens.StartConsulting.ChipInputTextField

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var dropDownExpandState by remember { mutableStateOf(false) }
        val filteredList = listOf(
            "Temporary",
            "Permanent"
        )
        ExposedDropdownMenuBox(
            expanded = dropDownExpandState,
            onExpandedChange = {dropDownExpandState = it},
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = {Text("Address Type")},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownExpandState)
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
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
                    .padding(12.dp)
            )

            ExposedDropdownMenu(
                expanded = dropDownExpandState,
                onDismissRequest = { dropDownExpandState = false },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
                filteredList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            dropDownExpandState = false
                        }
                    )
                }
            }
        }
        CustomTextField(placeHolderValue = "Address Line 1")
        CustomTextField(placeHolderValue = "Address Line 2")
        CustomTextField(placeHolderValue = "City")
        CustomTextField(placeHolderValue = "State")
        CustomTextField(placeHolderValue = "District")
        CustomTextField(placeHolderValue = "Ward Number")
        CustomTextField(placeHolderValue = "Zip Code")
        CustomTextField(placeHolderValue = "Country")

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {},
            modifier = modifier
                .padding(18.dp)
                .align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff359EFF)
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text("SAVE", color = Color.White, modifier = modifier.padding(horizontal = 30.dp))
        }


    }
}