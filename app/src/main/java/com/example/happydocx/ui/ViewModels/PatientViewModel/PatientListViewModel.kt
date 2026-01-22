package com.example.happydocx.ui.ViewModels.PatientViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.PatientScreen.Patients
import com.example.happydocx.Data.Repository.Patient.PatientListRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.ceil

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
            listState.value = PatientListUiState.Loading
            val result = repo.getPatientList(
                token = token,
                page = page,
                limit = limit
            )

            result.onSuccess { response ->
                listState.value = PatientListUiState.Success(
                    patientList = response.data.patients,
                    page = response.data.page,
                    limit = response.data.limit,
                    totalPages = response.data.totalPages,
                    totalRecords = response.data.totalRecords
                )
            }.onFailure {
                listState.value = PatientListUiState.Error(
                    message = it.message ?: "Unknown error Occurred"
                )
            }
        }
    }

   // function to load next page
   fun loadNextPage(token:String) {
       val currentState = listState.value
       if (currentState is PatientListUiState.Success) {
           val totalPage = ceil(currentState.totalRecords.toDouble() / currentState.limit).toInt()
           val nextPage = (currentState.page) + 1
           if (nextPage <= totalPage) {
               getPatientList(
                   token = token,
                   page = nextPage
               )
           }
       }
   }

    // load previous page
    fun loadPreviousPage(token:String) {
     val currentState = listState.value
        if(currentState is PatientListUiState.Success){
            val previousPage = (currentState.page)-1
            if(previousPage>=1){
                getPatientList(
                    token = token,
                    page = previousPage
                )
            }
        }
    }



}

sealed class PatientListUiState{
    object Loading: PatientListUiState()
    data class Success(
        val patientList:List<Patients>,
        val page:Int,
        val limit:Int,
        val totalPages:Int,
        val totalRecords:Int,
    ): PatientListUiState()
    data class Error(
        val message:String
    ):PatientListUiState()
}