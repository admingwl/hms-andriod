package com.example.happydocx.ui.ViewModels.StartConsulting

import androidx.camera.core.impl.MutableStateObservable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.StartConsulting.PrescriptionRecord
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllLabResultResponse.LabResultResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.Vitals
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetCurrentMedicationResponse.CurrentMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Request_Vitals
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Save_Vital_Signs_RequestBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Response.Save_vitalSigns_Response_Body
import com.example.happydocx.Data.Repository.StartConsulting.UpdatedVersion1_Repo.StartConsultingRepo
import com.example.happydocx.ui.uiStates.StartConsulting.StartConsultingUiStateUpdated1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StartConsultingViewModel : ViewModel() {

    // get repo object
    val repo = StartConsultingRepo()
    // UI state of start Consultation new Version
   private val startConsultationUpdatedVersion = MutableStateFlow(StartConsultingUiStateUpdated1())
    val _startConsultationfUpdatedVersion = startConsultationUpdatedVersion.asStateFlow()

    fun on_Heart_RateChanged(newHeartRate:String){
        startConsultationUpdatedVersion.update { it->
            it.copy(
                heartRate = newHeartRate
            )
        }
    }
    fun on_Oxygen_Saturation_Changed(newOxygen:String){
        startConsultationUpdatedVersion.update { it->
            it.copy(
                oxygenSaturation = newOxygen
            )
        }
    }
    fun on_SystolicBpChanged(systolic:String){
        startConsultationUpdatedVersion.update { it->
            it.copy(
                systolicBp = systolic
            )
        }
    }
    fun on_DystolicBpChanged(dystolic:String){
        startConsultationUpdatedVersion.update { it->
            it.copy(
                diastolicBp = dystolic
            )
        }
    }
    fun on_RespiratoryRateChanged(newRespiratory:String){
        startConsultationUpdatedVersion.update { it->
            it.copy(
                respiratoryRate = newRespiratory
            )
        }
    }
    fun on_TemperatureChanged(newTemperature:String){
        startConsultationUpdatedVersion.update { it->
            it.copy(
                temperature = newTemperature
            )
        }
    }
    fun on_WeightChanged(newWeight:String){
        startConsultationUpdatedVersion.update { it->
            it.copy(
                weight = newWeight
            )
        }
    }

    // network state of medical Records
    private val medicalRecordState = MutableStateFlow<AllMedicalRecordUiState>(AllMedicalRecordUiState.Idle)
    val _medicalRecordState = medicalRecordState.asStateFlow()

    // network state for save Vitals for patient
    private val saveVitals = MutableStateFlow<SaveVital_SignsUiState>(SaveVital_SignsUiState.Idle)
    val _saveVitals = saveVitals.asStateFlow()

    // network state for list of current medications
    private val currentMedicationState = MutableStateFlow<CurrentMedicationListUiState>(CurrentMedicationListUiState.Idle)
    val _currentMedicationState = currentMedicationState.asStateFlow()

    // get lab results state network
    private val labResultState = MutableStateFlow<CurrentLabResultUiState>(CurrentLabResultUiState.Idle)
    val _labResultState = labResultState.asStateFlow()

    // function to get the medical records
    suspend fun getAllMedicalRecords(
        token: String,
        appointmentId: String
    ) {
        viewModelScope.launch {
            medicalRecordState.value = AllMedicalRecordUiState.Loading
            try {
                val result = repo.getAllMedicalRecords(
                    token = token,
                    appointmentId = appointmentId
                )
                result.onSuccess { response ->
                   medicalRecordState.value = AllMedicalRecordUiState.Success(data = response)
                }.onFailure { errorMessage ->
                        medicalRecordState.value = AllMedicalRecordUiState.Error(
                            message = errorMessage.message ?: "Failed to load Medical details...."
                        )
                }
            } catch (e: Exception) {
                medicalRecordState.value = AllMedicalRecordUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }


    // Helper to determine condition label
    fun getCondition(value: Int?, min: Int, max: Int): String {
        if (value == null) return "Unknown"
        // If it's outside the range (smaller than min OR larger than max), it's a Warning
        return if (value < min || value > max) "Warning" else "Normal"
    }

    fun getBloodPressureCondition(systolic: Int?, diastolic: Int?): String {
        if (systolic == null || diastolic == null) return "Unknown"

        // Warning if High (>140/90) OR if Low (<90/60)
        val isOutOfRange = systolic > 140 || systolic < 90 || diastolic > 90 || diastolic < 60

        return if (isOutOfRange) "Warning" else "Normal"
    }

    fun getOxygenCondition(oxygen: Int?): String {
        if (oxygen == null) return "Unknown"

        // Below 95% is considered a medical warning
        return if (oxygen < 95) "Warning" else "Normal"
    }

    fun getConditionColor(condition: String): Color {
        return when (condition) {
            "Warning" -> Color(0xffAA6207) // Or Color(0xFFDC2626) for a nicer red
            "Normal" -> Color(0xff15803D)
            else -> Color.Gray
        }
    }

    // function to save the Patient's vital signs
    suspend fun Save_patient_Vitals(
        token:String,
        doctor:String,
        patient:String,
        appointment:String
    ){

        // first create the request body.
        val vitals = Request_Vitals(
            bpSystolic = startConsultationUpdatedVersion.value.systolicBp.toIntOrNull()?:0,
            bpDiastolic = startConsultationUpdatedVersion.value.diastolicBp.toIntOrNull()?:0,
            heartRate = startConsultationUpdatedVersion.value.heartRate.toIntOrNull()?:0,
            oxygenSaturation = startConsultationUpdatedVersion.value.oxygenSaturation.toIntOrNull()?:0,
            respiratoryRate = startConsultationUpdatedVersion.value.respiratoryRate.toIntOrNull()?:0,
            temperature = startConsultationUpdatedVersion.value.temperature.toIntOrNull()?:0,
            weight = startConsultationUpdatedVersion.value.weight.toIntOrNull()?:0,
            recordedAt = "",
        )

        val requestBody = Save_Vital_Signs_RequestBody(
            doctor = doctor,
            patient = patient,
            appointment = appointment,
            vitals =  vitals
        )
        viewModelScope.launch {
             saveVitals.value = SaveVital_SignsUiState.Loading
            try {
                val result = repo.Save_Patient_Vitals_Signs_And_Symptoms(token = token,requestBody,appointmentId = appointment)
                result.onSuccess { response ->
                    saveVitals.value = SaveVital_SignsUiState.Success(data = response)
                }.onFailure { errorMessage ->
                    saveVitals.value = SaveVital_SignsUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            }catch (e:Exception){
                saveVitals.value = SaveVital_SignsUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }


    // get list of current Medications.
    fun getCurrentMedications(
        token:String,
        appointmentId:String
    ){
        viewModelScope.launch {
            currentMedicationState.value = CurrentMedicationListUiState.Loading
            try {
                val result = repo.currentMedications(
                    token = token,
                    appointmentId = appointmentId
                )
                result.onSuccess { result->
                    currentMedicationState.value = CurrentMedicationListUiState.Success(data = result)
                }.onFailure { errorMessage->
                    currentMedicationState.value = CurrentMedicationListUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details....")
                }
            }catch(e:Exception){
                currentMedicationState.value = CurrentMedicationListUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }

    // function to get the labResults
    fun getAllLabResults(
        token:String,
        patientId:String
    ){
        viewModelScope.launch {
            labResultState.value = CurrentLabResultUiState.Loading

            try {
                val result = repo.labResults(
                    token = token,
                    patientId = patientId
                )
                result.onSuccess { result->
                    labResultState.value = CurrentLabResultUiState.Success(data = result)
                }.onFailure { errorMessage->
                    labResultState.value = CurrentLabResultUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details....")
                }
            }catch(e:Exception){
                labResultState.value = CurrentLabResultUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }

        }
    }
}

sealed class AllMedicalRecordUiState {
    object Idle : AllMedicalRecordUiState()
    object Loading : AllMedicalRecordUiState()
    data class Success(val data: AllVitalSignsResponse) : AllMedicalRecordUiState()
    data class Error(val message: String) : AllMedicalRecordUiState()
}

sealed class SaveVital_SignsUiState {
    object Idle : SaveVital_SignsUiState()
    object Loading : SaveVital_SignsUiState()
    data class Success(val data: Save_vitalSigns_Response_Body) : SaveVital_SignsUiState()
    data class Error(val message: String) : SaveVital_SignsUiState()
}

// sealed class for list of current Medications
sealed class CurrentMedicationListUiState{
    object Idle : CurrentMedicationListUiState()
    object Loading : CurrentMedicationListUiState()
    data class Success(val data: CurrentMedicationResponse) : CurrentMedicationListUiState()
    data class Error(val message: String) : CurrentMedicationListUiState()
}

// lab result list ui state
sealed class CurrentLabResultUiState{
    object Idle : CurrentLabResultUiState()
    object Loading : CurrentLabResultUiState()
    data class Success(val data: LabResultResponse) : CurrentLabResultUiState()
    data class Error(val message: String) : CurrentLabResultUiState()
}
