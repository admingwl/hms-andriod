package com.example.happydocx.ui.Screens

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.LoginScreenViewModel
import com.example.happydocx.ui.ViewModels.LoginViewModelFactory
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel


/**
 * This code flow
 * User clicks Sign In → onLoginClicked() is called in ViewModel
 * ViewModel validates → Checks if email and password are not empty
 * ViewModel calls Repository → Repository uses Retrofit to make API call
 * API responds → Repository returns success or failure
 * ViewModel updates UI state → LoginPage shows loading, success, or error
 * On success → Navigate to Home screen
 */

@Composable
fun LoginPage(
    viewModel: LoginScreenViewModel = viewModel(
        factory = LoginViewModelFactory(LocalContext.current)
    ),
    userViewModel: ParticularUserSignInViewModel,
    navController: NavController
) {


    val context = LocalContext.current
    val emailState = viewModel._emailState.collectAsStateWithLifecycle().value
    val passwordState = viewModel._passwordState.collectAsStateWithLifecycle().value
    val eyeToggleState = viewModel._eyeToggleState.collectAsStateWithLifecycle().value
    val loginUiState = viewModel._loginUiState.collectAsStateWithLifecycle().value
    val savedToken = TokenManager(context).getToken()


    // Check if user is already logged in on first launch
    LaunchedEffect(Unit) {
        if (viewModel.isUserLoggedIn()) {
            navController.navigate("AppointmentsScreen/${savedToken}") {
                popUpTo("Login") { inclusive = true }
                launchSingleTop = true
            }
        }
    }
    // handle the login success
    LaunchedEffect(loginUiState.isSuccess) {
        if(loginUiState.isSuccess){
            Log.d("LOGIN_DEBUG", "Login successful, token: $savedToken")
            if (savedToken != null) {
                // Navigate with token as argument
                navController.navigate("AppointmentsScreen/${savedToken}") {
                    // Clear the back stack up to and including the login screen
                    popUpTo("Login") { inclusive = true }
                    // Ensure only one instance exists
                    launchSingleTop = true
                }
            } else {
                Log.e("LOGIN_DEBUG", "Login successful but token is null!")
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
            text = "Sign In To HappyDocx",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 30.sp
        )
        Spacer(Modifier.height(30.dp))
        OutlinedTextField(
            value = emailState.enterEmail,
            onValueChange = {newEmailState->viewModel.onEmailChanged(newEmail = newEmailState)},
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
            placeholder = { Text("Enter Email") },
            leadingIcon = {

                Icon(
                    Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.Black
                )
            }
        )

        Spacer(Modifier.padding(vertical = 4.dp))

        OutlinedTextField(
            value = passwordState.enterPassword,
            onValueChange = {newPassword->viewModel.onPasswordChange(newPassword = newPassword)},
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
            placeholder = { Text("Enter Password") },
            leadingIcon = {
                Icon(
                    Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.Black
                )
            },
            trailingIcon = {
                IconButton(onClick = { viewModel.onEyeTogglePressed(!eyeToggleState.isEnable) }) {
                    Icon(
                        painter = painterResource(
                            if (eyeToggleState.isEnable) {
                               R.drawable.baseline_visibility_24
                            } else {
                                R.drawable.baseline_visibility_off_24
                            }
                        ),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
              visualTransformation = if(eyeToggleState.isEnable) VisualTransformation.None else PasswordVisualTransformation()
        )
        Row(modifier = Modifier.fillMaxWidth().padding(end = 8.dp), horizontalArrangement = Arrangement.End) {
            Text("Forgot Password ?",modifier = Modifier.clickable{})
        }
        Spacer(Modifier.height(20.dp))

        // for showing the error ........
//        if (loginUiState.errorMessage != null) {
//            Text(
//                text = loginUiState.errorMessage,
//                color = Color.Red,
//                fontSize = 12.sp,
//                modifier = Modifier.padding(horizontal = 10.dp)
//            )
//            Spacer(Modifier.height(8.dp))

        FilledTonalButton(
            onClick = {viewModel.loginClicked(userViewModel = userViewModel)},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff4f61e3),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = !loginUiState.isLoading // disable while loading
        ) {
            if(loginUiState.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            }else{
                Text(
                    "Sign In",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            }
        }
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Or sign in with ",
            fontSize = 13.sp
        )
        Spacer(Modifier.height(20.dp))



        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            SocialMediaAccounts(socialMediaImage = R.drawable.google)
            Spacer(Modifier.width(8.dp))
            SocialMediaAccounts(socialMediaImage = R.drawable.facebook)
            Spacer(Modifier.width(8.dp))
            SocialMediaAccounts(socialMediaImage = R.drawable.twitter)

        }
        Spacer(Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = "Don't have account ? "
            )
            Text(
                text = "Sign Up",
                modifier = Modifier.clickable {navController.navigate("SignUp")},
                color = Color(0xff4f61e3)
            )
        }
    }
}


@Composable
fun SocialMediaAccounts(socialMediaImage: Int) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .size(60.dp)
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.size(60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = socialMediaImage),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                    tint = Color.Unspecified,
                )
            }
        }
    }
}