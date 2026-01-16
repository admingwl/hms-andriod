package com.example.happydocx.ui.ViewModels.PatientViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.PatientScreen.PatientsList
import com.example.happydocx.Data.Repository.PatientList.PatientListRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PatientListViewModel : ViewModel(){

    // get the repo object
    private val repo = PatientListRepo()
    // lets first get the states.
  private  val listState: MutableStateFlow<PatientListUiState> = MutableStateFlow(PatientListUiState.Loading)
    val _listState = listState.asStateFlow()

    // current page state
    private val _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    // function to get list of all the patient from the repo
    fun getPatientList(
        token:String?,
        page:Int = 1,
        limit:Int = 10
    ){
        viewModelScope.launch {
            Log.d("DEBUG_VIEWMODEL", "getPatientList called")
            Log.d("DEBUG_VIEWMODEL", "Token: $token")
            listState.value = PatientListUiState.Loading
            Log.d("DEBUG_VIEWMODEL", "State set to Loading")
            val result = repo.getPatientList(
                token = token,
                page = page,
                limit = limit
            )

            result.onSuccess { response ->
                Log.d("DEBUG_VIEWMODEL", "API Success! Total: ${response.total}")
                listState.value = PatientListUiState.Success(
                    patientList = response.patients
                )
            }.onFailure {
                listState.value = PatientListUiState.Error(
                    message = it.message ?: "Unknown error Occurred"
                )
            }
        }
    }

}

sealed class PatientListUiState{
    object Loading: PatientListUiState()
    data class Success(
        val patientList:List<PatientsList>,
    ): PatientListUiState()
    data class Error(
        val message:String
    ):PatientListUiState()
}