package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName

data class SaveSendVitalSignsResponseBody(
    val _id: String?,
    val patient: String?,
    val physician: String?, // String kyunki JSON mein sirf ID hai
    val appointment: String?, // String kyunki JSON mein sirf ID hai
    val status: String?,
    val investigation: SaveSendVitalResponseInvestigation?,
    val notes: SaveSendNotes?, // Missing tha
    val orders: SaveSendOrders?, // Missing tha
    val medicationOrders: List<MedicationOrder>?,
    val patientVitalSigns: List<PatientVitalSign>?,
    val investigationOrders: List<InvestigationOrder>?, // Isse String se Object kar diya
    val encounterDate: String?,
    val createdAt: String?,
    val updatedAt: String?,
    @SerializedName("__v")
    val version: Int?
)
data class SaveSendNotes(
    val priority: String?
)

data class SaveSendOrders(
    val labTests: List<Any>?
)

data class SaveSendVitalResponseInvestigation(
    val status: String?,
    val problemDiagnosis: List<Any>?,
    val symptoms: List<Any>?
)

data class MedicationOrder(
    val genericName: String?,
    val strength: String?,
    val duration: String?,
    @SerializedName("_id")
    val id: String?
)

data class InvestigationOrder(
    val testName: String?,
    val reason: String?,
    @SerializedName("_id")
    val id: String?
)

data class PatientVitalSign(
    val bloodPressure: String?,
    val heartRate: String?,
    val temperature: String?,
    @SerializedName("oxygenSaturation") // Spelling theek kar di (y aayega)
    val oxygenSaturation: String?,
    val height: String?,
    val weight: String?,
    @SerializedName("_id")
    val id: String?,
    val recordedAt: String?
)