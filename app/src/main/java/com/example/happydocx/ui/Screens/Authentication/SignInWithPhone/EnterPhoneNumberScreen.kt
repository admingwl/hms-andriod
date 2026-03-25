package com.example.happydocx.ui.Screens.Authentication.SignInWithPhone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel.EnterPhoneNumberViewModel
import com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel.GettingOtpUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterPhoneNumberScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    enterPhoneNumberViewModel: EnterPhoneNumberViewModel
) {
    val enterPhoneNumberUiState = enterPhoneNumberViewModel._enterPhoneNumber.collectAsStateWithLifecycle().value
    val scope = rememberCoroutineScope()
    // network state of viewModel
    val enterPhoneNumberUiStateNetwork = enterPhoneNumberViewModel._gettingOtpNetworkState.collectAsStateWithLifecycle().value

    LaunchedEffect(enterPhoneNumberUiStateNetwork) {
        when(enterPhoneNumberUiStateNetwork) {
            is GettingOtpUiState.Success -> {
                navController.navigate("enterOtpScreen")
            }
            else -> {}
        }

    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
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
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color.White)
        ) {

            Text(
                text = "Enter Your Phone Number",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = """
                we have sent you a SMS with a code to
                your phone number.
            """.trimIndent(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = enterPhoneNumberUiState.phoneNumber,
                onValueChange = {enterPhoneNumberViewModel.onPhoneNumberChanged(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                placeholder = { Text("Enter phone number") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.phone_24),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                }
            )

            FilledTonalButton(
                onClick = {
                    if(enterPhoneNumberUiState.phoneNumber.isEmpty()){
                        return@FilledTonalButton
                    }else {
                        enterPhoneNumberViewModel.onGetOtpClicked(
                            phone = enterPhoneNumberUiState.phoneNumber
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xff4f61e3),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                if(enterPhoneNumberUiStateNetwork is GettingOtpUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                }else{
                    Text(
                        "Get OTP",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )

                }

            }
        }
    }
}