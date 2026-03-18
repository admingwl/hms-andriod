package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request

data class Notes(
    val chiefComplaint: String,
    val diagnosis: String,
    val followupSelect: String,
    val hpi: String,
    val physicalExamination: String,
    val priority: String,
    val treatment: String,
    val treatmentPlan: String
)