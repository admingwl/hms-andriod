package com.example.happydocx.ui.uiStates.SavePatientsState

data class TimeSlotsForAppointmentUiState(
    val dateState:String  = "",
    val isTimeExpanded:Boolean = false,
    val timeState:String="",
    val reasonForVisitState:String=""
)