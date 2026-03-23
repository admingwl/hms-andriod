package com.example.happydocx.ui.ViewModels.StartConsulting

import android.content.Context
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateRequestUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateResponseUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationRequest
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllLabResultResponse.LabResultResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllMedicalRecords.GetAllMedicalRecordsResponseItem
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetCurrentMedicationResponse.CurrentMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetParticularPatientAppointmentData.GetParticularPatientAppointemntDataResponse.PatientAppointmentData
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.HistoryResponse.GetAllHistoryResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Request_Vitals
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Save_Vital_Signs_RequestBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Response.Save_vitalSigns_Response_Body
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UpdateAppointmentDetail.Data
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UpdateAppointmentDetail.UpdateAppointmentDetailRequest
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UpdateAppointmentDetail.UpdateAppointmentDetailResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadDocuements.UploadDocumentResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request.Notes
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request.Orders
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request.Prescription
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request.UploadNotesRequestBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Response.UploadNotesResponseBody
import com.example.happydocx.Data.Repository.StartConsulting.UpdatedVersion1_Repo.StartConsultingRepo
import com.example.happydocx.ui.uiStates.StartConsulting.AddLabResultManualUpdate1
import com.example.happydocx.ui.uiStates.StartConsulting.AddMedicationUpdated1
import com.example.happydocx.ui.uiStates.StartConsulting.ConsultationNotesUpdate1
import com.example.happydocx.ui.uiStates.StartConsulting.MedicationItem
import com.example.happydocx.ui.uiStates.StartConsulting.StartConsultingUiStateUpdated1
import com.example.happydocx.ui.uiStates.StartConsulting.UploadDocumentUpdate1
import com.example.happydocx.ui.uiStates.StartConsulting.UploadLabReportUpdate1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    fun onReportDateChanged(newDate: String) {
        uploadLabReportState.update { it ->
            it.copy(
                reportDate = newDate
            )
        }
    }

    // on report type changed
    fun onReportTypeChanged(newType: String) {
        uploadLabReportState.update { it ->
            it.copy(
                reportType = newType
            )
        }
    }

    // on laboratory name changed
    fun onLaboratoryNameChanged(newLaboratory: String) {
        uploadLabReportState.update { it ->
            it.copy(
                laboratoryName = newLaboratory
            )
        }
    }

    // on laboratory notes changed
    fun onLaboratoryNotesChanged(newNotes: String) {
        uploadLabReportState.update { it ->
            it.copy(
                notes = newNotes
            )
        }
    }

    // upload Documents ui state
    private val uploadDocumentState = MutableStateFlow(UploadDocumentUpdate1())
    val _uploadDocumentState = uploadDocumentState.asStateFlow()

    fun onDocumentNameChanged(newDocumentName: String) {
        uploadDocumentState.update {
            it.copy(
                documentName = newDocumentName
            )
        }
    }

    fun onDocumentTypeChanged(documentType: String) {
        uploadDocumentState.update {
            it.copy(
                documentType = documentType
            )
        }
    }

    fun onDocumentDateChanged(documentDate: String) {
        uploadDocumentState.update {
            it.copy(
                reportDate = documentDate
            )
        }
    }

    fun onAttachmentChange(
        uri: Uri?,
        name: String
    ) {
        uploadDocumentState.update {
            it.copy(
                attachmentURI = uri,
                attachmentName = name
            )
        }
    }

    // ui state for Consultation notes
    private val consultationNotesState = MutableStateFlow(ConsultationNotesUpdate1())
    val _consultationNotesState = consultationNotesState.asStateFlow()

    fun onChiefComplaintChange(newComplaint: String) {
        consultationNotesState.update {
            it.copy(
                chiefComplaint = newComplaint
            )
        }
    }

    fun historyOfPresentIllnessChange(newHistory: String) {
        consultationNotesState.update {
            it.copy(
                historyOfPresentIllness = newHistory
            )
        }
    }

    fun onPhysicalExaminationChanged(newExamination: String) {
        consultationNotesState.update {
            it.copy(
                physicalExamination = newExamination
            )
        }
    }

    fun onAssessmentAndDiagnosisChanged(newDiagnosis: String) {
        consultationNotesState.update {
            it.copy(
                assessmentAndDiagnosis = newDiagnosis
            )
        }
    }

    fun onTreatmentPlanChanged(newPlan: String) {
        consultationNotesState.update {
            it.copy(
                treatmentPlan = newPlan
            )
        }
    }

    fun onFollowUpChanged(newFollowUp: String) {
        consultationNotesState.update {
            it.copy(
                followUp = newFollowUp
            )
        }
    }

    fun onPriorityChanged(newPriority: String) {
        consultationNotesState.update {
            it.copy(
                priority = newPriority
            )
        }
    }

    // add new medication card
    fun addMedication() {
        consultationNotesState.update { state ->
            val newId = (state.medications.maxOfOrNull { it.id } ?: 0) + 1
            state.copy(medications = state.medications + MedicationItem(id = newId))
        }
    }

    // Remove a medication card by id
    fun removeMedication(id: Int) {
        consultationNotesState.update { state ->
            state.copy(medications = state.medications.filter { it.id != id })
        }
    }

    // Update a specific medication field by id
    fun updateMedication(id: Int, update: MedicationItem.() -> MedicationItem) {
        consultationNotesState.update { state ->
            state.copy(
                medications = state.medications.map { item ->
                    if (item.id == id) item.update() else item
                }
            )
        }
    }

//    fun onNotesMedicationNameChanged(newMedication:String){
//        consultationNotesState.update {
//            it.copy(
//                medicationName = newMedication
//            )
//        }
//    }
//
//    fun onNotesDosageChanged(newDossage:String){
//        consultationNotesState.update {
//            it.copy(
//                dosage = newDossage
//            )
//        }
//    }
//
//    fun onNotesFrequencyChanged(newFrequency:String){
//        consultationNotesState.update {
//            it.copy(
//                frequency = newFrequency
//            )
//        }
//    }
//
//    fun onNotesDurationChanged(newDuration:String){
//        consultationNotesState.update {
//            it.copy(
//                duration = newDuration
//            )
//        }
//    }
//
//    fun mealTimeChanged(newMealTime:String) {
//        consultationNotesState.update {
//            it.copy(
//                mealTime = newMealTime
//            )
//        }
//    }
//
//    fun onSpecialInstructionsChanged(newInstructions:String){
//        consultationNotesState.update {
//            it.copy(
//                specialInstructions = newInstructions
//            )
//        }
//    }

    fun onLabTestChanged(labTest: String) {
        consultationNotesState.update {
            it.copy(
                labTest = labTest
            )
        }
    }

    fun onImagingStudiesChanged(newStudies: String) {
        consultationNotesState.update {
            it.copy(
                imagingStudies = newStudies
            )
        }
    }

    fun onReferralsChanged(newReferrals: String) {
        consultationNotesState.update {
            it.copy(
                referrals = newReferrals
            )
        }
    }

    fun onUrgencyChanged(newUrgency: String) {
        consultationNotesState.update {
            it.copy(
                urgency = newUrgency
            )
        }
    }

    fun onExpectedTimeLineChanged(newTime: String) {
        consultationNotesState.update {
            it.copy(
                expectedTimeline = newTime
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
    private val particularPatientAppointmentDataState: MutableStateFlow<ParticularPatientAppointmentDataUiState> =
        MutableStateFlow(ParticularPatientAppointmentDataUiState.Idle)
    val _particularPatientAppointmentDataState = particularPatientAppointmentDataState.asStateFlow()

    // network state for histories
    private val historiesState = MutableStateFlow<HistoriesUiState>(HistoriesUiState.Idle)
    val _historiesState = historiesState.asStateFlow()

    // get all medical document
    private val medicalDocumentRecords: MutableStateFlow<MedicalDocumentListUiState> =
        MutableStateFlow(MedicalDocumentListUiState.Idle)
    val _medicalDocumentRecords = medicalDocumentRecords.asStateFlow()


    private val uploadDocumentNetworkState: MutableStateFlow<UploadPatientDocumentUIState> =
        MutableStateFlow(UploadPatientDocumentUIState.Idle)
    val _uploadDocumentNetworkState = uploadDocumentNetworkState.asStateFlow()


    // upload notes network state
    private val uploadNotes: MutableStateFlow<UploadNotesUiState> =
        MutableStateFlow(UploadNotesUiState.Idle)
    val _uploadNotes = uploadNotes.asStateFlow()

    // update appointmentDetail network state
    private val updateDetailState: MutableStateFlow<UpdateAppointmentDetailUiState> = MutableStateFlow(UpdateAppointmentDetailUiState.Idle)
      val _updateDetailState = updateDetailState.asStateFlow()

    suspend fun onSaveUpdateAppointmentDetailsClicked(
        token: String,
        patientId:String,
        firstName: String,
        lastName: String,
        age: String,
        gender: String,
        bloodGroup: String,
        phone: String,
        email: String,
        address: String,
        allergies: String
    ){
        viewModelScope.launch {
            updateDetailState.value = UpdateAppointmentDetailUiState.Loading

            val requestBody = UpdateAppointmentDetailRequest(
                patientId = patientId,         // top level
                data = Data(
                    patientId = patientId,     // also inside data
                    firstName = firstName,
                    lastName = lastName,
                    age = age.toIntOrNull() ?: 0,  // String → Int, 0 if invalid
                    gender = gender,
                    bloodGroup = bloodGroup,
                    phone = phone,
                    email = email,
                    address = address,
                    allergies = allergies
                        .split(",")            // "pollen, dust" → ["pollen", "dust"]
                        .map { it.trim() }     // remove extra spaces
                        .filter { it.isNotEmpty() } // remove empty strings
                )
            )

            val result = repo.updateAppointmentDetail(
                token = token,
                requestBody = requestBody
            )

            result.fold(
                onSuccess = {
                    updateDetailState.value = UpdateAppointmentDetailUiState.Success(data = it)
                },
                onFailure = { exception ->
                    updateDetailState.value = UpdateAppointmentDetailUiState.Error(
                        exception.message ?: "Something went wrong"
                    )
                }
            )
        }
    }
    suspend fun onUploadNotesClicked(
        token: String,
        appointmentId: String
    ) {
        viewModelScope.launch {
            uploadNotes.value = UploadNotesUiState.Loading
            try {
                val consultationNotesState = consultationNotesState.value
                val notes = Notes(
                    chiefComplaint = consultationNotesState.chiefComplaint,
                    diagnosis = consultationNotesState.assessmentAndDiagnosis,
                    followupSelect = consultationNotesState.followUp,
                    hpi = consultationNotesState.historyOfPresentIllness,
                    physicalExamination = consultationNotesState.physicalExamination,
                    priority = consultationNotesState.priority,
                    treatment = consultationNotesState.treatmentPlan,
                    treatmentPlan = consultationNotesState.treatmentPlan
                )

                val orders = Orders(
                    expectedTimeline = consultationNotesState.expectedTimeline,
                    imaging = consultationNotesState.imagingStudies,
                    labTests = consultationNotesState.labTest,
                    referrals = consultationNotesState.referrals,
                    urgency = consultationNotesState.urgency,
                )

                val prescriptions = consultationNotesState.medications.map { med ->
                    Prescription(
                        dosage = med.dosage,
                        duration = med.duration,
                        frequency = med.frequency,
                        medicationName = med.medicationName,
                        specialInstructions = med.specialInstructions,
                        mealTiming = med.mealTime
                    )
                }

                val requestBody = UploadNotesRequestBody(
                    followUpDate = null,
                    notes = notes,
                    orders = orders,
                    prescription = prescriptions,
                    prescriptionLanguage ="en",
                )
                val result = repo.uploadNotesRepo(
                    token = token,
                    appointmentId = appointmentId,
                    requestBody = requestBody
                )
                result.onSuccess { response ->
                    uploadNotes.value = UploadNotesUiState.Success(data = response)
                }.onFailure { error ->
                    uploadNotes.value = UploadNotesUiState.Error(
                        message = error.message ?: "Failed to upload notes"
                    )
                }
            } catch (e: Exception) {
                uploadNotes.value = UploadNotesUiState.Error(
                    message = e.message ?: "An unexpected error occurred"
                )
            }
        }
    }
    // reset consultation notes fields back to empty
    fun resetConsultationNotesState() {
        consultationNotesState.value = ConsultationNotesUpdate1()
    }

    // reset upload notes network state back to idle
    fun resetUploadNotesState() {
        uploadNotes.value = UploadNotesUiState.Idle
    }

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
        token: String,
        appointmentId: String
    ) {
        viewModelScope.launch {
            labResultState.value = CurrentLabResultUiState.Loading

            try {
                val result = repo.particularPatientAppointmentDataRepo(
                    token = token,
                    appointmentId = appointmentId
                )
                result.onSuccess { result ->
                    particularPatientAppointmentDataState.value =
                        ParticularPatientAppointmentDataUiState.Success(data = result)
                }.onFailure { errorMessage ->
                    particularPatientAppointmentDataState.value =
                        ParticularPatientAppointmentDataUiState.Error(
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
        page: Int = 1,
        limit: Int = 10
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
    ) {
        viewModelScope.launch {
            medicalDocumentRecords.value = MedicalDocumentListUiState.Loading
            try {
                val result = repo.getAllMedicationDocumentListRepo(
                    token = token,
                    patientId = patientId,
                )
                result.onSuccess { response ->
                    medicalDocumentRecords.value =
                        MedicalDocumentListUiState.Success(data = response)
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
    suspend fun loadNextHistoryPage(token: String, patientId: String) {
        val currentState = historiesState.value
        if (currentState is HistoriesUiState.Success) {
            val totalpage = ceil(
                currentState.data.totalPages.toDouble() / (currentState.data.limit ?: 10)
            ).toInt()
            val nextPage = (currentState.data.page ?: 1) + 1
            if (nextPage <= totalpage) {
                getAllHistories(token, page = nextPage, patient = patientId)
            }
        }
    }

    // create helper function for load previous page
    suspend fun loadPreviousHistoryPage(token: String, patientId: String) {
        val currentState = historiesState.value
        if (currentState is HistoriesUiState.Success) {
            val prevPage = (currentState.data.page ?: 1) - 1
            if (prevPage >= 1) {
                getAllHistories(token, page = prevPage, patient = patientId)
            }
        }
    }

    // uplaod patient documents
    suspend fun UploadPatientDocumentClicked(
        context: Context,
        token: String,
        appointmentId: String,
        patientId: String
    ) {
        viewModelScope.launch {
            val state = uploadDocumentState.value
            if (state.attachmentURI != null) {
                val fileSize = context.contentResolver
                    .openFileDescriptor(state.attachmentURI, "r")?.statSize ?: 0
                val maxSize = 5 * 1024 * 1024 // 5MB in bytes
                if (fileSize > maxSize) {
                    uploadDocumentNetworkState.value = UploadPatientDocumentUIState.Error(
                        "File too large. Maximum size is 5MB"
                    )
                    return@launch
                }
            }
            uploadDocumentNetworkState.value = UploadPatientDocumentUIState.Loading
            try {
                val result = repo.uploadPatientDocuments(
                    context = context,
                    token = token,
                    appointmentId = appointmentId,
                    patientId = patientId,
                    state = state
                )
                result.onSuccess {
                    uploadDocumentNetworkState.value =
                        UploadPatientDocumentUIState.Success(data = it)
                    // Reset form after success
                    uploadDocumentState.update { UploadDocumentUpdate1() }
                }.onFailure { error ->
                    uploadDocumentNetworkState.value =
                        UploadPatientDocumentUIState.Error(error.message ?: "Upload failed")
                }
            } catch (e: Exception) {
                uploadDocumentNetworkState.value =
                    UploadPatientDocumentUIState.Error(e.message ?: "Something went wrong")
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
sealed class HistoriesUiState {
    object Idle : HistoriesUiState()
    object Loading : HistoriesUiState()
    data class Success(val data: GetAllHistoryResponse) : HistoriesUiState()
    data class Error(val message: String) : HistoriesUiState()
}

// Medical Document list
sealed class MedicalDocumentListUiState {
    object Idle : MedicalDocumentListUiState()
    object Loading : MedicalDocumentListUiState()
    data class Success(val data: List<GetAllMedicalRecordsResponseItem>) :
        MedicalDocumentListUiState()

    data class Error(val message: String) : MedicalDocumentListUiState()
}

// upload patient document ui state
sealed class UploadPatientDocumentUIState {
    object Idle : UploadPatientDocumentUIState()
    object Loading : UploadPatientDocumentUIState()
    data class Success(val data: UploadDocumentResponse) : UploadPatientDocumentUIState()
    data class Error(val message: String) : UploadPatientDocumentUIState()
}

// upload notes
sealed class UploadNotesUiState {
    object Idle : UploadNotesUiState()
    object Loading : UploadNotesUiState()
    data class Success(val data: UploadNotesResponseBody) : UploadNotesUiState()
    data class Error(val message: String) : UploadNotesUiState()
}

// upload notes
sealed class UpdateAppointmentDetailUiState {
    object Idle : UpdateAppointmentDetailUiState()
    object Loading : UpdateAppointmentDetailUiState()
    data class Success(val data: UpdateAppointmentDetailResponse) : UpdateAppointmentDetailUiState()
    data class Error(val message: String) : UpdateAppointmentDetailUiState()
}