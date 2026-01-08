package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName

data class SaveSendVitalSignsAndSymptomsRequestBody(
    @SerializedName("patient")
    val patientId: String?,

    @SerializedName("patientVitalSigns")
    val vitalSigns: List<PatientVitalSigns>?,

    @SerializedName("appointment")
    val appointmentId: String?,

    @SerializedName("physicianId")
    val physicianId: String?
)

data class PatientVitalSigns(
    @SerializedName("bloodPressure")
    val bloodPressure: String?,

    @SerializedName("heartRate")
    val heartRate: String?,

    @SerializedName("temperature")
    val temperature: String?,

    @SerializedName("oxygenSaturation")
    val oxygenSaturation: String?,

    @SerializedName("height")
    val height: String?,

    @SerializedName("weight")
    val weight: String?
)
