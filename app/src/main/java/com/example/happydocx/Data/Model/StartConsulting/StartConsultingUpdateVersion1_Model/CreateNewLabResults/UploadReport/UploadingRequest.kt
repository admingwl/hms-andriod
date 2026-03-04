package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.UploadReport

data class UploadingRequest(
    val category:String,
    val consultation:String,
    val doctor:String,
    val entryType:String,
    val notes:String,
    val patient:String,
    val status:String,
    val testDate:String,
    val testName:String
)