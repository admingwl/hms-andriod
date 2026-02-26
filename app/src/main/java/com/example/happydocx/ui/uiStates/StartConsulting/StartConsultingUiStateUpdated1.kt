package com.example.happydocx.ui.uiStates.StartConsulting

data class StartConsultingUiStateUpdated1(
  // save Vital Signs State
    val heartRate:String = "",
    val oxygenSaturation:String = "",
    val systolicBp:String = "",
    val diastolicBp:String = "",
    val respiratoryRate:String = "",
    val temperature:String = "",
    val weight:String = "",
)

// add medication ui state
data class AddMedicationUpdated1(
        val medicationName:String="",
        val medicationDosage:String="",
        val medicationFrequency:String = "",
        val medicationDuration:String="",
        val medicationRoute:String ="",
        val medicationTiming:String = "",
        val medicationDate:String="",
        val medicationNotes:String=""
)