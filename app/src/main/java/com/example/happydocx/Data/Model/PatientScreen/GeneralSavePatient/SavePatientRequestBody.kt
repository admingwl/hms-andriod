package com.example.happydocx.Data.Model.PatientScreen.GeneralSavePatient

data class SavePatientRequestBody(
    val Age:String,
    val age:SavePatientAge,
    val bloodGroup:String,
    val contactNumber:String,
    val dateOfBirth:String,
    val email:String,
    val first_name:String,
    val gender:String,
    val landlineNumber:String,
    val last_name:String,
    val maritalStatus:String,
    val middle_name:String,
    val prefix:String,
    val previous_last_name:String,
    val race:String,
    val religion:String
)

data class SavePatientAge(
    val unit:String,
    val value:String
)
