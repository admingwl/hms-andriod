package com.example.happydocx.ui.ViewModels

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.happydocx.Data.Network.NetworkConnectivityObserver

@RequiresApi(Build.VERSION_CODES.O)
class DoctorAppointmentsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DoctorAppointmentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DoctorAppointmentsViewModel(NetworkConnectivityObserver(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
