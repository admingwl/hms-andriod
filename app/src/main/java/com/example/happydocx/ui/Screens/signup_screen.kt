package com.example.happydocx.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.signUpScreenViewModel



@Composable
fun SignUpPage(viewModel: signUpScreenViewModel = viewModel(),navController: NavController){

    val emailState = viewModel._enterEmail.collectAsStateWithLifecycle().value
    val passwordState = viewModel._enterPassword.collectAsStateWithLifecycle().value
    val passwordEyeToggle = viewModel._enterPasswordEyeToggle.collectAsStateWithLifecycle().value
    val confirmPasswordState = viewModel._confirmPassword.collectAsStateWithLifecycle().value
    val confirmPasswordEyeToggleState = viewModel._confirmPasswordEyeToggle.collectAsStateWithLifecycle().value
    val signUpUiState = viewModel._signUpUiState.collectAsStateWithLifecycle().value


    // handle the signUp success
    // handle the login success
    LaunchedEffect(signUpUiState.isSuccess) {
        if(signUpUiState.isSuccess){
            navController.navigate("SignUpResponse") {
                // Clear the back stack up to and including the login screen
                popUpTo("SignUp") { inclusive = true }
                // Ensure only one instance of Home exists
                launchSingleTop = true
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.fulllogo),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 100.dp)
        )

Spacer(Modifier.height(20.dp))
        Text(
            text = "Create your Doctor Account",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Text(
            text = "Get your Happydocx account now",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Spacer(Modifier.height(30.dp))
        OutlinedTextField(
            value = emailState.enterEmail,
            onValueChange = {it->viewModel.onEmailChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
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
            singleLine = true,
            placeholder = { Text("Enter Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = Color.Black) }
        )
        Spacer(Modifier.padding(vertical = 4.dp))
        OutlinedTextField(
            value = passwordState.enterPassword,
            onValueChange = { it-> viewModel.onPasswordChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
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
            singleLine = true,
            placeholder = { Text("Enter Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Black) },
            trailingIcon = {
                IconButton(onClick = { viewModel.onEnterPasswordEyeTogglePressed(!passwordEyeToggle.onPressEyeToggle) }) {
                    Icon(
                        painter = painterResource(if(passwordEyeToggle.onPressEyeToggle){R.drawable.baseline_visibility_24}else{R.drawable.baseline_visibility_off_24}),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
            visualTransformation = if(passwordEyeToggle.onPressEyeToggle) VisualTransformation.None else PasswordVisualTransformation()
        )
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            value = confirmPasswordState.enterConfirmPassword,
            onValueChange = {it->viewModel.onConfirmPasswordStateChange(it)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
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
            singleLine = true,
            placeholder = { Text("Confirm password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color.Black) },
            trailingIcon = {
                    IconButton(onClick = { viewModel.onConfirmPasswordEyeTogglePressed(!confirmPasswordEyeToggleState.onPressEyeToggleState) }) {
                        Icon(
                            painter = painterResource(if(confirmPasswordEyeToggleState.onPressEyeToggleState){R.drawable.baseline_visibility_24}else{R.drawable.baseline_visibility_off_24}),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
            },
            visualTransformation = if(confirmPasswordEyeToggleState.onPressEyeToggleState) VisualTransformation.None else PasswordVisualTransformation()
        )

        Spacer(Modifier.height(20.dp))

        if(signUpUiState.errorMessage != null){
            Text(
                text = signUpUiState.errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(Modifier.height(8.dp))
        }

        FilledTonalButton(
            onClick = {viewModel.singUpButtonClicked()},
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff4f61e3),
                contentColor = Color.White
            ),
            enabled = !signUpUiState.isLoading, // disable while loading
            shape = RoundedCornerShape(8.dp)
        ) {
            if(signUpUiState.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            }else {
                Text("Sign Up", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
            }
        }
        Spacer(Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(
                text = "Already have an account ? "
            )
            Text(
                text = "Sign In",
                modifier =  Modifier.clickable{navController.navigate("Login")},
                color = Color(0xff4f61e3)
            )
        }
    }
}