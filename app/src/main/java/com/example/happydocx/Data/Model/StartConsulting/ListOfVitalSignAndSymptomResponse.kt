package com.example.happydocx.Data.Model.StartConsulting

data class ListOfVitalSignAndSymptomResponse(
   val data:List<ParticularPatient> = emptyList()
)

data class ParticularPatient(
    val bloodPressure:String?,
    val heartRate:String?,
    val temprature:String?,
    val oxigenSaturation:String?,
    val height:String?,
    val weight:String?,
    val recordedAt:String?,
)
