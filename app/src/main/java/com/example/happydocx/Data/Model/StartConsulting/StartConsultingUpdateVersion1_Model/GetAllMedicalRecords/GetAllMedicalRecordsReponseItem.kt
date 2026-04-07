package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllMedicalRecords

data class GetAllMedicalRecordsResponseItem(
    val _id: String?,
    val documentName: String?,
    val documentType: String?,
    val reportDate: String?,
    val signedUrl: String?,
    val uploadedBy: UploadedByObject?,
    val author:String?
)

data class UploadedByObject(
    val _id:String,
    val salutation:String?,
    val first_name:String,
    val last_name:String,
    val status:String,
    val id:String
)