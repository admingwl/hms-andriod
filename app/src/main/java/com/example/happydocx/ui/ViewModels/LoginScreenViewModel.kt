package com.example.happydocx.ui.ViewModels

import android.content.Context
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

    private val repository = loginRepository()

    private var emailState = MutableStateFlow(EmailState())
    val _emailState = emailState.asStateFlow()

    private var passwordState = MutableStateFlow(PasswordState())
    val _passwordState = passwordState.asStateFlow()

    private var eyeToggleState = MutableStateFlow(EyeToggleState())
    val _eyeToggleState = eyeToggleState.asStateFlow()

    private var loginUiState = MutableStateFlow(LoginUiState())
    val _loginUiState = loginUiState.asStateFlow()

    fun isUserLoggedIn(): Boolean {
        return tokenManager.getToken() != null
    }

    fun onEmailChanged(newEmail: String) {
        emailState.update { it.copy(enterEmail = newEmail) }
    }

    fun onPasswordChange(newPassword: String) {
        passwordState.update { it.copy(enterPassword = newPassword) }
    }

    fun onEyeTogglePressed(state: Boolean) {
        eyeToggleState.update { it.copy(isEnable = state) }
    }

    fun loginClicked(userViewModel: ParticularUserSignInViewModel) {
        val email = _emailState.value.enterEmail
        val password = _passwordState.value.enterPassword

        if (email.isBlank() || password.isBlank()) {
            loginUiState.value = LoginUiState(errorMessage = "Please enter both email and password")
            return
        }

        viewModelScope.launch {
            loginUiState.value = LoginUiState(isLoading = true)
            val result = repository.login(email = email, password = password)

            result.onSuccess { response ->
                response.token?.let {
                    tokenManager.saveToken(it)
                    userViewModel.saveLoginData(response = response)
                    loginUiState.value = LoginUiState(isLoading = false, isSuccess = true)
                }
            }

            result.onFailure { exception ->
                loginUiState.value = LoginUiState(
                    isLoading = false,
                    errorMessage = exception.message ?: "Login failed"
                )
            }
        }
    }

    fun logOut() {
        tokenManager.clearToken()
    }
}
