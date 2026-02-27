package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication

data class CreateMedicationRequest(
    val dosage:String,
    val duration:String,
    val frequency:String,
    val instructions:String,
    val medicationName:String,
    val route:String,
    val startDate:String,
    val timing:String
)