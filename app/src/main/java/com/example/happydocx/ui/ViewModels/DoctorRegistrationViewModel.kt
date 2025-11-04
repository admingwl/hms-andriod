package com.example.happydocx.ui.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.FormModel.Address
import com.example.happydocx.Data.Model.FormModel.PersonalDetails
import com.example.happydocx.Data.Model.FormModel.ProfessionalDetail
import com.example.happydocx.Data.Model.FormModel.SaveDraftResponse
import com.example.happydocx.Data.Repository.FormRepository.FormOneRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DoctorRegistrationViewModel(
    private val doctorId:String
) : ViewModel() {


    // get instance of the form_ome_repository
    val form_one_repository = FormOneRepository()

    // add state
    private var saveDraftState = MutableStateFlow<SaveDraftState>(SaveDraftState.Idle)
    val _saveDraftState = saveDraftState.asStateFlow()

    fun saveDraft(
        personalDetails: PersonalDetails ,
        professionalDetail: ProfessionalDetail,
        address: Address
    ){

        viewModelScope.launch {
            saveDraftState.value = SaveDraftState.Loading
            // use doctor Id in api call
            val result = form_one_repository.SaveDraft(
                doctorId = doctorId,
                personalDetails = personalDetails,
                professionalDetail = professionalDetail,
                address = address
            )

            saveDraftState.value = if(result.isSuccess){
                SaveDraftState.Success(response = result.getOrNull()!!)
            }else{
                SaveDraftState.Error(result.exceptionOrNull()?.message?:"Unknow Error")
            }
        }
    }
}

sealed class SaveDraftState{
    object Idle : SaveDraftState()
    object Loading : SaveDraftState()
    data class Success(val response: SaveDraftResponse) : SaveDraftState()
    data class Error(val message: String) : SaveDraftState()
}