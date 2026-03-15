package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadDocuements

data class Data(
    val __v: Int,
    val _id: String,
    val appointmentId: String,
    val documentName: String,
    val documentType: String,
    val fileType: String,
    val fileUrl: String,
    val patientId: String,
    val reportDate: String,
    val uploadedAt: String,
    val uploadedBy: String
)