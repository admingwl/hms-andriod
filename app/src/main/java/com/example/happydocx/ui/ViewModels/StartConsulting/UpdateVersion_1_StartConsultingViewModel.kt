package com.example.happydocx.ui.ViewModels.StartConsulting

import androidx.camera.core.impl.MutableStateObservable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.StartConsulting.PrescriptionRecord
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateRequestUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateResponseUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationRequest
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllLabResultResponse.LabResultResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllMedicalRecords.GetAllMedicalRecordsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllMedicalRecords.GetAllMedicalRecordsResponseItem
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.Vitals
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetCurrentMedicationResponse.CurrentMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetParticularPatientAppointmentData.GetParticularPatientAppointemntDataResponse.PatientAppointmentData
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.HistoryResponse.GetAllHistoryResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Request_Vitals
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Save_Vital_Signs_RequestBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Response.Save_vitalSigns_Response_Body
import com.example.happydocx.Data.Repository.StartConsulting.UpdatedVersion1_Repo.StartConsultingRepo
import com.example.happydocx.ui.ViewModels.AppointmentUiState
import com.example.happydocx.ui.uiStates.StartConsulting.AddLabResultManualUpdate1
import com.example.happydocx.ui.uiStates.StartConsulting.AddMedicationUpdated1
import com.example.happydocx.ui.uiStates.StartConsulting.StartConsultingUiStateUpdated1
import com.example.happydocx.ui.uiStates.StartConsulting.UploadLabReportUpdate1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import us.zoom.proguard.bo
import kotlin.math.ceil

class StartConsultingViewModel : ViewModel() {

    // get repo object
    val repo = StartConsultingRepo()

    // UI state of start Consultation new Version
    private val startConsultationUpdatedVersion = MutableStateFlow(StartConsultingUiStateUpdated1())
    val _startConsultationfUpdatedVersion = startConsultationUpdatedVersion.asStateFlow()


    fun on_Heart_RateChanged(newHeartRate: String) {
        startConsultationUpdatedVersion.update { it ->
            it.copy(
                heartRate = newHeartRate
            )
        }
    }

    fun on_Oxygen_Saturation_Changed(newOxygen: String) {
        startConsultationUpdatedVersion.update { it ->
            it.copy(
                oxygenSaturation = newOxygen
            )
        }
    }

    fun on_SystolicBpChanged(systolic: String) {
        startConsultationUpdatedVersion.update { it ->
            it.copy(
                systolicBp = systolic
            )
        }
    }

    fun on_DystolicBpChanged(dystolic: String) {
        startConsultationUpdatedVersion.update { it ->
            it.copy(
                diastolicBp = dystolic
            )
        }
    }

    fun on_RespiratoryRateChanged(newRespiratory: String) {
        startConsultationUpdatedVersion.update { it ->
            it.copy(
                respiratoryRate = newRespiratory
            )
        }
    }

    fun on_TemperatureChanged(newTemperature: String) {
        startConsultationUpdatedVersion.update { it ->
            it.copy(
                temperature = newTemperature
            )
        }
    }

    fun on_WeightChanged(newWeight: String) {
        startConsultationUpdatedVersion.update { it ->
            it.copy(
                weight = newWeight
            )
        }
    }

    // ui state of add medications.
    private val addMedicationUpdatedVersion = MutableStateFlow(AddMedicationUpdated1())
    val _addMedicationUpdateVersion = addMedicationUpdatedVersion.asStateFlow()

    fun onMedicationNameChanged(newMedication: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationName = newMedication
            )
        }
    }

    fun onDosageChanged(newDosage: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationDosage = newDosage
            )
        }
    }

    fun onFrequencyChanged(newFrequency: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationFrequency = newFrequency
            )
        }
    }

    fun onDurationChanged(newDuration: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationDuration = newDuration
            )
        }
    }

    fun onRouteChanged(newRoute: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationRoute = newRoute
            )
        }
    }

    fun onTimingChanged(newTiming: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationTiming = newTiming
            )
        }
    }

    fun onDateChanged(newDate: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationDate = newDate
            )
        }
    }

    fun onNotesChanged(newNotes: String) {
        addMedicationUpdatedVersion.update { it ->
            it.copy(
                medicationNotes = newNotes
            )
        }
    }

    // manual lab entry ui state
    private val labManualEntryUiState = MutableStateFlow(AddLabResultManualUpdate1())
    val _labManuLEntryUiState = labManualEntryUiState.asStateFlow()

    fun onTestNameChanged(newTest: String) {
        labManualEntryUiState.update { it ->
            it.copy(
                testName = newTest
            )
        }
    }

    fun onResultValueChanged(newResultValue: String) {
        labManualEntryUiState.update { it ->
            it.copy(
                resultValue = newResultValue
            )
        }
    }

    fun onUnitChanged(newUnit: String) {
        labManualEntryUiState.update { it ->
            it.copy(
                unit = newUnit
            )
        }
    }

    fun onTestDateChanged(newTestDate: String) {
        labManualEntryUiState.update { it ->
            it.copy(
                testDate = newTestDate
            )
        }
    }

    fun onNormalRangeChanged(newNormalRange: String) {

        labManualEntryUiState.update { it ->
            it.copy(
                normalRange = newNormalRange
            )
        }
    }

    fun onStatusChanged(newStatus: String) {

        labManualEntryUiState.update { it ->
            it.copy(
                status = newStatus
            )
        }
    }

    fun onManualLabRecordsNotesChanged(newNotes: String) {
        labManualEntryUiState.update { it ->
            it.copy(
                notes = newNotes
            )
        }
    }

    // upload lab report UI state
    private val uploadLabReportState = MutableStateFlow(UploadLabReportUpdate1())
    val _uploadLabReportState = uploadLabReportState.asStateFlow()

    // on report date changed
    fun onReportDateChanged(newDate:String){
        uploadLabReportState.update { it->
            it.copy(
                reportDate = newDate
            )
        }
    }
    // on report type changed
   fun onReportTypeChanged(newType:String){
        uploadLabReportState.update { it->
            it.copy(
                reportType = newType
            )
        }
    }
    // on laboratory name changed
    fun onLaboratoryNameChanged(newLaboratory:String) {
        uploadLabReportState.update { it ->
            it.copy(
                laboratoryName = newLaboratory
            )
        }
    }
    // on laboratory notes changed
    fun onLaboratoryNotesChanged(newNotes:String) {
        uploadLabReportState.update { it ->
            it.copy(
                notes = newNotes
            )
        }
    }

    // network state of medical Records
    private val medicalRecordState =
        MutableStateFlow<AllMedicalRecordUiState>(AllMedicalRecordUiState.Idle)
    val _medicalRecordState = medicalRecordState.asStateFlow()

    // network state for save Vitals for patient
    private val saveVitals = MutableStateFlow<SaveVital_SignsUiState>(SaveVital_SignsUiState.Idle)
    val _saveVitals = saveVitals.asStateFlow()

    // network state for list of current medications
    private val currentMedicationState =
        MutableStateFlow<CurrentMedicationListUiState>(CurrentMedicationListUiState.Idle)
    val _currentMedicationState = currentMedicationState.asStateFlow()

    // get lab results state network
    private val labResultState =
        MutableStateFlow<CurrentLabResultUiState>(CurrentLabResultUiState.Idle)
    val _labResultState = labResultState.asStateFlow()

    // network state of create new medication
    private val createNewMedicationState =
        MutableStateFlow<CreateNewMedicationUiState>(CreateNewMedicationUiState.Idle)
    val _createNewMedicationState = createNewMedicationState.asStateFlow()

    // create lab result Manually network state
    private val createLabResultManuallyState: MutableStateFlow<CreateLabResultManuallyUiState> =
        MutableStateFlow(CreateLabResultManuallyUiState.Idle)
    val _createLabResultManuallyState = createLabResultManuallyState.asStateFlow()

    // particular patient appointment data network state
    private val particularPatientAppointmentDataState: MutableStateFlow<ParticularPatientAppointmentDataUiState> = MutableStateFlow(ParticularPatientAppointmentDataUiState.Idle)
    val _particularPatientAppointmentDataState = particularPatientAppointmentDataState.asStateFlow()

    // network state for histories
    private val historiesState = MutableStateFlow<HistoriesUiState>(HistoriesUiState.Idle)
    val _historiesState = historiesState.asStateFlow()

    // get all medical document
    private val medicalDocumentRecords: MutableStateFlow<MedicalDocumentListUiState> = MutableStateFlow(MedicalDocumentListUiState.Idle)
    val _medicalDocumentRecords = medicalDocumentRecords.asStateFlow()


    private val _currentPageHistory = MutableStateFlow(1)
    val currentPageHistory = _currentPageHistory.asStateFlow()


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
        token: String,
        doctor: String,
        patient: String,
        appointment: String
    ) {

        // first create the request body.
        val vitals = Request_Vitals(
            bpSystolic = startConsultationUpdatedVersion.value.systolicBp.toIntOrNull() ?: 0,
            bpDiastolic = startConsultationUpdatedVersion.value.diastolicBp.toIntOrNull() ?: 0,
            heartRate = startConsultationUpdatedVersion.value.heartRate.toIntOrNull() ?: 0,
            oxygenSaturation = startConsultationUpdatedVersion.value.oxygenSaturation.toIntOrNull()
                ?: 0,
            respiratoryRate = startConsultationUpdatedVersion.value.respiratoryRate.toIntOrNull()
                ?: 0,
            temperature = startConsultationUpdatedVersion.value.temperature.toIntOrNull() ?: 0,
            weight = startConsultationUpdatedVersion.value.weight.toIntOrNull() ?: 0,
            recordedAt = "",
        )

        val requestBody = Save_Vital_Signs_RequestBody(
            doctor = doctor,
            patient = patient,
            appointment = appointment,
            vitals = vitals
        )
        viewModelScope.launch {
            saveVitals.value = SaveVital_SignsUiState.Loading
            try {
                val result = repo.Save_Patient_Vitals_Signs_And_Symptoms(
                    token = token,
                    requestBody,
                    appointmentId = appointment
                )
                result.onSuccess { response ->
                    saveVitals.value = SaveVital_SignsUiState.Success(data = response)
                }.onFailure { errorMessage ->
                    saveVitals.value = SaveVital_SignsUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {
                saveVitals.value = SaveVital_SignsUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }


    // get list of current Medications.
    fun getCurrentMedications(
        token: String,
        appointmentId: String
    ) {
        viewModelScope.launch {
            currentMedicationState.value = CurrentMedicationListUiState.Loading
            try {
                val result = repo.currentMedications(
                    token = token,
                    appointmentId = appointmentId
                )
                result.onSuccess { result ->
                    currentMedicationState.value =
                        CurrentMedicationListUiState.Success(data = result)
                }.onFailure { errorMessage ->
                    currentMedicationState.value = CurrentMedicationListUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {
                currentMedicationState.value = CurrentMedicationListUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }

    // function to get the labResults
    fun getAllLabResults(
        token: String,
        patientId: String
    ) {
        viewModelScope.launch {
            labResultState.value = CurrentLabResultUiState.Loading

            try {
                val result = repo.labResults(
                    token = token,
                    patientId = patientId
                )
                result.onSuccess { result ->
                    labResultState.value = CurrentLabResultUiState.Success(data = result)
                }.onFailure { errorMessage ->
                    labResultState.value = CurrentLabResultUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {
                labResultState.value = CurrentLabResultUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }

        }
    }

    // function to create new medication
    fun createNewMedication(
        token: String,
        appointmentId: String,
    ) {
        viewModelScope.launch {
            createNewMedicationState.value = CreateNewMedicationUiState.Loading
            try {

                val requestBody = CreateMedicationRequest(
                    dosage = addMedicationUpdatedVersion.value.medicationDosage,
                    duration = addMedicationUpdatedVersion.value.medicationDuration,
                    frequency = addMedicationUpdatedVersion.value.medicationFrequency,
                    medicationName = addMedicationUpdatedVersion.value.medicationName,
                    instructions = addMedicationUpdatedVersion.value.medicationNotes,
                    route = addMedicationUpdatedVersion.value.medicationRoute,
                    timing = addMedicationUpdatedVersion.value.medicationTiming,
                    startDate = addMedicationUpdatedVersion.value.medicationDate
                )
                val result = repo.createNewMedicationRepo(
                    token = token,
                    appointmentId = appointmentId,
                    requestBody = requestBody
                )
                result.onSuccess { result ->
                    createNewMedicationState.value =
                        CreateNewMedicationUiState.Success(data = result)
                    resetMedicalRecordState()
                }.onFailure { errorMessage ->
                    createNewMedicationState.value = CreateNewMedicationUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {
                createNewMedicationState.value = CreateNewMedicationUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }

    // reset the state of the medical records
    fun resetMedicalRecordState() {
        medicalRecordState.value = AllMedicalRecordUiState.Idle
    }

    // create lab result manually
    fun createLabResultManuallyViewModelFunction(
        token: String,
        doctorId: String,
        patientId: String
    ) {
        viewModelScope.launch {
            createLabResultManuallyState.value = CreateLabResultManuallyUiState.Loading

            try {
                val body = ManualLabReportCreateRequestUpdate1(
                    doctor = doctorId,
                    normalRange = labManualEntryUiState.value.normalRange,
                    notes = labManualEntryUiState.value.notes,
                    patient = patientId,
                    resultValue = labManualEntryUiState.value.resultValue,
                    status = labManualEntryUiState.value.status,
                    testDate = labManualEntryUiState.value.testDate,
                    testName = labManualEntryUiState.value.testName,
                    unit = labManualEntryUiState.value.unit
                )
                val result = repo.CreateLabResultManuallyRepo(
                    token = token,
                    requestBody = body
                )
                result.onSuccess { result ->
                    createLabResultManuallyState.value =
                        CreateLabResultManuallyUiState.Success(data = result)
                }.onFailure { errorMessage ->
                    createLabResultManuallyState.value = CreateLabResultManuallyUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {

            }
        }

    }

    fun resetManuallyAddLabResult() {
        createLabResultManuallyState.value = CreateLabResultManuallyUiState.Idle
    }

    // fun to get particular patient data
    suspend fun getParticularPatientAppointmentData(
        token:String,
        appointmentId:String
    ){
        viewModelScope.launch {
            labResultState.value = CurrentLabResultUiState.Loading

            try {
                val result = repo.particularPatientAppointmentDataRepo(
                    token = token,
                    appointmentId = appointmentId
                )
                result.onSuccess { result ->
                    particularPatientAppointmentDataState.value = ParticularPatientAppointmentDataUiState.Success(data = result)
                }.onFailure { errorMessage ->
                    particularPatientAppointmentDataState.value = ParticularPatientAppointmentDataUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {
                labResultState.value = CurrentLabResultUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }

        }
    }

    suspend fun getAllHistories(
        token: String,
        patient: String,
        page:Int = 1,
        limit:Int = 10
    ) {
        viewModelScope.launch {
            historiesState.value = HistoriesUiState.Loading
            try {
                val result = repo.historyRepo(
                    token = token,
                    patientId = patient,
                    page = page,
                    limit = limit
                )
                result.onSuccess { response ->
                    historiesState.value = HistoriesUiState.Success(data = response)
                }.onFailure { errorMessage ->
                    historiesState.value = HistoriesUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {
                historiesState.value = HistoriesUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }

    suspend fun getAllMedicalRecordsViewModel(
        token: String,
        patientId: String,
    ){
        viewModelScope.launch {
            medicalDocumentRecords.value = MedicalDocumentListUiState.Loading
            try {
                val result = repo.getAllMedicationDocumentListRepo(
                    token = token,
                    patientId = patientId,
                )
                result.onSuccess { response ->
                    medicalDocumentRecords.value = MedicalDocumentListUiState.Success(data = response)
                }.onFailure { errorMessage ->
                    medicalDocumentRecords.value = MedicalDocumentListUiState.Error(
                        message = errorMessage.message ?: "Failed to load Medical details...."
                    )
                }
            } catch (e: Exception) {
                medicalDocumentRecords.value = MedicalDocumentListUiState.Error(
                    message = e.message ?: "An Unexpected error occurred"
                )
            }
        }
    }
    // create helper function for pagination
   suspend fun loadNextHistoryPage(token:String,patientId: String){
        val currentState = historiesState.value
        if(currentState is HistoriesUiState.Success){
            val totalpage = ceil(currentState.data.totalPages.toDouble() / (currentState.data.limit ?: 10)).toInt()
            val nextPage = (currentState.data.page ?: 1)+1
            if(nextPage<=totalpage){
                getAllHistories(token,page = nextPage, patient = patientId)
            }
        }
    }

    // create helper function for load previous page
    suspend fun loadPreviousHistoryPage(token:String,patientId:String){
        val currentState = historiesState.value
        if(currentState is HistoriesUiState.Success){
            val prevPage = (currentState.data.page ?: 1) - 1
            if(prevPage>=1) {
                getAllHistories(token, page = prevPage, patient = patientId)
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
sealed class CurrentMedicationListUiState {
    object Idle : CurrentMedicationListUiState()
    object Loading : CurrentMedicationListUiState()
    data class Success(val data: CurrentMedicationResponse) : CurrentMedicationListUiState()
    data class Error(val message: String) : CurrentMedicationListUiState()
}

// lab result list ui state
sealed class CurrentLabResultUiState {
    object Idle : CurrentLabResultUiState()
    object Loading : CurrentLabResultUiState()
    data class Success(val data: LabResultResponse) : CurrentLabResultUiState()
    data class Error(val message: String) : CurrentLabResultUiState()
}

sealed class CreateNewMedicationUiState {
    object Idle : CreateNewMedicationUiState()
    object Loading : CreateNewMedicationUiState()
    data class Success(val data: CreateMedicationResponse) : CreateNewMedicationUiState()
    data class Error(val message: String) : CreateNewMedicationUiState()
}

// sealed class for creating the lab result manually
sealed class CreateLabResultManuallyUiState {
    object Idle : CreateLabResultManuallyUiState()
    object Loading : CreateLabResultManuallyUiState()
    data class Success(val data: ManualLabReportCreateResponseUpdate1) :
        CreateLabResultManuallyUiState()

    data class Error(val message: String) : CreateLabResultManuallyUiState()
}

// particular patient appointment Data
sealed class ParticularPatientAppointmentDataUiState {
    object Idle : ParticularPatientAppointmentDataUiState()
    object Loading : ParticularPatientAppointmentDataUiState()
    data class Success(val data: PatientAppointmentData) : ParticularPatientAppointmentDataUiState()
    data class Error(val message: String) : ParticularPatientAppointmentDataUiState()
}

// histories sealed class
sealed class HistoriesUiState{
    object Idle : HistoriesUiState()
    object Loading : HistoriesUiState()
    data class Success(val data: GetAllHistoryResponse) : HistoriesUiState()
    data class Error(val message: String) : HistoriesUiState()
}

// Medical Document list
sealed class MedicalDocumentListUiState{
    object Idle : MedicalDocumentListUiState()
    object Loading : MedicalDocumentListUiState()
    data class Success(val data: List<GetAllMedicalRecordsResponseItem>) : MedicalDocumentListUiState()
    data class Error(val message: String) : MedicalDocumentListUiState()
}

