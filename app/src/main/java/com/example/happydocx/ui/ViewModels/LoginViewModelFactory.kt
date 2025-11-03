package com.example.happydocx.ui.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happydocx.Data.TokenManager

class LoginViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginScreenViewModel::class.java)) {
            val tokenManager = TokenManager(context)
            @Suppress("Unchecked_Cast")
            return LoginScreenViewModel(tokenManager = tokenManager) as T
        }
        throw IllegalArgumentException("Unknown view Model class")
    }
}