package com.example.happydocx.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.happydocx.ui.ViewModels.ParticularUserSignUpViewModel

@Composable
fun SignUpResponse(
    particularUserSignUpViewModel: ParticularUserSignUpViewModel
){
    val signUpResponse = particularUserSignUpViewModel._signUpDataResponse.collectAsStateWithLifecycle().value

    val user = signUpResponse?.user
    val message = signUpResponse?.message
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text("message = $message")
        Text("id = ${user?.id}")

        Text("message = $user?.email")
        Text("id = ${user?.role}")

        Text("message = ${user?.doctorId}")
        Text("id = ${user?.companyId}")

    }
}