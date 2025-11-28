package com.example.happydocx.ui.ViewModels.Departments

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.Departments
import com.example.happydocx.Data.Repository.Departments.AllDepartmentRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GetAllDepartmentViewModel : ViewModel() {

    val repo = AllDepartmentRepo() // repo object created

    private val state: MutableStateFlow<ScreenUiState> = MutableStateFlow(ScreenUiState.Loading)
    val _state = state.asStateFlow()

    suspend fun getAllDepartments(token:String){
        // set state to loading
        viewModelScope.launch {
            state.value = ScreenUiState.Loading
            val result = repo.getAllDepartments(token  = token)

            result.onSuccess { data->
                state.value = ScreenUiState.Success(
                    data = data.departments,
                )
                Log.d("message","Hurray you get the data from the api")
            }.onFailure {
                state.value = ScreenUiState.Error(
                    message = it.message ?: "Unknown error occurred"
                )
                Log.d("DEBUG_VIEWMODEL", "State set to Error")
            }
        }
    }


}


sealed class ScreenUiState {
    object Loading : ScreenUiState()
    data class Success(val data: List<Departments>) : ScreenUiState()
    data class Error(val message: String) : ScreenUiState()
}