package com.example.happydocx.ui.ViewModels

import androidx.lifecycle.ViewModel
import com.example.happydocx.Data.Model.SignUpModel.SignUpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//Shared viewModel for SignUp User
class ParticularUserSignUpViewModel : ViewModel(){

    // state
    private var SignUpDataResponse = MutableStateFlow<SignUpResponse?>(null)
    val _signUpDataResponse = SignUpDataResponse.asStateFlow()

    // save Sign Up data
    fun SaveSignUpResponse(signUpResponse: SignUpResponse){
        SignUpDataResponse.value = signUpResponse
    }
}