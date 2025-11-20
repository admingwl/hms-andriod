package com.example.happydocx.ui.uiStates.StartConsulting

import androidx.compose.runtime.Immutable

@Immutable
data class StartConsultingUiState(
    val scheduleState:String= "",
    val nextScheduleDate:String="",
    val symptoms:String="",
    // allow user to select more than one symptom
    val selectedSymptoms:List<String> = emptyList(),
    val symptomsExpandingState:Boolean=false,
    val diagnosis:String="",
    // allow user to select more than one Diagnosis
    val selectedDiagnosis:List<String> = emptyList(),
    val diagnosisExpandingState:Boolean=false,
    val notes:String="",
    val testSearchQuery:String="",
    val medicationSearchQuery:String="",
    val symptomsSearchQuery:String="",
    val diagnosisSearchQuery:String="",
    val medication:String="",
    // allow user to select more than one Medication
    val selectedMedication:List<String> = emptyList(),
    val medicationExpandingState:Boolean=false,
    val testInvestigation:String="",
    // allow user to select more than one Test
    val selectedTest: List<String> = emptyList(),
    val testInvestigationExpandingState:Boolean=false,
)
