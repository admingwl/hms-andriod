package com.example.happydocx.ui.ViewModels.PatientViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.AppointmentTimeSlots
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.ScheduleAppointmentRequest
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.ScheduleAppointmentResponse
import com.example.happydocx.Data.Repository.Patient.ScheduleAppointmentRepo
import com.example.happydocx.Data.Repository.Patient.SchedulePatientAppointment
import com.example.happydocx.ui.uiStates.SavePatientsState.TimeSlotsForAppointmentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GetTimeSlotsForAppointmentViewModel : ViewModel() {

    //take object of the repo
    val repoObject = ScheduleAppointmentRepo()
    // lets get the repo object
    val repoObjectScheduleAppointment = SchedulePatientAppointment()
    // UI State
   private val uiState = MutableStateFlow(TimeSlotsForAppointmentUiState())
    val _uiState = uiState.asStateFlow()
    // network State
   private val networkState: MutableStateFlow<GetTimeSlotsUiState> = MutableStateFlow(GetTimeSlotsUiState.Idle)
    val _networkState = networkState.asStateFlow()

    // network state for the create appointment
    private val createAppointmentNetworkState: MutableStateFlow<CreatePatientAppointmentUiState> = MutableStateFlow(CreatePatientAppointmentUiState.Idle)
    val _createAppointmentNetorkState = createAppointmentNetworkState.asStateFlow()

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


    suspend fun scheduleAppointment(
        token:String,
        patientId:String
    ){
        viewModelScope.launch {
            createAppointmentNetworkState.value = CreatePatientAppointmentUiState.Loading

            val requestBody = ScheduleAppointmentRequest(
                appointmentDate = uiState.value.dateState,
                appointmentTime = uiState.value.timeState,
                patient = patientId,
                reason = uiState.value.reasonForVisitState
                )

            val result = repoObjectScheduleAppointment.ScheduleAppoitmentForPatientRepo(token = token,requestBody = requestBody)

            result.onSuccess{response->
                createAppointmentNetworkState.value = CreatePatientAppointmentUiState.Success(
                    data = response
                )
            }.onFailure { failure->
                createAppointmentNetworkState.value = CreatePatientAppointmentUiState.Error(
                    message = failure.message?:"Unknown error Occurred"
                )
            }

        }
    }

    // Add this function
    fun resetAppointmentState() {
        createAppointmentNetworkState.value = CreatePatientAppointmentUiState.Idle
    }

    // Add this function to reset all form fields
    fun resetFormFields() {
        uiState.value = TimeSlotsForAppointmentUiState(
            dateState = "",
            timeState = "",
            reasonForVisitState = "",
            isTimeExpanded = false
        )
    }


}

sealed class GetTimeSlotsUiState{
    object Idle: GetTimeSlotsUiState()
    object Loading: GetTimeSlotsUiState()
    data class Success(val data:List<AppointmentTimeSlots>): GetTimeSlotsUiState()
    data class Error(val errorMessage:String): GetTimeSlotsUiState()
}

sealed class CreatePatientAppointmentUiState{
    object Idle: CreatePatientAppointmentUiState()
    object Loading: CreatePatientAppointmentUiState()
    data class Success(val data: ScheduleAppointmentResponse): CreatePatientAppointmentUiState()
    data class Error(val message:String): CreatePatientAppointmentUiState()
}

