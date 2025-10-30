package com.example.happydocx.ui.Screens.SignUpForms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.happydocx.ui.ViewModels.SingUp_From1_ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form_One_Screen(viewModel: SingUp_From1_ViewModel = viewModel()) {

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val gradient_colors = Brush.linearGradient(
        listOf(
            Color(0xff586AE5), Color(0xff717FE8),
            Color(0xff7785E9)
        )
    )
    val scrollState = rememberScrollState()

    // states
    val personalInformationState =
        viewModel._personalInformationState.collectAsStateWithLifecycle().value
    val professionalDetailState =
        viewModel._professionalDetailState.collectAsStateWithLifecycle().value
    val contactInformationState =
        viewModel._contactInformationState.collectAsStateWithLifecycle().value
    val salutationState =
        viewModel._salutationState.collectAsStateWithLifecycle().value
    val genderState =
        viewModel._genderState.collectAsStateWithLifecycle().value
    val departmentState =
        viewModel._departmentState.collectAsStateWithLifecycle().value
    val bloodGroupState =
        viewModel._bloodGroupState.collectAsStateWithLifecycle().value
    val addressState =
        viewModel._addressState.collectAsStateWithLifecycle().value

    // date picker state
    val openDatePickerState_DateOfBirth = remember { mutableStateOf(false) }
    val datePickerState_DateOfBirth = rememberDatePickerState()


    // date picker for date of joining
    val openDatePicker_DateOfJoining = remember{mutableStateOf(false)}
    val datePickerState_DateOfJoining = rememberDatePickerState()



    Scaffold(
        /*In Compose, when you use a collapsing or moving TopAppBar (like enterAlwaysScrollBehavior()),
          you must attach the scroll behavior to the scrollable content using this modifier:*/
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        // adding top bar
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Doctor Registration",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            "Please fill in your personal and professional details",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent // keep the same gradient
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradient_colors),
                scrollBehavior = scrollBehaviour
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(paddingValues = paddingValues)
        ) {

            // section 1
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomNumberDisplay(1)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Personal Information",
                    fontSize = 18.sp,
                    color = Color(0xff4f61e3),
                    fontWeight = FontWeight.Bold
                )
            }

            ExposedDropdownMenuBox(
                expanded = salutationState.expandedState,
                onExpandedChange = { viewModel.onSalutationDropDownPressed(!salutationState.expandedState) },
            ) {
                OutlinedTextField(
                    value = salutationState.selectedOptions,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Salutation", color = Color.Black) },
                    trailingIcon = {

                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedContainerColor = Color(0xfff6f6f6),
                        unfocusedContainerColor = Color(0xfff6f6f6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
                ExposedDropdownMenu(
                    expanded = salutationState.expandedState,
                    onDismissRequest = { viewModel.onSalutationDropDownPressed(!salutationState.expandedState) },
                    containerColor = Color(0xffebedfc),
                    matchTextFieldWidth = true,
                    shape = RoundedCornerShape(30.dp)
                ) {
                    salutationState.optionList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                viewModel.onSalutationItemClicked(it)
                            }
                        )
                    }
                }

            }
            OutlinedTextField(
                value = personalInformationState.firstName,
                onValueChange = { viewModel.onFirstNameChanged(it) },
                placeholder = { Text("First Name", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = personalInformationState.middleName,
                onValueChange = { it -> viewModel.onMiddleNameChanged(it) },
                placeholder = { Text("Middle Name", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = personalInformationState.lastName,
                onValueChange = { viewModel.onLastNameChanged(it) },
                placeholder = { Text("Last Name", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = personalInformationState.dateOfBirth,
                onValueChange = {},
                placeholder = { Text("date of birth ... dd-mm-yyyy", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { openDatePickerState_DateOfBirth.value = true }) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            if (openDatePickerState_DateOfBirth.value) {
                DatePickerDialog(
                    shape = RoundedCornerShape(30.dp),
                    colors = DatePickerDefaults.colors(
                        // add color to date picker dialog
                        containerColor = Color(0xffebedfc)
                    ),
                    onDismissRequest = { openDatePickerState_DateOfBirth.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            openDatePickerState_DateOfBirth.value = false
                            datePickerState_DateOfBirth.selectedDateMillis?.let { millis ->
                                val formatted = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    .format(Date(millis))
                                viewModel.onDateOfBirthChange(formatted)
                            }
                        }) { Text("OK", color = Color.Black) }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDatePickerState_DateOfBirth.value = false }) {
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

            ExposedDropdownMenuBox(
                expanded = genderState.expandedState,
                onExpandedChange = { viewModel.onGenderDropDownPressed(!genderState.expandedState) }
            ) {
                OutlinedTextField(
                    value = genderState.selectedOptions,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Gender", color = Color.Black) },
                    trailingIcon = {

                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedContainerColor = Color(0xfff6f6f6),
                        unfocusedContainerColor = Color(0xfff6f6f6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
                ExposedDropdownMenu(
                    expanded = genderState.expandedState,
                    onDismissRequest = { viewModel.onGenderDropDownPressed(!genderState.expandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    genderState.optionList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                viewModel.onGenderItemClicked(it)
                            }
                        )
                    }
                }
            }


            // section 2
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomNumberDisplay(2)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Professional Details",
                    fontSize = 18.sp,
                    color = Color(0xff4f61e3),
                    fontWeight = FontWeight.Bold
                )
            }

            ExposedDropdownMenuBox(
                expanded = departmentState.expandedState,
                onExpandedChange = { viewModel.onDepartmentDropDownPressed(!departmentState.expandedState) }
            ) {
                OutlinedTextField(
                    value = departmentState.selectedOptions,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Department", color = Color.Black) },
                    trailingIcon = {

                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedContainerColor = Color(0xfff6f6f6),
                        unfocusedContainerColor = Color(0xfff6f6f6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
                ExposedDropdownMenu(
                    expanded = departmentState.expandedState,
                    onDismissRequest = { viewModel.onDepartmentDropDownPressed(!departmentState.expandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    departmentState.optionList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                viewModel.onDepartmentItemClicked(it)
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = professionalDetailState.contactNumber,
                onValueChange = { viewModel.onContactNumberChanged(it) },
                placeholder = { Text("Contact Number", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = professionalDetailState.email,
                onValueChange = { it -> viewModel.onEmailChanged(it) },
                placeholder = { Text("Email", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )



            OutlinedTextField(
                value = professionalDetailState.dateOfJoining,
                onValueChange = {},
                placeholder = { Text("date of joining .. dd-mm-yyyy", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {openDatePicker_DateOfJoining.value =true}) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            if (openDatePicker_DateOfJoining.value) {
                DatePickerDialog(
                    shape = RoundedCornerShape(30.dp),
                    colors = DatePickerDefaults.colors(
                        // add color to date picker dialog
                        containerColor = Color(0xffebedfc)
                    ),
                    onDismissRequest = { openDatePicker_DateOfJoining.value = false },
                    confirmButton = {
                        TextButton(onClick = {
                            openDatePicker_DateOfJoining.value = false
                            datePickerState_DateOfJoining.selectedDateMillis?.let { millis ->
                                val formatted = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    .format(Date(millis))
                                viewModel.onDateOfJoiningChange(formatted)
                            }
                        }) { Text("OK", color = Color.Black) }
                    },
                    dismissButton = {
                        TextButton(onClick = { openDatePicker_DateOfJoining.value = false }) {
                            Text("Cancel", color = Color.Black)
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerState_DateOfJoining,
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


            ExposedDropdownMenuBox(
                expanded = bloodGroupState.expandedState,
                onExpandedChange = { viewModel.onBloodGroupDropDownPressed(!bloodGroupState.expandedState) }
            ) {
                OutlinedTextField(
                    value = bloodGroupState.selectedOptions,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Blood Group", color = Color.Black) },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedContainerColor = Color(0xfff6f6f6),
                        unfocusedContainerColor = Color(0xfff6f6f6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
                ExposedDropdownMenu(
                    expanded = bloodGroupState.expandedState,
                    onDismissRequest = { viewModel.onBloodGroupDropDownPressed(!bloodGroupState.expandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    bloodGroupState.optionList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                viewModel.onBloodGroupItemClicked(it)
                            }
                        )
                    }
                }
            }

            // section 3
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomNumberDisplay(3)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Contact Information",
                    fontSize = 18.sp,
                    color = Color(0xff4f61e3),
                    fontWeight = FontWeight.Bold
                )
            }


            ExposedDropdownMenuBox(
                expanded = addressState.expandedState,
                onExpandedChange = { viewModel.onAddressDropDownPressed(!addressState.expandedState) }
            ) {
                OutlinedTextField(
                    value = addressState.selectedOptions,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Address Type", color = Color.Black) },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedContainerColor = Color(0xfff6f6f6),
                        unfocusedContainerColor = Color(0xfff6f6f6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
                ExposedDropdownMenu(
                    expanded = addressState.expandedState,
                    onDismissRequest = { viewModel.onAddressDropDownPressed(!addressState.expandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    addressState.optionList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                viewModel.onAddressItemClicked(it)
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = contactInformationState.addressLineOne,
                onValueChange = { viewModel.onAddressLineOneChanged(it) },
                placeholder = { Text("Address Line 1", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = contactInformationState.addressLineTwo,
                onValueChange = { viewModel.onAddressLineTwoChanged(it) },
                placeholder = { Text("Address Line 2", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = contactInformationState.city,
                onValueChange = { viewModel.city(it) },
                placeholder = { Text("City", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = contactInformationState.state,
                onValueChange = { viewModel.state(it) },
                placeholder = { Text("State", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = contactInformationState.district,
                onValueChange = { viewModel.district(it) },
                placeholder = { Text("District", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = contactInformationState.zipCode,
                onValueChange = { viewModel.zipCode(it) },
                placeholder = { Text("Zip Code", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = contactInformationState.country,
                onValueChange = { viewModel.country(it) },
                placeholder = { Text("Country", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = contactInformationState.clinicLocationUrl,
                onValueChange = { viewModel.clinicLocationUrl(it) },
                placeholder = { Text("Clinic Location URL..", color = Color.Black) },
                colors = TextFieldDefaults.colors(
                    cursorColor = Color.Black,
                    focusedContainerColor = Color(0xfff6f6f6),
                    unfocusedContainerColor = Color(0xfff6f6f6),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                FilledTonalButton(
                    onClick = {},
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff4f61e3),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Next Step",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 13.sp
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null
                    )
                }
            }
        }
    }
}


@Composable
fun CustomNumberDisplay(number: Int) {
    Box(
        modifier = Modifier
            .size(30.dp)
            .background(shape = RoundedCornerShape(50.dp), color = Color(0xffebedfc))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("$number", fontWeight = FontWeight.ExtraBold, color = Color(0xff3c50e2))
    }
}