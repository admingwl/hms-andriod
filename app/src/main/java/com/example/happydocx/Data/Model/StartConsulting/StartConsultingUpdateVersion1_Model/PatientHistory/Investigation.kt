package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.PatientHistory

data class Investigation(
    val notes: String?,
    val problemDiagnosis: List<ProblemDiagnosis>,
    val status: String?,
    val symptoms: List<Symptom>
)