package com.example.happydocx.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel

@Composable
fun HomeScreen(
    userViewModel: ParticularUserSignInViewModel
){
    // get saved login data
    val loginData = userViewModel._loginDataResponse.collectAsStateWithLifecycle().value

    // know we have data
    val user = loginData?.user
    val token = loginData?.token
    val message = loginData?.message
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Token = $token")
        Text("message: $message")
        Text("user name = ${user?.name}")
        Text("email = ${user?.email}")
        Text("id = ${user?.id}")
        Text("companyID = ${user?.companyId}")
        Text("doctorId = ${user?.doctorId}")
        Text("role = ${user?.role}")
    }
}