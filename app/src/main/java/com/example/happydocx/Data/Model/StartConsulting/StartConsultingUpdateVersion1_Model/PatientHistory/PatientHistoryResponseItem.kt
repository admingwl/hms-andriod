package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.PatientHistory

data class PatientHistoryResponseItem(
    val __v: Int,
    val _id: String?,
    val appointment: Appointment,
    val createdAt: String,
    val encounterDate: String,
    val investigation: Investigation,
    val investigationOrders: List<InvestigationOrder> = emptyList(),
    val medicationOrders: List<MedicationOrder>,
    val notes: Notes,
    val orders: Orders,
    val patient: Patient,
    val patientVitalSigns: List<PatientVitalSign> = emptyList(),
    val physician: Physician,
    val prescription: List<Any>,
    val status: String?,
    val updatedAt: String?
)