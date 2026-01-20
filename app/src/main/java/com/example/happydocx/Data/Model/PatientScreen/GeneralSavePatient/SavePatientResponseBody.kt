package com.example.happydocx.Data.Model.PatientScreen.GeneralSavePatient

data class SavePatientResponseBody(
    val first_name:String,
    val middle_name:String,
    val last_name:String,
    val previous_last_name:String,
    val prefix:String,
    val age: SavePatientAgeResponse,
    val Age:String,
    val dateOfBirth:String,
    val gender:String,
    val maritalStatus:String,
    val religion:String,
    val race:String,
    val bloodGroup:String,
    val contactNumber:String,
    val landlineNumber:String,
    val email:String,
    val isDialysisPatient:String,
    val allergies:List<Any>,
    val createdBy:String,
    val companyId:String,
    val _id:String,
    val currentMedications:List<Any>,
    val createdAt:String,
    val updatedAt:String,
    val patientId:String,
)

data class SavePatientAgeResponse(
    val unit:String,
    val value:String
)
