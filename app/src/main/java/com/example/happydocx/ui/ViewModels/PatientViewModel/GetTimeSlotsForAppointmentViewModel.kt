package com.example.happydocx.ui.ViewModels.PatientViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.AppointmentTimeSlots
import com.example.happydocx.Data.Repository.Patient.ScheduleAppointmentRepo
import com.example.happydocx.ui.uiStates.SavePatientsState.TimeSlotsForAppointmentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GetTimeSlotsForAppointmentViewModel : ViewModel() {

    //take object of the repo
    val repoObject = ScheduleAppointmentRepo()
    // UI State
   private val uiState = MutableStateFlow(TimeSlotsForAppointmentUiState())
    val _uiState = uiState.asStateFlow()
    // network State
   private val networkState: MutableStateFlow<GetTimeSlotsUiState> = MutableStateFlow(GetTimeSlotsUiState.Idle)
    val _networkState = networkState.asStateFlow()

    fun onDateChanged(newDate:String){
        uiState.update { it->
            it.copy(
                dateState = newDate,
                // set the date state to empty string on change of date
                timeState = ""
            )
        }
        networkState.value = GetTimeSlotsUiState.Idle
    }
    fun onTimeExpandStateChanged(newState:Boolean){
        uiState.update { it->
            it.copy(
           isTimeExpanded = newState
            )
        }
    }

    fun onTimeStateChanged(newTime: String) {
        uiState.update { it ->
            it.copy(
                timeState = newTime
            )
        }
    }

    fun onReasonForVisitChanged(newReason:String){
        uiState.update { it->
            it.copy(
                reasonForVisitState = newReason
            )
        }
    }
     fun getAllTimeSlots(
        token:String,
        date:String
    ){
        viewModelScope.launch {
            networkState.value = GetTimeSlotsUiState.Loading
            val result = repoObject.getTimeSlots(token = token,date = date)

            result.onSuccess{success->
                networkState.value = GetTimeSlotsUiState.Success(
                    data = success.slots
                )
            }.onFailure { failure->
                networkState.value = GetTimeSlotsUiState.Error(
                    errorMessage = failure.message ?: "Unknown error Occurred"
                )
            }
        }
    }
}

sealed class GetTimeSlotsUiState{
    object Idle: GetTimeSlotsUiState()
    object Loading: GetTimeSlotsUiState()
    data class Success(val data:List<AppointmentTimeSlots>): GetTimeSlotsUiState()
    data class Error(val errorMessage:String): GetTimeSlotsUiState()
}

