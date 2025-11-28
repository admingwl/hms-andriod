package com.example.happydocx.ui.ViewModels.StartConsulting

import androidx.compose.animation.core.updateTransition
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.StartConsulting.AppointmentApiResponse
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