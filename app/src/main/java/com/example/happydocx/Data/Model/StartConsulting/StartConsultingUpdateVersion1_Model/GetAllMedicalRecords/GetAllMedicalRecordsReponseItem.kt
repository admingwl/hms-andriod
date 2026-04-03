package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllMedicalRecords

data class GetAllMedicalRecordsResponseItem(
    val __v: Int,
    val _id: String,
    val appointmentId: String,
    val documentName: String,
    val documentType: String,
    val fileType: String,
    val fileUrl: String,
    val patientId: String,
    val reportDate: String,
    val signedUrl: String,
    val testResult: String,
    val uploadedAt: String,
    val uploadedBy: UploadedByObject?
)

data class UploadedByObject(
    val _id:String,
    val salutation:String?,
    val first_name:String,
    val last_name:String,
    val status:String,
    val id:String
)