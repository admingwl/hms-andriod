package com.example.happydocx.Data.Model.StartConsulting

data class SaveSendVitalSignsAndSymptomsRequestBody(
    val patient: String,
    val appointment: String,
    val physicianId: String,
    val patientVitalSigns: List<PatientVitalSigns>
)

data class PatientVitalSigns(
    val bloodPressure:String,
    val heartRate:String,
    val temperature:String,
    val oxigenSaturation:String,
    val height:String,
    val weight:String,
)
