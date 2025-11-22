package com.example.happydocx.ui.ViewModels.StartConsulting

import androidx.compose.animation.core.copy
import androidx.lifecycle.ViewModel
import com.example.happydocx.ui.uiStates.StartConsulting.StartConsultingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BasicInformationViewModel : ViewModel() {

    private val state = MutableStateFlow(StartConsultingUiState())
    val _state = state.asStateFlow()


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
        state.update { it ->
            val updatedSymptoms = (it.selectedSymptoms + newSymptom).distinct()
            it.copy(
                selectedSymptoms = updatedSymptoms,
                symptomsExpandingState = false,
                symptomsSearchQuery = ""
            )
        }
    }

    fun onDiagnosisSelected(newDiagnosis: String) {
        state.update { it ->
            val updatedDiagnosis = (it.selectedDiagnosis + newDiagnosis).distinct()
            it.copy(
                selectedDiagnosis = updatedDiagnosis,
                diagnosisExpandingState = false,
                diagnosisSearchQuery = ""
            )
        }
    }

    fun onMedicationSelected(newMedication: String) {
        state.update { it ->
            val updatedMedication = (it.selectedMedication + newMedication).distinct()
            it.copy(
                selectedMedication = updatedMedication,
                medicationExpandingState = false,
                medicationSearchQuery = ""
            )
        }
    }

    fun ontestInvestigationSelected(newTest: String) {
        state.update { it ->
            val updatedTest = (it.selectedTest + newTest).distinct()
            it.copy(
                selectedTest = updatedTest,
                testInvestigationExpandingState = false,
                testSearchQuery = ""
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
            it.copy(symptomsExpandingState = isExpanded?:!it.symptomsExpandingState)
        }
    }

    fun onDiagnosisDropdownToggle(isExpanded: Boolean? = null) {
        state.update {
            it.copy(diagnosisExpandingState = isExpanded?:!it.diagnosisExpandingState)
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
    fun onSymptomRemove(symptom: String) {
        state.update {
            val updatedState = it.selectedSymptoms.toMutableList()
            updatedState.remove(symptom)
            it.copy(selectedSymptoms = updatedState)
        }
    }

    fun onDiagnosisRemove(diagnosis: String) {
        state.update { currentState ->
            // first convert to mutable list
            val updatedDiagnosis = currentState.selectedDiagnosis.toMutableList()
            // them remove
            updatedDiagnosis.remove(diagnosis)
            currentState.copy(
                selectedDiagnosis = updatedDiagnosis
            )
        }
    }

    fun onMedicationRemoved(medication: String) {
        state.update {
            val updatedMedication =
                it.selectedMedication.toMutableList().apply { remove(medication) }
            it.copy(selectedMedication = updatedMedication)
        }
    }

    fun onTestInvestigationRemoved(test: String) {
        state.update {
            val updatedTest = it.selectedTest.toMutableList().apply { remove(test) }
            it.copy(selectedTest = updatedTest)
        }
    }

    // adding state change for adding symptoms

    fun onBloodPressureAdded(newBloodPressure:String){
          state.update { it->
              it.copy(
                  bloodPressure = newBloodPressure
              )
          }
    }
    fun onHeartRateAdded(newHeartRate:String){
        state.update { it->
            it.copy(
                heartRate = newHeartRate
            )
        }
    }
    fun onTempratureAdded(newTemprature:String){
        state.update { it->
            it.copy(
                temperature = newTemprature
            )
        }
    }
    fun onOxygenSaturationAdded(newOxygen:String){
        state.update { it->
            it.copy(
                oxygenSaturation = newOxygen
            )
        }
    }
    fun onHeightAdded(newHeight:String){
        state.update { it->
            it.copy(
                height = newHeight
            )
        }
    }
    fun onWeightAdded(newWeight:String){
        state.update { it->
            it.copy(
                weight = newWeight
            )
        }
    }
}