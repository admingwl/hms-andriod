package com.example.happydocx.ui.uiStates.SavePatientsState

data class SavePatientGeneralUiState(
    val contactNumber:String="",
    val email:String="",
    val prefix:String = "",
    val firstName:String="",
    val middleName:String="",
    val lastName:String="",
    val previousLastName:String="",
    val dateOfBirth:String="",
    val isGenderExpanded:Boolean = false,
    val genderState:String="",
    val isMaritalExpanded:Boolean = false,
    val maritalState:String="",
    val religion:String="",
    val race:String="",
    val bloodGroup:String="",
    val landLineNumber:String="",
    val age:String="",
)