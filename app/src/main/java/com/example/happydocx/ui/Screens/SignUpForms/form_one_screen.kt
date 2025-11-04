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
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.happydocx.ui.ViewModels.formViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form_One_Screen(
    viewModel: formViewModel = viewModel()){

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()
    val gradient_colors = Brush.linearGradient(
        listOf(
            Color(0xff586AE5), Color(0xff717FE8),
            Color(0xff7785E9)
        )
    )
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // form states
   val formInformationState = viewModel._formState.collectAsStateWithLifecycle().value

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
                expanded = formInformationState.salutationDropDownexpandedState,
                onExpandedChange = { viewModel.onSalutationDropDownPressed(!formInformationState.salutationDropDownexpandedState) },
            ) {
                OutlinedTextField(
                    value = formInformationState.salutationselectedOptions,
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
                    expanded = formInformationState.salutationDropDownexpandedState,
                    onDismissRequest = { viewModel.onSalutationDropDownPressed(!formInformationState.salutationDropDownexpandedState) },
                    containerColor = Color(0xffebedfc),
                    matchTextFieldWidth = true,
                    shape = RoundedCornerShape(30.dp)
                ) {
                    formInformationState.salutationoptionList.forEach { it ->
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
                value = formInformationState.firstName,
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
                value = formInformationState.middleName,
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
                value = formInformationState.lastName,
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
                value = formInformationState.dateOfBirth,
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
                expanded = formInformationState.genderexpandedState,
                onExpandedChange = { viewModel.onGenderDropDownPressed(!formInformationState.genderexpandedState) }
            ) {
                OutlinedTextField(
                    value = formInformationState.genderselectedOptions,
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
                    expanded = formInformationState.genderexpandedState,
                    onDismissRequest = { viewModel.onGenderDropDownPressed(!formInformationState.genderexpandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    formInformationState.genderoptionList.forEach { it ->
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
                expanded = formInformationState.departmentexpandedState,
                onExpandedChange = { viewModel.onDepartmentDropDownPressed(!formInformationState.departmentexpandedState) }
            ) {
                OutlinedTextField(
                    value = formInformationState.departmentselectedOptions,
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
                    expanded = formInformationState.departmentexpandedState,
                    onDismissRequest = { viewModel.onDepartmentDropDownPressed(!formInformationState.departmentexpandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    formInformationState.departmentoptionList.forEach { it ->
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
                value = formInformationState.contactNumber,
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
                value = formInformationState.email,
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
                value = formInformationState.dateOfJoining,
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
                expanded = formInformationState.bloodGroupexpandedState,
                onExpandedChange = { viewModel.onBloodGroupDropDownPressed(!formInformationState.bloodGroupexpandedState) }
            ) {
                OutlinedTextField(
                    value = formInformationState.bloodGroupselectedOptions,
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
                    expanded =formInformationState.bloodGroupexpandedState,
                    onDismissRequest = { viewModel.onBloodGroupDropDownPressed(!formInformationState.bloodGroupexpandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    formInformationState.bloodGroupoptionList.forEach { it ->
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
                expanded = formInformationState.addressexpandedState,
                onExpandedChange = { viewModel.onAddressDropDownPressed(!formInformationState.addressexpandedState) }
            ) {
                OutlinedTextField(
                    value = formInformationState.addressselectedOptions,
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
                    expanded = formInformationState.addressexpandedState,
                    onDismissRequest = { viewModel.onAddressDropDownPressed(!formInformationState.addressexpandedState) },
                    containerColor = Color(0xffebedfc),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    formInformationState.addressoptionList.forEach { it ->
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
                value = formInformationState.addressLineOne,
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
                value = formInformationState.addressLineTwo,
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
                value = formInformationState.city,
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
                value = formInformationState.state,
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
                value = formInformationState.district,
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
                value = formInformationState.zipCode,
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
                value = formInformationState.country,
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
                value = formInformationState.clinicLocationUrl,
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
                    onClick = {

                    },
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff4f61e3),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                        Text(
                            "Next Step",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 13.sp
                        )
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null
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

// logic to calculate the year of experience the doctor has by his year of joining
fun calculateYearsOfExperience(dateOfJoining: String): Int {
    return try {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val joiningDate = formatter.parse(dateOfJoining)
        val currentDate = Date()
        val diff = currentDate.time - (joiningDate?.time ?: 0)
        val years = diff / (1000L * 60 * 60 * 24 * 365)
        years.toInt()
    } catch (e: Exception) {
        0
    }
}