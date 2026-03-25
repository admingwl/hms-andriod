package com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.OtpAuthMdodel.VerifyOtp.VerifyOtpResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Data.Repository.AuthRepository.OtpBasedSignIn.OtpBasedSignIn
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.Utils.RetrofitInstance
import com.example.happydocx.ui.uiStates.OtpBasedSignedIn.EnterOtpUiState
import com.google.common.base.Verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EnterOtpViewModel(
    private val tokenManager: TokenManager
) : ViewModel(){

    val repo = OtpBasedSignIn()
    private val verifyOtpUiState = MutableStateFlow(EnterOtpUiState())
    val _verifyOtpUiState = verifyOtpUiState.asStateFlow()

    private val verifyOtpNetworkState: MutableStateFlow<VerifyOtpUiState> = MutableStateFlow(VerifyOtpUiState.Idle)
    val _verifyOtpNetworkState = verifyOtpNetworkState.asStateFlow()

    // on otp changed
    fun onOtpChanged(newOtp:String){
        verifyOtpUiState.update {
            it.copy(
                otp = newOtp
            )
        }
    }
    // on verify otp Clicked
    suspend fun onVerifyOtpClicked(
        otp:String,
        phone:String,
    ){
        viewModelScope.launch {
            verifyOtpNetworkState.value = VerifyOtpUiState.Loading
            val result = repo.verifyOtp(phone = phone,otp = otp)
            result.fold(
                onSuccess = {
                    // before navigation save that token to the token manager
                    if(it.token.isNotBlank()){
                        tokenManager.saveToken(it.token)
                        Log.d("DEBUG_TOKEN", "Token saved: ${it.token}")
                    }
                    verifyOtpNetworkState.value = VerifyOtpUiState.Success(it)
                },
                onFailure = { exception ->
                    verifyOtpNetworkState.value = VerifyOtpUiState.Error(
                        exception.message ?: "Something went wrong"
                    )
                }
            )
        }
    }
}

sealed class VerifyOtpUiState{
    object Loading: VerifyOtpUiState()
    object Idle: VerifyOtpUiState()
    data class  Success(val otp: VerifyOtpResponse): VerifyOtpUiState()
    data class Error(val message:String): VerifyOtpUiState()
}