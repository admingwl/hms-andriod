package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName

data class SaveSendVitalSignsResponseBody(
    @SerializedName("_id")
    val id: String,
    val patient: String,
    val physician: String,
    val appointment: String,
    val status: String,
    val investigation: SaveSendVitalResponseInvestigation,
    val medicationOrders: List<MedicationOrder>,
    val encounterDate: String,
    val patientVitalSigns: List<PatientVitalSign>,
    val investigationOrders: List<String>, // Empty array, adjust type if needed
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("__v")
    val version: Int
)
data class SaveSendVitalResponseInvestigation(
    val status: String,
    val problemDiagnosis: List<SaveSendVitalSignProblemDiagnosis>,
    val symptoms: List<Symptom>,
    val notes: String
)

data class SaveSendVitalSignProblemDiagnosis(
    val diagnosis: String,
    val severity: String,
    val duration: String,
    @SerializedName("_id")
    val id: String
)

data class Symptom(
    val symptom: String,
    val severity: String,
    val duration: String,
    @SerializedName("_id")
    val id: String
)

data class MedicationOrder(
    val genericName: String,
    val strength: String,
    val duration: String,
    @SerializedName("_id")
    val id: String
)

data class PatientVitalSign(
    val bloodPressure: String,
    val heartRate: String,
    val temperature: String,
    @SerializedName("oxigenSaturation")
    val oxygenSaturation: String,
    val height: String,
    val weight: String,
    @SerializedName("_id")
    val id: String,
    val recordedAt: String  // Add this field
)