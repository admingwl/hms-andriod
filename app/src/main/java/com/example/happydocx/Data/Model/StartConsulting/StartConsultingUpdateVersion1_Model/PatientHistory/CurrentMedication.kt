package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.PatientHistory

data class CurrentMedication(
    val _id: String,
    val active: Boolean,
    val dosage: String,
    val duration: String,
    val frequency: String,
    val instructions: String,
    val name: String,
    val route: String,
    val startDate: String,
    val timing: String
)