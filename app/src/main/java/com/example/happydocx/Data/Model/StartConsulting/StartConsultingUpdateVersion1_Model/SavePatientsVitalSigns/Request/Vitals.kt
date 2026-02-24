package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request

data class Request_Vitals(
    val bpDiastolic: Int,
    val bpSystolic: Int,
    val heartRate: Int,
    val oxygenSaturation: Int,
    val recordedAt: String,
    val respiratoryRate: Int,
    val temperature: Int,
    val weight: Int
)