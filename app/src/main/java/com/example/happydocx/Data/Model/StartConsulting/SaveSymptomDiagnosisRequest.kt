package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName


data class SaveSymptomDiagnosisRequest(
    @SerializedName("patient")
    val patientId: String,
    @SerializedName("appointmentId")
    val appointmentId: String,
    @SerializedName("physicianId")
    val physicianId: String,
    @SerializedName("investigation")
    val investigation: InvestigationData,
)

data class InvestigationData(
    @SerializedName("notes")
    val notes: String,
    @SerializedName("problemDiagnosis")
    val problemDiagnosis: List<AssessmentItem>,
    @SerializedName("symptoms")
    val symptoms: List<AssessmentItem>
)

data class AssessmentItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("severity")
    val severity: String,
    @SerializedName("duration")
    val duration: String
)