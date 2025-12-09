package com.example.happydocx.Data.Model.StartConsulting

data class TestAndInvestigationResponse(
    val message: String,
    val data: TestAndInvestigationMedicalRecord
)

data class TestAndInvestigationMedicalRecord(
    val investigation: InvestigationOfTestAndInvestigation,
    val _id: String,
    val patient: String,
    val physician: String,
    val appointment: String,
    val status: String,
    val medicationOrders: List<TestAndInvestigationMedicationOrder>,
    val encounterDate: String,
    val patientVitalSigns: List<Any>,
    val investigationOrders: List<TestAndInvestigationOrder>,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class InvestigationOfTestAndInvestigation(
    val status: String,
    val problemDiagnosis: List<String>,
    val symptoms: List<String>
)

data class TestAndInvestigationMedicationOrder(
    val genericName: String,
    val strength: String,
    val duration: String,
    val _id: String
)
data class TestAndInvestigationOrder(
    val testName: String,
    val reason: String,
    val _id:String
)