package com.example.happydocx.ui.uiStates

data class EmailState(
  val enterEmail:String = ""
)

data class PasswordState(
  val enterPassword:String = ""
)
data class EyeToggleState(
  val isEnable:Boolean = false
)

data class LoginUiState(
  val isLoading:Boolean = false,
  val isSuccess:Boolean = false,
  val errorMessage:String?=null
)