package com.example.happydocx.ui.uiStates.StartConsulting

data class PatientDocumentUploadUiState(
    val documentDiscription:String="",
    val testResultSelected:String="",
    val testResultExpandingState: Boolean = false
)
