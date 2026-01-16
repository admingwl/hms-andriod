package com.example.happydocx.Data.Model.PatientScreen

data class PatientListResponse(
    val patients: List<PatientsList>,
    val total:Int
)

data class PatientsList(
    val _id: String,
    val first_name: String,
    val middle_name:String,
    val last_name:String,
    val previous_last_name:String,
    val prefix:String,
    val age: PatientListResponseAge,
    val Age:String,
    val address:AddressPatientListResponse?,
    val dateOfBirth:String,
    val gender:String,
    val maritalStatus:String,
    val religion:String,
    val race:String,
    val bloodGroup:String,
    val contactNumber:String,
    val landlineNumber:String,
    val email:String,
    val isDialysisPatient:Boolean,
    val allergies:List<String>,
    val conditions:List<ConditionsPatientListResponse>,
    val createdBy:String,
    val companyId:String,
    val currentMedications:List<CurrentMedicationPatientListResponse>,
    val createdAt:String,
    val updateAt:String,
    val patientId:String,
    )

data class PatientListResponseAge(
    val value:Int,
    val unit:String
)

data class CurrentMedicationPatientListResponse(
    val name:String,
    val dosage:String,
    val frequency:String,
    val duration:String,
    val instructions:String,
    val route:String,
    val timing:String,
    val startDate:String,
    val active:Boolean,
    val _id:String
)

data class ConditionsPatientListResponse(
    val condition:String,
    val onsetDate:String,
    val status: String,
    val severity:String,
    val _id:String
)

data class AddressPatientListResponse(
    val addressType:String,
    val addressLine1:String
)