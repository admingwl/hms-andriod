package com.example.happydocx.ui.ViewModels.StartConsulting

import androidx.lifecycle.ViewModel
import com.example.happydocx.ui.uiStates.StartConsulting.PatientDocumentUploadUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PatientDocumentUploadViewModel : ViewModel() {

    private  val state = MutableStateFlow(PatientDocumentUploadUiState())
    val _state = state.asStateFlow()

    fun onEnterDocumentDiscriptionChange(newDescription:String){

        state.update { it->
            it.copy(
                documentDiscription = newDescription
            )
        }
    }

    fun onTestResultExpandStateChange(expanded:Boolean){
        state.update { it->
            it.copy(
                testResultExpandingState = expanded
            )
        }
    }

    fun onTestResultSelected(selected:String){
        state.update { it->
            it.copy(
                testResultSelected = selected,
                testResultExpandingState = false
            )
        }
    }
}