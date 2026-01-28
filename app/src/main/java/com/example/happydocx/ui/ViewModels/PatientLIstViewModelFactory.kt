package com.example.happydocx.ui.ViewModels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happydocx.Data.Network.NetworkConnectivityObserver
import com.example.happydocx.ui.ViewModels.PatientViewModel.PatientListViewModel
import kotlinx.serialization.Contextual

class PatientListViewModelFactory(val context: Context) : ViewModelProvider.Factory{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PatientListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PatientListViewModel(NetworkConnectivityObserver(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}