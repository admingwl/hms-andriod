package com.example.happydocx.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Repository.AuthRepository.SignUpRepository.SignUpRepository
import com.example.happydocx.ui.uiStates.SignUpConfirmPasswordEyeToggleState
import com.example.happydocx.ui.uiStates.SignUpConfirmPasswordState
import com.example.happydocx.ui.uiStates.SignUpEmailState
import com.example.happydocx.ui.uiStates.SignUpPasswordState
import com.example.happydocx.ui.uiStates.SignUpPasswordStateEyeToggleState
import com.example.happydocx.ui.uiStates.SignUpUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class signUpScreenViewModel : ViewModel() {


    // get first the signUp repository
    val signUpRespository = SignUpRepository()  // good practice to use hilt here i add that later

    //  email state
    private var enterEmail = MutableStateFlow(SignUpEmailState())
    val _enterEmail = enterEmail.asStateFlow()

    // enter password
    private var enterPassword = MutableStateFlow(SignUpPasswordState())
    val _enterPassword = enterPassword.asStateFlow()

    // enter password eye toggle
    private var enterPasswordEyeToggle = MutableStateFlow(SignUpPasswordStateEyeToggleState())
    val _enterPasswordEyeToggle = enterPasswordEyeToggle.asStateFlow()

    // confirm password
    private var confirmPassword = MutableStateFlow(SignUpConfirmPasswordState())
    val _confirmPassword = confirmPassword.asStateFlow()

    // confirm password eye toggle
    private var confirmPasswordEyeToggle = MutableStateFlow(SignUpConfirmPasswordEyeToggleState())
    val _confirmPasswordEyeToggle = confirmPasswordEyeToggle.asStateFlow()


    // here take the singUp screen ui state
    private var signUpUiState = MutableStateFlow(SignUpUiState())
    val _signUpUiState = signUpUiState.asStateFlow()

    // on email state change
    fun onEmailChanged(newEmail:String){
        enterEmail.update { enterEmail->
            enterEmail.copy(
                enterEmail = newEmail
            )
        }
    }

    // on password change
    fun onPasswordChanged(newPassword:String){
        enterPassword.update {enterPassword ->
            enterPassword.copy(
                enterPassword = newPassword
            )
        }
    }

    // on password eye toggle press
    fun onEnterPasswordEyeTogglePressed(state:Boolean){
        enterPasswordEyeToggle.update { eyeToggle->
            eyeToggle.copy(
                onPressEyeToggle = state
            )
        }
    }

    // on confirm password state change
    fun onConfirmPasswordStateChange(confirmPasswords:String){
       confirmPassword.update { confirmPassword->
           confirmPassword.copy(
               enterConfirmPassword = confirmPasswords
           )
       }
    }

    // on confirm password eye toggle state
    fun onConfirmPasswordEyeTogglePressed(state:Boolean){
        confirmPasswordEyeToggle.update { it->
            it.copy(
                onPressEyeToggleState = state
            )
        }
    }

    fun singUpButtonClicked(){
        val email = enterEmail.value.enterEmail
        val password = enterPassword.value.enterPassword
        val confirmPassword = confirmPassword.value.enterConfirmPassword

        // Basic validation
        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()||password!=confirmPassword){
           signUpUiState.update { uiState->
               uiState.copy(
                   errorMessage = "Please Enter Full & Right Credentials"
               )
           }
            return
        }

        // here we write the actual signup logic
        viewModelScope.launch {
            signUpUiState.update { signUpUiState->
                signUpUiState.copy(
                    isLoading = true
                )
            }
            val result = signUpRespository.signUp(
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )

         result.onSuccess { signUpResponse ->

             signUpUiState.update { signUpUiState ->
                 signUpUiState.copy(
                     isLoading = false,
                     isSuccess = true
                 )
             }
         }

             result.onFailure { exception->
                 signUpUiState.update { signUpUiState->
                     signUpUiState.copy(
                         isLoading = false,
                         errorMessage =  exception.message ?: "SignUp failed"
                     )
                 }
             }
        }
    }
}