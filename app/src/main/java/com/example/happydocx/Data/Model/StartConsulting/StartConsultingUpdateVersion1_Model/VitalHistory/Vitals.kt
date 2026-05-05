package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.VitalHistory

data class Vitals(
    val bpDiastolic: Int,
    val bpSystolic: Int,
    val heartRate: Int,
    val oxygenSaturation: Int,
    val recordedAt: String,
    val respiratoryRate: Int,
    val temperature: Double,
    val weight: Int
)