package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.PatientHistory

data class Slot(
    val _id: String,
    val id: String,
    val isBooked: Boolean,
    val period: String,
    val slotTime: String
)