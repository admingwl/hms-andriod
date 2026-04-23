package com.example.happydocx.ui.ViewModels.Queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.ScheduleAppointmentResponse
import com.example.happydocx.Data.Model.Queue.TodayQueueResponse
import com.example.happydocx.Data.Repository.QueueRepo.QueueRepository
import com.example.happydocx.ui.ViewModels.PatientViewModel.GetTimeSlotsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QueueViewModel : ViewModel(){

    val repoObject = QueueRepository()

    private val QueueNetworkState: MutableStateFlow<GetTodayQueueUiState> = MutableStateFlow(GetTodayQueueUiState.Idle)
    val _QueueNetworkState = QueueNetworkState.asStateFlow()

     fun getTodayQueue(
        token:String,
    ) {
        viewModelScope.launch {
            QueueNetworkState.value = GetTodayQueueUiState.Loading
            val result = repoObject.getAllTodayQueue(
                token = token,
            )

            result.onSuccess{success->
                QueueNetworkState.value = GetTodayQueueUiState.Success(
                    data = success
                )
            }.onFailure { failure->
                QueueNetworkState.value = GetTodayQueueUiState.Error(
                    message = failure.message ?: "Unknown error Occurred"
                )
            }
        }
    }
}

sealed class GetTodayQueueUiState{
    object Idle: GetTodayQueueUiState()
    object Loading: GetTodayQueueUiState()
    data class Success(val data: TodayQueueResponse): GetTodayQueueUiState()
    data class Error(val message:String): GetTodayQueueUiState()
}