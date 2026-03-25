package com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happydocx.Data.TokenManager

class EnterOtpViewModelFactory(
    private val tokenManager: TokenManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EnterOtpViewModel(tokenManager) as T
    }
}