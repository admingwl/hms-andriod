package com.example.happydocx.ui.uiStates.StartConsulting

import androidx.compose.runtime.Immutable

@Immutable
data class MedicalEntry(
    val name:String,
    val duration:String = "",
    val severity:String = "",
)

data class MedicationEntry(
    val medicationName:String,
    val duration:String="",
    val quantity:String=""
)
@Immutable
data class StartConsultingUiState(
    val scheduleState:String= "",
    val nextScheduleDate:String="",
    val symptoms:String="",
    // allow user to select more than one symptom
    val selectedSymptoms:List<MedicalEntry> = emptyList(),
    val symptomsExpandingState:Boolean=false,
    val diagnosis:String="",
    // allow user to select more than one Diagnosis
    val selectedDiagnosis:List<MedicalEntry> = emptyList(),
    val diagnosisExpandingState:Boolean=false,
    val notes:String="",
    val testSearchQuery:String="",
    val medicationSearchQuery:String="",
    val symptomsSearchQuery:String="",
    val diagnosisSearchQuery:String="",
    val medication:String="",
    // allow user to select more than one Medication
    val selectedMedication:List<MedicationEntry> = emptyList(),
    val medicationExpandingState:Boolean=false,
    val testInvestigation:String="",
    // allow user to select more than one Test
    val selectedTest: List<String> = emptyList(),
    val testInvestigationExpandingState:Boolean=false,
    // symptoms adding state
    val bloodPressure:String="",
    val heartRate:String="",
    val temperature:String="",
    val oxygenSaturation:String="",
    val height:String="",
    val weight:String="",
    )
