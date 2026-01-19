package com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ChildTabScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens.CustomTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyScreen(modifier: Modifier = Modifier) {
    val genderFilterList = listOf(
        "Male",
        "Female",
        "Other"
    )
    val typeFilteredList = listOf(
        "KIN",
        "Emergency Contact",
        "Both"
    )
    var dropDownExpandStateGender by rememberSaveable { mutableStateOf(false) }
    var dropDownExpandStateType by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize().background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(placeHolderValue = "FirstName")
        CustomTextField(placeHolderValue = "LastName")
        CustomTextField(placeHolderValue = "Relationship")
        // here add the gender drop down
        ExposedDropdownMenuBox(
            expanded = dropDownExpandStateGender,
            onExpandedChange = {dropDownExpandStateGender = it},
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = {Text("Gender")},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownExpandStateGender)
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
                expanded = dropDownExpandStateGender,
                onDismissRequest = { dropDownExpandStateGender = false },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
                genderFilterList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            dropDownExpandStateGender = false
                        }
                    )
                }
            }
        }

        CustomTextField(placeHolderValue = "Phone Number")
        CustomTextField(placeHolderValue = "Date Of Birth")
        // dropdown
        ExposedDropdownMenuBox(
            expanded = dropDownExpandStateType,
            onExpandedChange = {dropDownExpandStateType = it},
        ) {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                readOnly = true,
                placeholder = {Text(" Select Type")},
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropDownExpandStateType)
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
                expanded = dropDownExpandStateType,
                onDismissRequest = { dropDownExpandStateType = false },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
                typeFilteredList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            dropDownExpandStateType = false
                        }
                    )
                }
            }
        }

        CustomTextField(placeHolderValue = "Comment")

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