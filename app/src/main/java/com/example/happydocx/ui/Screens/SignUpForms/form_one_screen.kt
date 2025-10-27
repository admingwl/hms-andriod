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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.internal.synchronizedImpl
import kotlin.math.exp

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form_One_Screen() {

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val gradient_colors = Brush.linearGradient(listOf(
            Color(0xff586AE5), Color(0xff717FE8),
            Color(0xff7785E9)
        ))
    val scrollState = rememberScrollState()

    Scaffold(
        /*In Compose, when you use a collapsing or moving TopAppBar (like enterAlwaysScrollBehavior()),
          you must attach the scroll behavior to the scrollable content using this modifier:*/
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        // adding top bar
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Doctor Registration", fontWeight = FontWeight.ExtraBold, color = Color.White)
                        Text(
                            "Please fill in your personal and professional details", color = Color.White,
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
                .fillMaxSize().background(Color.White).verticalScroll(scrollState).padding(paddingValues = paddingValues)
        ) {

            // section 1
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 10.dp),
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
                expanded = true,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Salutation", color = Color.Black) },
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("dd-mm-yyyy", color = Color.Black) },
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
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            ExposedDropdownMenuBox(
                expanded = true,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Gender", color = Color.Black) },
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }


            // section 2
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 10.dp),
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
                expanded = true,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Department", color = Color.Black) },
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }

            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = "",
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
                    IconButton(onClick = {}) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            ExposedDropdownMenuBox(
                expanded = true,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = "",
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }

            // section 3
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 10.dp),
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
                expanded = true,
                onExpandedChange = {}
            ) {
                OutlinedTextField(
                    value = "",
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(20.dp)
                )
            }

            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                shape = RoundedCornerShape(20.dp)
            )

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End){
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
fun CustomNumberDisplay(number:Int){
    Box(
        modifier = Modifier.size(30.dp).background(shape = RoundedCornerShape(50.dp), color = Color(0xffebedfc)).padding(4.dp),
        contentAlignment = Alignment.Center
    ){
        Text("$number", fontWeight = FontWeight.ExtraBold, color = Color(0xff3c50e2))
    }
}