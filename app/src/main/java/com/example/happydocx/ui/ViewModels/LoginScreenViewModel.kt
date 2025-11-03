package com.example.happydocx.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Repository.AuthRepository.LoginRepository.loginRepository
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.ui.uiStates.EmailState
import com.example.happydocx.ui.uiStates.EyeToggleState
import com.example.happydocx.ui.uiStates.LoginUiState
import com.example.happydocx.ui.uiStates.PasswordState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
 private val tokenManager: TokenManager
) : ViewModel() {


    // get repository object
    private val repository = loginRepository()
    // write email state
    private var emailState = MutableStateFlow(EmailState())
    val _emailState = emailState.asStateFlow()

    // write password state
    private var passwordState = MutableStateFlow(PasswordState())
    val _passwordState = passwordState.asStateFlow()

    // eyeToggle state
    private var eyeToggleState = MutableStateFlow(EyeToggleState())
    val _eyeToggleState = eyeToggleState.asStateFlow()


    // Login Ui state
    private var loginUiState = MutableStateFlow(LoginUiState())
    val _loginUiState = loginUiState.asStateFlow()

    // check if user is already logged in or not
    fun isUserLoggedIn(): Boolean{
        return tokenManager.isLoggedIn()
    }

    // on Email state change
    fun onEmailChanged(newEmail: String) {
        emailState.update { emailState ->
            emailState.copy(
                enterEmail = newEmail
            )
        }
    }

    // on Password state change
    fun onPasswordChange(newPassword: String) {
        passwordState.update { passwordState ->
            passwordState.copy(
                enterPassword = newPassword
            )
        }
    }

    // on eye toggle pressed
    fun onEyeTogglePressed(state: Boolean) {
        eyeToggleState.update { eyeToggleState ->
            eyeToggleState.copy(
                isEnable = state
            )
        }
    }


    // function to handle loginUI
    fun loginClicked(userViewModel: ParticularUserSignInViewModel){
        // take email and password state to get both
        val emailState = _emailState.value.enterEmail
        val passwordState = _passwordState.value.enterPassword

        // basic validation
        if(emailState.isBlank() || passwordState.isBlank()){
            loginUiState.value = LoginUiState(
                errorMessage = "Please enter both email and password"
            )
            return
        }

        // start login process
        viewModelScope.launch { // create a coroutine scope
            loginUiState.value = LoginUiState(isLoading = true)
            val result = repository.login(email = emailState, password = passwordState)

            result.onSuccess { response->

              // fetch token from response and save in sharePref
                response.token?.let {token->
                    tokenManager.saveToken(token = token)
                }
                //save response here
                // userViewModel.saveLoginData(response) saves the data
                //Now HomeScreen can access this data!
                userViewModel.saveLoginData(response = response)

                loginUiState.value = LoginUiState(
                    isLoading = false,
                    isSuccess = true
                )
            }

            result.onFailure { exception->
                loginUiState.value = LoginUiState(
                    isLoading = false,
                    errorMessage = exception.message ?: "login failed"
                )
            }
        }
    }

    fun logOut(){
        tokenManager.clearToken()
    }
}