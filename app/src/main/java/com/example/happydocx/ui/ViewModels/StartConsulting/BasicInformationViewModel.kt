    package com.example.happydocx.ui.ViewModels.StartConsulting
    
    import android.util.Log
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.happydocx.Data.Model.StartConsulting.AppointmentApiResponse
    import com.example.happydocx.Data.Model.StartConsulting.AssessmentItem
    import com.example.happydocx.Data.Model.StartConsulting.InvestigationData
    import com.example.happydocx.Data.Model.StartConsulting.ListOfVitalSignAndSymptomResponse
    import com.example.happydocx.Data.Model.StartConsulting.MedicationOrder
    import com.example.happydocx.Data.Model.StartConsulting.MedicationOrders
    import com.example.happydocx.Data.Model.StartConsulting.MedicationRequest
    import com.example.happydocx.Data.Model.StartConsulting.MedicationResponse
    import com.example.happydocx.Data.Model.StartConsulting.ParticularPatient
    import com.example.happydocx.Data.Model.StartConsulting.PatientVitalSigns
    import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsAndSymptomsRequestBody
    import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsResponseBody
    import com.example.happydocx.Data.Model.StartConsulting.SaveSymptomDiagnosisRequest
    import com.example.happydocx.Data.Model.StartConsulting.TestAndInvestigationOrder
    import com.example.happydocx.Data.Model.StartConsulting.TestAndInvestigationOrders
    import com.example.happydocx.Data.Model.StartConsulting.TestAndInvestigationRequest
    import com.example.happydocx.Data.Model.StartConsulting.TestAndInvestigationResponse
    import com.example.happydocx.Data.Repository.StartConsulting.BasicInformationRepository
    import com.example.happydocx.ui.uiStates.StartConsulting.InvestigationEntry
    import com.example.happydocx.ui.uiStates.StartConsulting.MedicalEntry
    import com.example.happydocx.ui.uiStates.StartConsulting.MedicationEntry
    import com.example.happydocx.ui.uiStates.StartConsulting.StartConsultingUiState
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.flow.update
    import kotlinx.coroutines.launch
    
    class BasicInformationViewModel : ViewModel() {
    
    
        // get repo object
        val repo = BasicInformationRepository()
        private val state = MutableStateFlow(StartConsultingUiState())
        val _state = state.asStateFlow()
    
        // state for handling the api response (appointments{id})
        private val apiState =
            MutableStateFlow<BasicInformationUiState>(BasicInformationUiState.Loading)
        val _apiState = apiState.asStateFlow()
    
        // submissions state
        private val submissionState = MutableStateFlow<SubmitDiagnosisNotesSymptomsUiState>(SubmitDiagnosisNotesSymptomsUiState.Idle)
        val _submitState = submissionState.asStateFlow()
    
        // send vital signs and symptoms state
        private val saveVitalSignsState: MutableStateFlow<SaveVitalSignsUiState> = MutableStateFlow(SaveVitalSignsUiState.Idle)
        val _saveVitalSignState = saveVitalSignsState.asStateFlow()

        private val listOfVitalSignAndSymptoms: MutableStateFlow<VitalSignAndSymptomsList> = MutableStateFlow(VitalSignAndSymptomsList.Loading)
        val _listOfVitalSignAndSymptoms = listOfVitalSignAndSymptoms.asStateFlow()

        // send medication state
        private val sendMedication: MutableStateFlow<MedicationUiState> = MutableStateFlow(MedicationUiState.Idle)
        val _sendMedication = sendMedication.asStateFlow()

        // get state for test and investigation
        private val testAndInvestigation:MutableStateFlow<TestAndInvestigation> = MutableStateFlow(TestAndInvestigation.Idle)
        val _testAndInvestigation = testAndInvestigation.asStateFlow()
    
    
        // function for the api response
         fun onStartConsultingClicked(
            appointmentId: String,
            token:String,
        ) {
            viewModelScope.launch {
                // set loading state
                apiState.value = BasicInformationUiState.Loading
                try {
                    val result = repo.getBasicInformation(appointmentId = appointmentId,token=token)
                    result.fold(
                        onSuccess = { appointmentData ->
                            apiState.value = BasicInformationUiState.Success(data = appointmentData)
                        },
                        onFailure = { exception ->
                            apiState.value = BasicInformationUiState.Error(
                                message = exception.message ?: "Failed to load appointment details...."
                            )
                        }
                    )
    
                } catch (e: Exception) {
                    apiState.value = BasicInformationUiState.Error(
                        message = e.message ?: "An Unexpected error occurred"
                    )
                }
            }
        }
    
        // function when user click on send diagnosis and symptoms with notes
       suspend fun onSubmitClicked(
             patientId:String,
             appointmentId:String,
             physicianId:String,
             token:String
        ) {
    
            viewModelScope.launch {
                submissionState.value = SubmitDiagnosisNotesSymptomsUiState.Loading
    
                // prepare the data (map the data)
                val symptomList = state.value.selectedSymptoms.map { it ->
                    AssessmentItem(
                        name = it.name,
                        severity = it.severity,
                        duration = it.duration
                    )
                }
    
                val diagnosis = state.value.selectedDiagnosis.map { it ->
                    AssessmentItem(
                        name = it.name,
                        severity = it.severity,
                        duration = it.duration
                    )
                }
    
                val investigationData = InvestigationData(
                    notes = state.value.notes,
                    problemDiagnosis = diagnosis,
                    symptoms = symptomList
                )
    
                val requestBody = SaveSymptomDiagnosisRequest(
                    patientId = patientId,
                    appointmentId = appointmentId,
                    physicianId = physicianId,
                    investigation = investigationData
                )
    
                // call the repo function here
                val result = repo.submitSymptomsDiagnosisNotes(requestBody = requestBody, token = token)
    
                // handle the result
                result.fold(
                    onSuccess = {
                        submissionState.value = SubmitDiagnosisNotesSymptomsUiState.Success
                    },
                    onFailure = {
                        // API failed: Update state to Error
                        // The UI will observe this and show a Toast/Snackbar
                        submissionState.value = SubmitDiagnosisNotesSymptomsUiState.Error(
                            message = it.message ?: "Failed to submit data"
                        )
                    }
                )
            }
        }
        // 1. Function to clear Clinical Assessment inputs (Symptoms, Diagnosis, Notes)
        fun clearClinicalAssessmentFields() {
            state.update { current ->
                current.copy(
                    selectedSymptoms = emptyList(),
                    selectedDiagnosis = emptyList(),
                    notes = "",
                    symptomsSearchQuery = "",
                    diagnosisSearchQuery = ""
                )
            }
        }

        // 2. Function to reset the Submission State back to Idle
        fun resetSubmissionState() {
            submissionState.value = SubmitDiagnosisNotesSymptomsUiState.Idle
        }
    
        // function Save button  for save the vital signs and symptoms
         fun onSaveClicked(
            token:String,
            patientId:String,
            appointmentId:String,
            physicianId: String
        ) {
            viewModelScope.launch {
                // first set to loading
                saveVitalSignsState.value = SaveVitalSignsUiState.Loading
                val patientVitalSigns = listOf(
    
                    PatientVitalSigns(
                        bloodPressure = state.value.bloodPressure,
                        heartRate = state.value.heartRate,
                        temperature = state.value.temperature,
                        oxigenSaturation = state.value.oxygenSaturation,
                        height = state.value.height,
                        weight = state.value.weight
                    )
                )
                val requestBody = SaveSendVitalSignsAndSymptomsRequestBody(
                    patient = patientId,
                    appointment = appointmentId,
                    physicianId = physicianId,
                    patientVitalSigns = patientVitalSigns
                )
    
    
                // call repo function here
                val result = repo.sendVitalSignsAndSymptoms(
                    token = token,
                    requestBody = requestBody
                )
                // Handle the result
                result.fold(
                    onSuccess = {
                        saveVitalSignsState.value = SaveVitalSignsUiState.Success(data = it)
                        Log.d("SaveVitalSigns", "Successfully saved: ${it.id}")
                    },
                    onFailure = { exception ->
                        saveVitalSignsState.value = SaveVitalSignsUiState.Error(
                            message = exception.message ?: "Failed to save vital signs"
                        )
                        Log.e("SaveVitalSigns", "Error: ${exception.message}")
                    }
                )
            }
        }

        // function for send medication
        fun onSendMedicationClicked(
            token:String,
            patientId: String,
            appointmentId: String,
            physicianId: String
        ){
            viewModelScope.launch {
                // initially i set it to loading
                sendMedication.value = MedicationUiState.Loading
                val medicationOrder = state.value.selectedMedication.map { medication->
                    MedicationOrders(
                        medicationName = medication.medicationName,
                        dosage = medication.quantity,
                        duration = medication.duration
                    )
                }

                // create request body
                val requestBody = MedicationRequest(
                    patient = patientId,
                    medicationOrders = medicationOrder,
                    appointment = appointmentId,
                    physicianId = physicianId
                )

                val result = repo.sendMedicationReport(
                    token = token,
                    requestBody = requestBody
                )
                // Handle the result
                result.fold(
                    onSuccess = { response ->
                        sendMedication.value = MedicationUiState.Success(data = response)
                        Log.d("SendMedication", "Successfully sent medications: ${response.message}")
                    },
                    onFailure = { exception ->
                        sendMedication.value = MedicationUiState.Error(
                            message = exception.message ?: "Failed to send medications"
                        )
                        Log.e("SendMedication", "Error: ${exception.message}")
                    }
                )
            }
        }
        // clear medication fields
        fun clearMedicationFields(

        ){
            state.update { it->
                it.copy(
                    selectedMedication = emptyList(),
                    medicationSearchQuery = ""
                )
            }
        }

        fun resetSendMedicationState() {
            sendMedication.value = MedicationUiState.Idle
        }

        // function for the testAndInvestigation
        fun sendTestAndInvestigationRepo(
            token:String,
            patientId: String,
            appointmentId: String,
            physicianId: String
        ){
            viewModelScope.launch {
                testAndInvestigation.value = TestAndInvestigation.Loading
               // Map the selected test to InvestigationOrder format
                val investigationOrderList = state.value.selectedTest.map { test->
                    TestAndInvestigationOrders(
                        testName = test.testInvestigationName,
                        reason = test.testInvestigationReason
                    )
                }
                // create the request body
                val requestBody = TestAndInvestigationRequest(
                    patient = patientId,
                    appointment = appointmentId,
                    physicianId = physicianId,
                    investigationOrders = investigationOrderList
                )

                // call repo function
                val result = repo.submitTestAndInvestigation(
                    token = token,
                    requestBody = requestBody
                )

                // Handle the result
                result.fold(
                    onSuccess = { response ->
                        testAndInvestigation.value = TestAndInvestigation.Success(data = response)
                        Log.d("TestInvestigation", "Successfully sent: ${response.message}")
                    },
                    onFailure = { exception ->
                        testAndInvestigation.value = TestAndInvestigation.Error(
                            message = exception.message ?: "Failed to send test & investigation"
                        )
                        Log.e("TestInvestigation", "Error: ${exception.message}")
                    }
                )
            }
        }
        // Clear test & investigation fields
        fun clearTestInvestigationFields() {
            state.update { it ->
                it.copy(
                    selectedTest = emptyList(),
                    testSearchQuery = ""
                )
            }
        }

        // Reset test & investigation state
        fun resetTestInvestigationState() {
            testAndInvestigation.value = TestAndInvestigation.Idle
        }
        // function to clear the inputs after successful save
        fun clearVitalSignField(){
            state.update { it->
                it.copy(
                    bloodPressure = "",
                    heartRate = "",
                    temperature = "",
                    oxygenSaturation = "",
                    height = "",
                    weight = ""
                )
            }
        }

        // add this above function to reset the api state so the toast not shown again on rotation
        fun resetSaveVitalSignState(){
            saveVitalSignsState.value = SaveVitalSignsUiState.Idle
        }
    
        // symptoms
        val Symtoms = listOf(
            "chest Pain",
            "Chronic Kidney Disease",
            "Shortness of breath",
            "Fatigue",
            "Headache",
            "Nausea",
            "Sore Throat",
            "Abdominal pain",
            "Diarrhea",
            "joint pain",
            "Back pain",
            "Loss of appetite",
            "Unintentional weight loss",
            "Swelling in legs or ankles",
            "Increase thirst",
            "Night Sweat",
            "Muscle Cramps",
            "Palpitations",
    
            )
    
        // diagnosis
        val diagnosis = listOf(
            "Diabetes Mellitus",
            "Coronary Artery Disease",
            "Asthma",
            "COPD",
            "Anemia",
            "Hyperlipidemia",
            "Anxiety Disorder",
            "Migraine",
            "GERD",
            "Rheumatoid Arthritis",
            "Osteoarthritis",
            "Chronic Kidney Disease",
            "Urinary Track Infection",
            "pneumonia",
            "Peptic Ulcer Disease",
            "Hepatitis B",
            "Hepatitis C",
            "Congestive Heart Failure"
        )
    
        // medication
        val medication = listOf<String>(
            "Amlodipine",
            "Atorvastatin",
            "Losartan",
            "Metformin",
            "Simvastatin",
            "Salbutamol",
            "Ferrous Sulphate",
            "Diazepam",
            "Sumatriptan",
            "Omeprazole",
            "Nitrofurantoin",
            "Azithromycin",
            "Rifampin",
            "Paracetamol",
            "Pantoprazole",
            "Entecavir",
            "Aspirin",
            "Carvedilol"
    
    
        )
    
        // test & investigation
        val testInvestigation = listOf(
            "Blood Glucose (Fasting)",
            "HbA1c",
            "TSH",
            "Urinalysis",
            "Electrolyte Panel",
            "C-Reactive Protein(CRP)",
            "(ESR)",
            "Vitamin D(25-Hydroxy)",
            "Vitamin B12",
            "INR",
            "Hepatitis B Surface Antigen",
            "Hepatitis C Antibody",
            "Urine Pregnancy Test",
            "Creatine Kinase (CK-MB)",
            "Arterial Blood Gas(ABG)"
        )
    
        // Event handlers functions
        fun onSymptomSelected(newSymptom: String) {
           state.update { it->
               // check if the symptom already exists
               if(it.selectedSymptoms.any{it.name == newSymptom}){
                   it.copy(symptomsExpandingState = false, symptomsSearchQuery =  "")
               }else{
                   val newItem = MedicalEntry(name = newSymptom)
                   it.copy(
                       selectedSymptoms = it.selectedSymptoms + newItem,
                       symptomsExpandingState =  false,
                       symptomsSearchQuery = ""
                   )
               }
           }
        }
    
        // 2. NEW: Update Symptom Details (Duration/Severity)
        fun onSymptomDetailUpdate(index:Int,duration:String?=null,severity:String?=null){
            state.update { currentState ->
                val mutableList = currentState.selectedSymptoms.toMutableList()
                val currentItem = mutableList[index]
    
                mutableList[index] = currentItem.copy(
                    duration = duration ?: currentItem.duration,
                    severity = severity ?: currentItem.severity
                )
                currentState.copy(selectedSymptoms = mutableList)
            }
        }
    
    
        fun onDiagnosisSelected(newDiagnosis: String) {
            state.update { currentState ->
                if (currentState.selectedDiagnosis.any { it.name == newDiagnosis }) {
                    currentState.copy(diagnosisExpandingState = false, diagnosisSearchQuery = "")
                } else {
                    val newItem = MedicalEntry(name = newDiagnosis)
                    currentState.copy(
                        selectedDiagnosis = currentState.selectedDiagnosis + newItem,
                        diagnosisExpandingState = false,
                        diagnosisSearchQuery = ""
                    )
                }
            }
        }
    
        // NEW: Update Diagnosis Details
        fun onDiagnosisDetailsUpdated(index: Int, duration: String? = null, severity: String? = null) {
            state.update { currentState ->
                val mutableList = currentState.selectedDiagnosis.toMutableList()
                val currentItem = mutableList[index]
    
                mutableList[index] = currentItem.copy(
                    duration = duration ?: currentItem.duration,
                    severity = severity ?: currentItem.severity
                )
                currentState.copy(selectedDiagnosis = mutableList)
            }
        }
    
        fun onMedicationSelected(newMedication: String) {
           state.update { it->
               if(it.selectedMedication.any{it.medicationName == newMedication}){
                   it.copy(medicationExpandingState = false, medicationSearchQuery = "")
               }else{
                   val newItem = MedicationEntry(medicationName = newMedication)
                   it.copy(
                       selectedMedication = it.selectedMedication + newItem,
                       medicationExpandingState = false,
                       medicationSearchQuery = ""
                   )
               }
           }
        }
    
        fun onMedicationUpdated(index:Int,duration:String?=null,frequency:String?=null){
            state.update { it->
                val mutableList = it.selectedMedication.toMutableList()
                val currentItem = mutableList[index]
                mutableList[index] = currentItem.copy(
                    duration = duration ?: currentItem.duration,
                    quantity = frequency ?: currentItem.quantity)
                it.copy(selectedMedication = mutableList)
            }
        }
    
        fun ontestInvestigationSelected(newTest: String) {
            state.update { it->
                if(it.selectedTest.any{it.testInvestigationName == newTest}){
                    it.copy(testInvestigationExpandingState = false, testSearchQuery = "")
                }else{
                    val newItem = InvestigationEntry(testInvestigationName = newTest)
                    it.copy(
                        selectedTest = it.selectedTest + newItem,
                        testInvestigationExpandingState = false,
                        testSearchQuery = ""
                    )
                }
            }
        }
    
        fun onTestInvestigationUpdated(index:Int,reason:String?=null){
            state.update { it->
                val mutableList = it.selectedTest.toMutableList()
                val currentItem = mutableList[index]
                mutableList[index] = currentItem.copy(
                    testInvestigationReason = reason ?: currentItem.testInvestigationReason
                )
                it.copy(selectedTest = mutableList
                )
            }
        }
    
        fun onNotes(newNote: String) {
            state.update { it ->
                it.copy(
                    notes = newNote
                )
            }
        }
    
        fun onSymptomDropdownToggle(isExpanded: Boolean? = null) {
            state.update {
                it.copy(symptomsExpandingState = isExpanded ?: !it.symptomsExpandingState)
            }
        }
    
        fun onDiagnosisDropdownToggle(isExpanded: Boolean? = null) {
            state.update {
                it.copy(diagnosisExpandingState = isExpanded ?: !it.diagnosisExpandingState)
            }
        }
    
        fun onMedicationDropdownToggle(isExpanded: Boolean? = null) {
            state.update {
                it.copy(medicationExpandingState = isExpanded ?: !it.medicationExpandingState)
            }
        }
    
        fun onTestDropdownToggle(isExpanded: Boolean? = null) {
            state.update {
                it.copy(
                    testInvestigationExpandingState = isExpanded ?: !it.testInvestigationExpandingState
                )
            }
        }
    
        // Search Query Handlers
        fun onSymptomSearchQueryChanged(newQuery: String) {
            state.update { it ->
                it.copy(
                    symptomsSearchQuery = newQuery
                )
            }
        }
    
        fun onDiagnosisSearchQueryChanged(newQuery: String) {
            state.update { it ->
                it.copy(
                    diagnosisSearchQuery = newQuery
                )
            }
        }
    
        fun onMedicationSearchQueryChanged(newQuery: String) {
            state.update { it ->
                it.copy(
                    medicationSearchQuery = newQuery
                )
            }
        }
    
        fun onTestSearchQueryChanged(newQuery: String) {
            state.update { it ->
                it.copy(
                    testSearchQuery = newQuery
                )
            }
        }
    
        // remove item functions
        fun onSymptomRemove(entry: MedicalEntry) {
          state.update { it->
              val updatedState  = it.selectedSymptoms.toMutableList()
              updatedState.remove(entry)
              it.copy(selectedSymptoms = updatedState)
          }
        }
    
        fun onDiagnosisRemove(entry: MedicalEntry) {
            state.update { currentState ->
                val updatedDiagnosis = currentState.selectedDiagnosis.toMutableList()
                updatedDiagnosis.remove(entry)
                currentState.copy(selectedDiagnosis = updatedDiagnosis)
            }
        }
    
        fun onMedicationRemoved(medication: MedicationEntry) {
            state.update {
               val updateMedication = it.selectedMedication.toMutableList()
                updateMedication.remove(medication)
                it.copy(selectedMedication = updateMedication)
            }
        }
    
        fun onTestInvestigationRemoved(test: InvestigationEntry) {
            state.update {
                val updatedTest = it.selectedTest.toMutableList()
                updatedTest.remove(test)
                it.copy(selectedTest = updatedTest)
            }
        }
    
        // adding state change for adding symptoms
    
        fun onBloodPressureAdded(newBloodPressure: String) {
            state.update { it ->
                it.copy(
                    bloodPressure = newBloodPressure
                )
            }
        }
    
        fun onHeartRateAdded(newHeartRate: String) {
            state.update { it ->
                it.copy(
                    heartRate = newHeartRate
                )
            }
        }
    
        fun onTempratureAdded(newTemprature: String) {
            state.update { it ->
                it.copy(
                    temperature = newTemprature
                )
            }
        }
    
        fun onOxygenSaturationAdded(newOxygen: String) {
            state.update { it ->
                it.copy(
                    oxygenSaturation = newOxygen
                )
            }
        }
    
        fun onHeightAdded(newHeight: String) {
            state.update { it ->
                it.copy(
                    height = newHeight
                )
            }
        }
    
        fun onWeightAdded(newWeight: String) {
            state.update { it ->
                it.copy(
                    weight = newWeight
                )
            }
        }
    
        fun onVitalSignSymptomResponseCardClicked(key:String,isExpanded:Boolean){
            state.update { current ->
                val updatedMap = current.VitalSignSymptomsCardExpandState.toMutableMap().apply {
                    this[key] = !isExpanded
                }
                current.copy(
                    VitalSignSymptomsCardExpandState = updatedMap
                )
            }
        }

        // fun on getting the list of all the vital signs and symptoms of a particular patient
        fun getListOfSymptomsAndVitalSigns(
            token:String,
            patientId:String
        ) {
            viewModelScope.launch {
                // set loading state
                listOfVitalSignAndSymptoms.value = VitalSignAndSymptomsList.Loading
                try {
                    // Debug logs
                    Log.d("API_CALL", "Token: Bearer ${token.take(10)}...")
                    Log.d("API_CALL", "PatientId: $patientId")
                    val result = repo.getAllSignsAndSymptomsList(patientId = patientId,token=token)
                    result.fold(
                        onSuccess = {
                            listOfVitalSignAndSymptoms.value = VitalSignAndSymptomsList.Success(data = it)
                        },
                        onFailure = { exception ->
                            listOfVitalSignAndSymptoms.value = VitalSignAndSymptomsList.Error(
                                message = exception.message ?: "Failed to load vital sign and symptom list...."
                            )
                        }
                    )

                } catch (e: Exception) {
                    listOfVitalSignAndSymptoms.value = VitalSignAndSymptomsList.Error(
                        message = e.message ?: "An Unexpected error occurred"
                    )
                }
            }
        }


    }
    
    sealed class BasicInformationUiState {
    
        object Loading : BasicInformationUiState()
    
        data class Success(
            val data: AppointmentApiResponse
        ) : BasicInformationUiState()
    
        data class Error(
            val message: String
        ) : BasicInformationUiState()
    }
    
    sealed class SubmitDiagnosisNotesSymptomsUiState{
        object Idle : SubmitDiagnosisNotesSymptomsUiState()
        object Loading : SubmitDiagnosisNotesSymptomsUiState()
        object Success: SubmitDiagnosisNotesSymptomsUiState()
        data class Error(val message: String) : SubmitDiagnosisNotesSymptomsUiState()
    }
    
    sealed class SaveVitalSignsUiState {
        object Idle : SaveVitalSignsUiState()
        object Loading : SaveVitalSignsUiState()
        data class Success(val data : SaveSendVitalSignsResponseBody) : SaveVitalSignsUiState()
        data class Error(val message: String) : SaveVitalSignsUiState()
    }

    // sealed class for the list of the vital signs and symptoms of the particular patient
    sealed class VitalSignAndSymptomsList {

        object Loading : VitalSignAndSymptomsList()

        data class Success(
            val data: List<ParticularPatient>
        ) : VitalSignAndSymptomsList()

        data class Error(
            val message: String
        ) : VitalSignAndSymptomsList()
    }

    // create sealed class for the send medication
    sealed class MedicationUiState{
        object Idle : MedicationUiState()
        object Loading : MedicationUiState()
        data class Success(val data: MedicationResponse) : MedicationUiState()
        data class Error(val message: String) : MedicationUiState()
    }

    sealed class TestAndInvestigation{
        object Idle : TestAndInvestigation()
        object Loading: TestAndInvestigation()
        data class Success(val data : TestAndInvestigationResponse): TestAndInvestigation()
        data class Error(val message:String): TestAndInvestigation()
    }

