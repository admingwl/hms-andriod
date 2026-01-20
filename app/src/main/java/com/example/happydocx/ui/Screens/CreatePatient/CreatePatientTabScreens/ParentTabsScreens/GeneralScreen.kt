package com.example.happydocx.ui.Screens.CreatePatient.CreatePatientTabScreens.ParentTabsScreens

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.CreatePatient.ChildTabLayoutPatientInfo
import com.example.happydocx.ui.ViewModels.PatientViewModel.SavePatientGeneralUi
import com.example.happydocx.ui.ViewModels.PatientViewModel.SavePatientViewModel
import com.example.happydocx.ui.ViewModels.formViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralScreen(
    modifier: Modifier = Modifier,
    viewModel: SavePatientViewModel,
    token:String
) {

    val scope = rememberCoroutineScope()
    val state = viewModel._saveGeneralState.collectAsStateWithLifecycle().value
    // get network state
    val networkSaveGeneralState = viewModel._apiState.collectAsStateWithLifecycle().value
    // date picker states.
    var openDatePicker_DateOfBirth = remember{mutableStateOf(false)}
    val datePickerState_DateOfBirth = rememberDatePickerState()
    val context = LocalContext.current

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

        CustomTextField(
            state = state.contactNumber,
            onValueChange = { viewModel.onContactNumberChanged(it) },
            placeHolderValue = " Contact Number")
        CustomTextField(
            state = state.email,
            onValueChange = { viewModel.onEmailChanged(it) },
            placeHolderValue = " Email"
        )
        CustomTextField(
            state = state.firstName,
            onValueChange = { viewModel.onFirstNameChanged(it) },
            placeHolderValue = " FirstName"
        )
        CustomTextField(
            state = state.middleName,
            onValueChange = { viewModel.onMiddleNameChanged(it) },
            placeHolderValue = " MiddleName"
        )
        CustomTextField(
            state = state.lastName,
            onValueChange = { viewModel.onLastNameChanged(it) },
            placeHolderValue = " LastName"
        )
        CustomTextField(
            state = state.previousLastName,
            onValueChange = { viewModel.onPreviousLastNameChanged(it) },
            placeHolderValue = " PreviousLastName"
        )
        // date picker for dateOfBirth
        OutlinedTextField(
            value = state.dateOfBirth,
            onValueChange = { },
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            placeholder = {
                Text(
                    text = "date of birth",
                    color = Color.Black
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {openDatePicker_DateOfBirth.value = !openDatePicker_DateOfBirth.value}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.calendar_days),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = modifier.size(20.dp)
                    )
                }
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
        // open date picker for the date of birth
        if (openDatePicker_DateOfBirth.value) {
            DatePickerDialog(
                shape = RoundedCornerShape(30.dp),
                colors = DatePickerDefaults.colors(
                    // add color to date picker dialog
                    containerColor = Color(0xffebedfc)
                ),
                onDismissRequest = { openDatePicker_DateOfBirth.value = false },
                confirmButton = {
                    TextButton(onClick = {
                        openDatePicker_DateOfBirth.value = false
                        datePickerState_DateOfBirth.selectedDateMillis?.let { millis ->
                            val formatted = SimpleDateFormat("MM-dd-yyyy", Locale.getDefault())
                                .format(Date(millis))
                            viewModel.onDateOfBirthChanged(formatted)
                        }
                    }) { Text("OK", color = Color.Black) }
                },
                dismissButton = {
                    TextButton(onClick = { openDatePicker_DateOfBirth.value = false }) {
                        Text("Cancel", color = Color.Black)
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState_DateOfBirth,
                    colors = DatePickerDefaults.colors(
                        containerColor = Color(0xffebedfc),
                        dayContentColor = Color.Black,
                        titleContentColor = Color.Black,
                        weekdayContentColor = Color.Black,
                        headlineContentColor = Color.Black,
                        navigationContentColor = Color.Black,
                        subheadContentColor = Color.Black,
                        dateTextFieldColors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White
                        )
                    )
                )
            }
        }
        //drop down for gender.
        ExposedDropdownMenuBox(
            expanded = state.isGenderExpanded,
            onExpandedChange = { viewModel.onGenderExpandedStateChange(it) },
        ) {
            OutlinedTextField(
                value = state.genderState,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Gender",color = Color.Black) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isGenderExpanded)
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
                expanded = state.isGenderExpanded,
                onDismissRequest = { viewModel.onGenderExpandedStateChange(false) },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
                viewModel.genderList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onGenderStateChanged(it)
                            viewModel.onGenderExpandedStateChange(false)
                        }
                    )
                }
            }
        }
        // marital status
        ExposedDropdownMenuBox(
            expanded = state.isMaritalExpanded,
            onExpandedChange = { viewModel.onMaritalExpandStateChanged(it) },
        ) {
            OutlinedTextField(
                value = state.maritalState,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Marital Status",color = Color.Black) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = state.isMaritalExpanded)
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
                expanded = state.isMaritalExpanded,
                onDismissRequest = { viewModel.onMaritalExpandStateChanged(false) },
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(30.dp)
            ) {
                // here comes the list of the suggestions
                viewModel.MaritalList.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onMaritalStateChanged(it)
                            viewModel.onMaritalExpandStateChanged(false)
                        }
                    )
                }
            }
        }
        CustomTextField(
            state = state.religion,
            onValueChange = { viewModel.onReligionChanged(it) },
            placeHolderValue = " Religion"
        )
        CustomTextField(
            state = state.race,
            onValueChange = { viewModel.onRaceChanged(it) },
            placeHolderValue = " Race"
        )
        CustomTextField(
            state = state.bloodGroup,
            onValueChange = { viewModel.onBloodGroupChanged(it) },
            placeHolderValue = " Blood Group"
        )
        CustomTextField(
            state = state.landLineNumber,
            onValueChange = { viewModel.onLandlineNumberChanged(it) },
            placeHolderValue = " LandLine Number"
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                scope.launch {
//                    // validation
//                    if(state.contactNumber.isEmpty() ||
//                        state.email.isEmpty()||
//                        state.firstName.isEmpty()||
//                        state.middleName.isEmpty()||
//                        state.lastName.isEmpty()||
//                        state.previousLastName.isEmpty()||
//                        state.dateOfBirth.isEmpty()||
//                        state.genderState.isEmpty()||
//                        state.maritalState.isEmpty()||
//                        state.religion.isEmpty()||
//                        state.race.isEmpty()||
//                        state.bloodGroup.isEmpty()||
//                        state.landLineNumber.isEmpty()
//                        ){
//                        Toast.makeText(
//                            context,
//                            "Please fill all fields first",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }else{
                        viewModel.savePatientGeneralInfo(token = token)
                        viewModel.clearTextFiled()
                        viewModel.resetState()
                }
            },
            modifier = modifier
                .padding(18.dp)
                .align(Alignment.Start),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff16a34a),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            if(networkSaveGeneralState is SavePatientGeneralUi.Loading){
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                if ( networkSaveGeneralState is SavePatientGeneralUi.Loading) "Uploading..." else "Saved",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp),
                fontSize = 13.sp
            )

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
    placeHolderValue: String = "Enter Name",

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
                color = Color.Black
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