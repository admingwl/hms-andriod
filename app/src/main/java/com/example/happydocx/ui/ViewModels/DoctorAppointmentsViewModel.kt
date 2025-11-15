package com.example.happydocx.ui.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.DoctorAppointment.Appointment
import com.example.happydocx.Data.Repository.DoctorAppointments.DoctorAppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ceil

class DoctorAppointmentsViewModel() : ViewModel() {

    // get repo object
    private val repository = DoctorAppointmentRepository()

    private val uiState: MutableStateFlow<AppointmentUiState> = MutableStateFlow(AppointmentUiState.Loading)
    val _uiState = uiState.asStateFlow()


    // add current page state
    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()
    fun getDoctorAppointments(
        token:String?,
        page:Int=1,
        limit:Int=10,
        showCompleted:Boolean = false
    ){
        viewModelScope.launch {
            Log.d("DEBUG_VIEWMODEL", "getDoctorAppointments called")
            Log.d("DEBUG_VIEWMODEL", "Token: $token")
            uiState.value = AppointmentUiState.Loading
            Log.d("DEBUG_VIEWMODEL", "State set to Loading")
            val result = repository.getDoctorAppointments(token,page,limit, showCompleted = showCompleted)

            result.onSuccess { response ->
                Log.d("DEBUG_VIEWMODEL", "API Success! Total: ${response.total}")
                Log.d("DEBUG_VIEWMODEL", "Appointments: ${response.data.size}")
                uiState.value = AppointmentUiState.Success(
                    appointments = response.data,
                    total = response.total,
                    page = response.page,      // Pass from response
                    limit = response.limit     // pass from response
                )
                Log.d("DEBUG_VIEWMODEL", "State set to Success")
            }.onFailure {
                uiState.value = AppointmentUiState.Error(
                    message = it.message ?: "Unknown error occurred"
                )
                Log.d("DEBUG_VIEWMODEL", "State set to Error")
            }

        }
    }

    // create helper function for pagination
    fun loadNextPage(token:String){
     val currentState = uiState.value
        if(currentState is AppointmentUiState.Success){
            val totalpage = ceil(currentState.total.toDouble() / (currentState.limit ?: 10)).toInt()
            val nextPage = (currentState.page ?: 1)+1
            if(nextPage<=totalpage){
                getDoctorAppointments(token,page = nextPage)
            }
        }
    }

    // create helper function for load previous page
    fun loadPreviousPage(token:String){
        val currentState = uiState.value
        if(currentState is AppointmentUiState.Success){
            val prevPage = (currentState.page ?: 1) - 1
            if(prevPage>=1) {
                getDoctorAppointments(token, page = prevPage)
            }
        }
    }

    // fun to load the specific page
    fun loadSpecificPage(token:String,page:Int){
        getDoctorAppointments(token,page=page)
    }


}

sealed class AppointmentUiState {
    object Loading : AppointmentUiState()
    data class Success(
        val appointments:List<Appointment>,
        val total:Int,
        val page: Int? = null,    // From response.page
        val limit: Int? = null
    ): AppointmentUiState()
    data class  Error(val message: String) : AppointmentUiState()

}