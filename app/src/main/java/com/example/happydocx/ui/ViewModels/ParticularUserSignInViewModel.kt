package com.example.happydocx.ui.ViewModels

import androidx.lifecycle.ViewModel
import com.example.happydocx.Data.Model.LoginModel.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// shared viewModel
//Stores the API response (LoginResponse)
//Any screen can read this data
//Data stays even when you navigate to different screens

class ParticularUserSignInViewModel : ViewModel() {

    // this store the entire login response
    private var loginDataResponse = MutableStateFlow<LoginResponse?>(null)
    val _loginDataResponse = loginDataResponse.asStateFlow()

    // function to save login data (called after successful login)
    fun saveLoginData(response: LoginResponse){
        loginDataResponse.value = response
    }

}