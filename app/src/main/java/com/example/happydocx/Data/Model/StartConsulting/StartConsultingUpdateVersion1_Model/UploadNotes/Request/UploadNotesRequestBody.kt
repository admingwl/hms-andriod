package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request

data class UploadNotesRequestBody(
    val followUpDate: Any?,
    val notes: Notes,
    val orders: Orders,
    val prescription: List<Prescription>,
    val prescriptionLanguage: String,
//    val status: String
)