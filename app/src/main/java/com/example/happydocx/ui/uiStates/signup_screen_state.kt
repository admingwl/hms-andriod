package com.example.happydocx.ui.uiStates

data class SignUpEmailState(
    val enterEmail:String = ""
)

data class SignUpPasswordState(
    val enterPassword:String = ""
)

data class SignUpPasswordStateEyeToggleState(
    val onPressEyeToggle:Boolean = false
)

data class SignUpConfirmPasswordState(
    val enterConfirmPassword:String = ""
)

data class SignUpConfirmPasswordEyeToggleState(
    val onPressEyeToggleState : Boolean = false
)

data class SignUpUiState(
    val isLoading:Boolean=false,
    val isSuccess:Boolean=false,
    val errorMessage:String?=null,
    val doctorId: String? = null
)