package com.example.happydocx.ui.Screens.Authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePasswordScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    var eyeToggleState by rememberSaveable { mutableStateOf(false) }
    var eyeToggleStateTwo by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Update Password", fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.White
                ),
                modifier = modifier.background(gradient_colors),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedContainerColor = Color(0xfff6f6f6),
                        unfocusedContainerColor = Color(0xfff6f6f6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("enter new password") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { eyeToggleState = !eyeToggleState }
                        ) {
                            Icon(
                                painter = if (eyeToggleState) painterResource(R.drawable.baseline_visibility_24) else painterResource(
                                    R.drawable.baseline_visibility_off_24
                                ),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = modifier.size(20.dp)
                            )
                        }
                    }
                )
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        cursorColor = Color.Black,
                        focusedContainerColor = Color(0xfff6f6f6),
                        unfocusedContainerColor = Color(0xfff6f6f6),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    placeholder = { Text("confirm new password") },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = { eyeToggleStateTwo = !eyeToggleStateTwo }
                        ) {
                            Icon(
                                painter = if (eyeToggleStateTwo) painterResource(R.drawable.baseline_visibility_24) else painterResource(
                                    R.drawable.baseline_visibility_off_24
                                ),
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = modifier.size(20.dp)
                            )
                        }
                    }
                )
                // update password button
            }
            FilledTonalButton(
                onClick = { navController.navigate("Login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4f61e3),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "update password",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun UpdatePasswordPreview() {
    val navController = rememberNavController()
    UpdatePasswordScreen(navController = navController)
}
