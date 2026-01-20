package com.example.happydocx.ui.ViewModels.PatientViewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.PatientScreen.GeneralSavePatient.SavePatientAge
import com.example.happydocx.Data.Model.PatientScreen.GeneralSavePatient.SavePatientRequestBody
import com.example.happydocx.Data.Model.PatientScreen.GeneralSavePatient.SavePatientResponseBody
import com.example.happydocx.Data.Repository.Patient.SavePatientInfo
import com.example.happydocx.ui.ViewModels.StartConsulting.TestAndInvestigation
import com.example.happydocx.ui.uiStates.SavePatientsState.SavePatientGeneralUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavePatientViewModel : ViewModel() {

    // get repo object
    val generalSaveInfoRepo = SavePatientInfo()
    // state to handle the screen UI state.
    private val saveGeneralState = MutableStateFlow(SavePatientGeneralUiState())
    val _saveGeneralState = saveGeneralState.asStateFlow()


    // Save General Api State
    private val apiState: MutableStateFlow<SavePatientGeneralUi> = MutableStateFlow(SavePatientGeneralUi.Idle)
    val _apiState = apiState.asStateFlow()

    // filtered list for the gneder
    val genderList = listOf(
        "Male",
        "Female",
        "Other"
    )

    val MaritalList = listOf(
        "Married",
        "Unmarried",
    )

    fun onContactNumberChanged(newContactNumber:String){
        saveGeneralState.update { contactNumber->
            contactNumber.copy(
                contactNumber = newContactNumber
            )
        }
    }
    fun onEmailChanged(newEmail:String){
        saveGeneralState.update { email->
            email.copy(
                email = newEmail
            )
        }
    }
    fun onFirstNameChanged(newFirstName:String){
        saveGeneralState.update { firstName->
            firstName.copy(
                firstName = newFirstName
            )
        }
    }
    fun onMiddleNameChanged(newMiddleName:String){
        saveGeneralState.update { middleName->
            middleName.copy(
                middleName = newMiddleName
            )
        }
    }
    fun onLastNameChanged(newLastName:String){
        saveGeneralState.update { lastName->
            lastName.copy(
                lastName = newLastName
            )
        }
    }
    fun onPreviousLastNameChanged(newPreviousLastName:String){
        saveGeneralState.update { previousLastName->
            previousLastName.copy(
                previousLastName = newPreviousLastName
            )
        }
    }
    fun onDateOfBirthChanged(newDateOfBirth:String){
        saveGeneralState.update { dateOfBirth->
            dateOfBirth.copy(
                dateOfBirth = newDateOfBirth
            )
        }
    }
    fun onReligionChanged(newReligion:String){
        saveGeneralState.update { religion->
            religion.copy(
                religion = newReligion
            )
        }
    }
    fun onRaceChanged(newRace:String){
        saveGeneralState.update { race->
            race.copy(
                race = newRace
            )
        }
    }
    fun onBloodGroupChanged(newBloodGroup:String){
        saveGeneralState.update { bloodGroup->
            bloodGroup.copy(
                bloodGroup = newBloodGroup
            )
        }
    }
    fun onLandlineNumberChanged(newLandlineNumber:String){
        saveGeneralState.update { number->
            number.copy(
                landLineNumber = newLandlineNumber
            )
        }
    }
    fun onAgeChanged(newAge:String){
        saveGeneralState.update { age->
            age.copy(
                age = newAge
            )
        }
    }

    fun onGenderExpandedStateChange(newState:Boolean){
        saveGeneralState.update { enable ->
            enable.copy(
                isGenderExpanded = newState
            )
        }
    }

    fun onGenderStateChanged(newGender:String){
        saveGeneralState.update { it->
            it.copy(
                genderState = newGender
            )
        }
    }

    fun onMaritalExpandStateChanged(newState:Boolean){
        saveGeneralState.update { it->
            it.copy(
                isMaritalExpanded = newState
            )
        }
    }

    fun onMaritalStateChanged(newMaritalState:String){
        saveGeneralState.update { it->
            it.copy(
                maritalState = newMaritalState
            )
        }
    }

    // function to save the patient General Information.
    fun savePatientGeneralInfo(
        token:String
    ){

        val ageObject = SavePatientAge(
            unit = "Years",
            value = saveGeneralState.value.age  // This should be the numeric age
        )
        viewModelScope.launch {
            apiState.value = SavePatientGeneralUi.Loading

            // map the data
            val requestBody = SavePatientRequestBody(
                Age = saveGeneralState.value.age,
                age = ageObject,
                bloodGroup = saveGeneralState.value.bloodGroup,
                contactNumber = saveGeneralState.value.contactNumber,
                dateOfBirth = saveGeneralState.value.dateOfBirth,
                email = saveGeneralState.value.email,
                first_name = saveGeneralState.value.firstName,
                gender = saveGeneralState.value.genderState,
                landlineNumber = saveGeneralState.value.landLineNumber,
                last_name = saveGeneralState.value.lastName,
                maritalStatus = saveGeneralState.value.maritalState,
                middle_name = saveGeneralState.value.middleName,
                previous_last_name = saveGeneralState.value.previousLastName,
                race = saveGeneralState.value.race,
                religion = saveGeneralState.value.religion,
                prefix =   saveGeneralState.value.prefix.ifEmpty { "Mr" }
            )

            val result = generalSaveInfoRepo.SaveGeneralInformationOfPatient(
                token = token,
                requestBody = requestBody
            )

            result.onSuccess { response->
                apiState.value = SavePatientGeneralUi.Success(response)
            }.onFailure { message->
                apiState.value = SavePatientGeneralUi.Error(message = message.message?:"Unknown Error occurred")
            }
        }
    }

    fun clearTextFiled(){

        saveGeneralState.update { it->
            it.copy(
                age = "",
                bloodGroup = "",
                contactNumber = "",
                dateOfBirth = "",
                email ="",
                firstName = "",
                genderState = "",
                landLineNumber = "",
                lastName ="",
                maritalState ="",
                middleName = "",
                previousLastName = "",
                race = "",
                religion ="",
                prefix =""
            )
        }


    }

    fun resetState() {
        apiState.value = SavePatientGeneralUi.Idle
    }

}

// sealed class for the SaveGeneralInfo
sealed class SavePatientGeneralUi{
    object Idle:SavePatientGeneralUi()
    object Loading: SavePatientGeneralUi()
    data class Success(val data:SavePatientResponseBody): SavePatientGeneralUi()
    data class Error(val message:String): SavePatientGeneralUi()
}