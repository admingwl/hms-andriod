package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request

data class Prescription(
    val dosage: String,
    val duration: String,
    val frequency: String,
    val mealTiming: String,
    val medicationName: String,
    val specialInstructions: String
)