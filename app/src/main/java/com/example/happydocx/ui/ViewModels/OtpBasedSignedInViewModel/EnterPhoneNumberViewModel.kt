package com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.OtpAuthMdodel.SendOtp.SendOtpResponseBody
import com.example.happydocx.Data.Repository.AuthRepository.OtpBasedSignIn.OtpBasedSignIn
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.UpdateAppointmentDetailUiState
import com.example.happydocx.ui.uiStates.LoginUiState
import com.example.happydocx.ui.uiStates.OtpBasedSignedIn.EnterPhoneNumberUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnterPhoneNumberViewModel : ViewModel() {
    private val gettingOptRepo = OtpBasedSignIn()
    private val enterPhoneNumberState = MutableStateFlow(EnterPhoneNumberUiState())
    val _enterPhoneNumber = enterPhoneNumberState.asStateFlow()

    // network state
    private val gettingOtpNetworkState = MutableStateFlow<GettingOtpUiState>(GettingOtpUiState.Idle)
    val _gettingOtpNetworkState = gettingOtpNetworkState.asStateFlow()

    // network state

    // fun on phone number changed
    fun onPhoneNumberChanged(newPhNumber: String) {
        enterPhoneNumberState.update {
            it.copy(
                phoneNumber = newPhNumber
            )
        }
    }


    fun onGetOtpClicked(
        phone: String
    ) {
        viewModelScope.launch {
            gettingOtpNetworkState.value = GettingOtpUiState.Loading
            val result = gettingOptRepo.gettingOtp(phone = phone)
            result.fold(
                onSuccess = {
                    gettingOtpNetworkState.value = GettingOtpUiState.Success(otp = it)
                },
                onFailure = { exception ->
                    gettingOtpNetworkState.value = GettingOtpUiState.Error(
                        exception.message ?: "Something went wrong"
                    )
                }
            )
        }
    }
}

sealed class GettingOtpUiState {
    data class Success(val otp: SendOtpResponseBody) : GettingOtpUiState()
    data class Error(val message: String) : GettingOtpUiState()
    object Loading : GettingOtpUiState()
    object Idle : GettingOtpUiState()
}