package com.example.happydocx.ui.ViewModels.Queue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.ScheduleAppointmentResponse
import com.example.happydocx.Data.Model.Queue.TodayQueueResponse
import com.example.happydocx.Data.Model.Queue.walkInRequestBody
import com.example.happydocx.Data.Model.Queue.walkInResponseBody
import com.example.happydocx.Data.Repository.QueueRepo.QueueRepository
import com.example.happydocx.ui.ViewModels.PatientViewModel.GetTimeSlotsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QueueViewModel : ViewModel(){

    val repoObject = QueueRepository()

    private val QueueNetworkState: MutableStateFlow<GetTodayQueueUiState> = MutableStateFlow(GetTodayQueueUiState.Idle)
    val _QueueNetworkState = QueueNetworkState.asStateFlow()


    private val addPatientToQueue :MutableStateFlow<AddPatientToQueue>   = MutableStateFlow(AddPatientToQueue.Idle)
     val _addPatientToQueue = addPatientToQueue.asStateFlow()
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

     fun addPatientToQueue(
        token:String,
        patientID:String
    ) {
        viewModelScope.launch {
            addPatientToQueue.value = AddPatientToQueue.Loading
            val result = repoObject.putPatientWalkinQueue(
                token = token,
                requestBody = walkInRequestBody(
                      patient = patientID,
                    )
            )
            result.onSuccess{success->
                addPatientToQueue.value = AddPatientToQueue.Success(
                    data = success
                )
            }.onFailure { failure->
                addPatientToQueue.value = AddPatientToQueue.Error(
                    message = failure.message ?: "Unknown error Occurred"
                )
            }
        }
    }

    fun resetQueueState() {
        addPatientToQueue.value = AddPatientToQueue.Idle
    }
}

sealed class GetTodayQueueUiState{
    object Idle: GetTodayQueueUiState()
    object Loading: GetTodayQueueUiState()
    data class Success(val data: TodayQueueResponse): GetTodayQueueUiState()
    data class Error(val message:String): GetTodayQueueUiState()
}

sealed class AddPatientToQueue{
    object Idle: AddPatientToQueue()
    object Loading: AddPatientToQueue()
    data class Success(val data: walkInResponseBody): AddPatientToQueue()
    data class Error(val message:String): AddPatientToQueue()
}