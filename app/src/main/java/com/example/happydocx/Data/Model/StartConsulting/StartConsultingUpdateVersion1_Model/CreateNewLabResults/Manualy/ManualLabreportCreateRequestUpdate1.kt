package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy

data class ManualLabReportCreateRequestUpdate1(
    val consultation: Any? = null,
    val doctor:String,
    val entryType:String = "manual",
    val normalRange:String,
    val notes:String,
    val patient:String,
    val resultValue:String,
    val status:String,
    val testDate:String,
    val testName:String,
    val unit:String
)
