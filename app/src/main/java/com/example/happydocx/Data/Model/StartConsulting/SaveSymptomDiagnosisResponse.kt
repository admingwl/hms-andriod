package com.example.happydocx.Data.Model.StartConsulting

data class SaveSymptomDiagnosisResponse(
    val message: String,
    val data: DataResponse
    )

data class DataResponse(
    val patient: String,
    val physician: String,
    val appointment: String,
    val investigation: Investigation
)

data class Investigation(
    val notes: String,
    val problemDiagnosis: List<ProblemDiagnosis>,
    val symptoms: List<Symptoms>
)

data class ProblemDiagnosis(
    val diagnosis: String,
    val severity: String,
    val duration: String,
)

data class Symptoms(
    val symptom: String,
    val severity: String,
    val duration: String
)

