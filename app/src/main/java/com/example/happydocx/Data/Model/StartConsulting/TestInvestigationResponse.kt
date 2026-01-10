package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName

data class TestInvestigationResponse(
    val message: String,
    val data: TestInvestigationRecord
)

data class TestInvestigationRecord(
    @SerializedName("_id") val id: String,
    val patient: String,
    val physician: String,
    val appointment: String,
    val status: String,
    val investigation: MedicationInvestigation,
    val notes: MedicationNotes,
    val orders: LabOrders,
    val patientVitalSigns: List<VitalSignThree>,
    val encounterDate: String,
    val medicationOrders: List<MedicationOrder>,
    val investigationOrders: List<InvestigationOrder>, // Now has a specific type
    val prescription: List<Any>, // Keep as Any if always empty, or create a class if data appears
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("__v") val version: Int
)

data class MedicationInvestigation(
    val status: String,
    val problemDiagnosis: List<String>,
    val symptoms: List<String>
)

data class MedicationNotes(
    val priority: String
)

data class LabOrders(
    val labTests: List<String>
)

data class VitalSignThree(
    val bloodPressure: String,
    val heartRate: String,
    val temperature: String,
    val oxigenSaturation: String,
    val height: String,
    val weight: String,
    @SerializedName("_id") val id: String,
    val recordedAt: String
)

data class MedicationOrder(
    val genericName: String,
    val strength: String,
    val duration: String,
    @SerializedName("_id") val id: String
)

data class InvestigationOrder(
    val testName: String,
    val reason: String,
    @SerializedName("_id") val id: String
)