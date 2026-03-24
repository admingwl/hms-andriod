package com.example.happydocx.ui.Screens.Authentication.SignInWithPhone

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterOtpScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var otpValue by remember {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            tint = Color.Black,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)

        ) {
            Text(
                text = "Phone Verification",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = """
                Enter your otp code here.
            """.trimIndent(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            BasicTextField(
                value = otpValue,
                onValueChange = {
                    if (it.length <= 6) {
                        otpValue = it
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                decorationBox = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        repeat(6) {
                            val char = when {
                                it >= otpValue.length -> ""
                                else -> otpValue[it].toString()
                            }
                            val isFocused = otpValue.length == it
                            Text(
                                modifier = Modifier
                                    .width(40.dp)
                                    .border(
                                        if(isFocused) 2.dp
                                        else  1.dp,
                                        if(isFocused) Color.Black
                                        else Color(0xff4f61e3),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(2.dp),
                                text = char,
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            )

            FilledTonalButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4f61e3),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(
                    "Verify",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            }
        }
    }
}
