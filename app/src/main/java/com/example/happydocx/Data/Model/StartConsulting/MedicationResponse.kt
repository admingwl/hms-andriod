package com.example.happydocx.Data.Model.StartConsulting

data class MedicationResponse(
    val message: String,
    val data: MedicalRecord
)
data class MedicalRecord(
    val _id: String,
    val patient: String,
    val physician: String,
    val appointment: String,
    val status: String,
    val investigation: MedicationInvestigations,
    val notes: MedicationNote?, // Added
    val orders: LabOrder?,      // Added
    val medicationOrders: List<MedicationOrderss>,
    val encounterDate: String,
    val patientVitalSigns: List<VitalSignss>, // Changed from Any
    val investigationOrders: List<Any>,
    val prescription: List<Any>, // Added
    val createdAt: String,
    val updatedAt: String,
    val __v: Int
)

data class MedicationInvestigations(
    val status: String,
    val problemDiagnosis: List<String>,
    val symptoms: List<String>
)
data class MedicationNote(
    val priority: String
)

data class LabOrder(
    val labTests: List<String>
)
data class MedicationOrderss(
    val genericName: String,
    val strength: String,
    val duration: String,
    val _id: String
)

data class VitalSignss(
    val bloodPressure: String,
    val heartRate: String,
    val temperature: String,
    val oxigenSaturation: String,
    val height: String,
    val weight: String,
    val _id: String,
    val recordedAt: String
)
