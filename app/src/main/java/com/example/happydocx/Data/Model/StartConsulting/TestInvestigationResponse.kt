package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName

data class TestInvestigationResponse(
    val message: String,
    val data: TestInvestigationRecord
)

data class TestInvestigationRecord(
   val investigation:InvestigationTestAndInvestigationResponse,
    val notes:NotesTestAndInvestigationResponse,
    val orders:OrdersTestAndInvestigationResponse,
    val _id:String,
    val patient:String,
    val physician:String,
    val appointment:String,
    val status:String,
    val encounterDate:String,
    val patientVitalSigns:List<PatientVitalSignsTestAndInvestigationResponse>,
    val medicalOrders:List<MedicationOrdersTestAndInvestigationResponse>,
    val investigationOrders:List<InvestigationOrdersTestAndInvestigationResponse>,
    val prescription:List<Any>,
    val createdAt:String,
    val updatedAt:String,
)

data class InvestigationTestAndInvestigationResponse(
    val problemDiagnosis:List<ProblemDiagnosisTestAndInvestigationResponse>,
    val notes:String,
    val symptoms:List<SymptomsTestAndInvestigationResponse>,
    val status:String,

)
data class ProblemDiagnosisTestAndInvestigationResponse(
    val diagnosis: String,
    val severity:String,
    val duration:String,
    val _id:String
)

data class SymptomsTestAndInvestigationResponse(
    val symptom:String,
    val severity:String,
    val duration:String,
    val _id:String
)

data class NotesTestAndInvestigationResponse(
    val priority:String,
)

data class OrdersTestAndInvestigationResponse(
    val labTests:List<Any>
)

data class PatientVitalSignsTestAndInvestigationResponse(
    val bloodPressure:String,
    val heartRate:String,
    val temperature:String,
    val oxygenSaturation:String,
    val height:String,
    val weight:String,
    val _id:String,
    val recordedAt:String

)
data class MedicationOrdersTestAndInvestigationResponse(
    val genericName:String,
    val strength:String,
    val duration:String,
    val _id:String,
)

data class InvestigationOrdersTestAndInvestigationResponse(
    val testName:String,
    val reason:String,
    val _id:String
)