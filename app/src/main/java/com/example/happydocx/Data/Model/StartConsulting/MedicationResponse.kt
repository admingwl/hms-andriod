package com.example.happydocx.Data.Model.StartConsulting

data class MedicationResponse(
    val message:String,
    val data:MedicalRecord,
)
data class MedicalRecord(
    val _id: String,
    val patient: String,
    val physician: String,
    val appointment: String,
    val status: String,
    val investigation: MedicationInvestigation,
    val medicationOrders: List<MedicationOrderss>,
    val encounterDate: String,
    val patientVitalSigns: List<Any>,
    val investigationOrders: List<Any>,
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class MedicationInvestigation(
    val status: String,
    val problemDiagnosis: List<String>,
    val symptoms: List<String>
)

data class MedicationOrderss(
    val genericName: String,
    val strength: String,
    val duration: String,
    val _id: String,
)
