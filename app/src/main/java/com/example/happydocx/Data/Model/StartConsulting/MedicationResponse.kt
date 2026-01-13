package com.example.happydocx.Data.Model.StartConsulting

data class MedicationResponse(
    val message: String,
    val data: MedicalRecord
)

data class MedicalRecord(
    val investigation: InvestigationMedicalResponse,
    val notes: NotesMedicationResponse,
    val orders: OrdersMedicalResponse,
    val _id:String,
    val patient:String,
    val physician:String,
    val appointment:String,
    val status:String,
    val encounterDate:String,
    val patientVitalSigns:List<PatientVitalSignsMedicationResponse>,
    val medicationOrders:List<MedicationOrdersMedicationResponse>,
    val investigationOrders:List<Any>,
    val prescription:List<Any>,
    val createdAt:String,
    val updatedAt:String
)

data class InvestigationMedicalResponse(
    val problemDiagnosis: List<ProblemDiagnosisMedicationResponse>,
    val notes: String,
    val symptoms: List<SymptomsMedicationResponse>,
    val status: String,
)

data class ProblemDiagnosisMedicationResponse(
    val diagnosis: String,
    val severity: String,
    val duration: String,
    val _id: String
)

data class SymptomsMedicationResponse(
    val symptom: String,
    val severity: String,
    val duration: String,
    val _id: String
)

data class NotesMedicationResponse(
    val priority: String
)

data class OrdersMedicalResponse(
    val labTests: List<Any>
)

data class PatientVitalSignsMedicationResponse(
    val bloodPressure:String,
    val heartRate: String,
    val temperature:String,
    val oxygenSaturation:String,
    val height:String,
    val weight:String,
    val _id:String,
    val recordedAt:String,
)

data class MedicationOrdersMedicationResponse(
    val genericName:String,
    val strength:String,
    val duration:String,
    val _id:String
)

