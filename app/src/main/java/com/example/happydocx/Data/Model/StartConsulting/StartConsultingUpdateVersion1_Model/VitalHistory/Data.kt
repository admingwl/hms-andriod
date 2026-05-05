package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.VitalHistory

data class Data(
    val _id: String,
    val bpDiastolic: Int,
    val bpSystolic: Int,
    val createdAt: String,
    val heartRate: Int,
    val oxygenSaturation: Int,
    val recordedAt: String,
    val respiratoryRate: Int,
    val temperature: Double,
    val vitals: Vitals,
    val weight: Int
)