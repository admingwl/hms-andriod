package com.example.happydocx.Data.Model.StartConsulting

data class vitalSignList(
    val total:Int,
    val vitalSigns:List<ParticularPatient>
)
data class ParticularPatient(
    val bloodPressure:String?,
    val heartRate:String?,
    val temperature:String?,
    val oxygenSaturation:String?,
    val height:String?,
    val weight:String?,
    val _id:String?,
    val recordedAt:String?,
)
