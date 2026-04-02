package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.PatientHistory

data class AppointmentSlot(
    val _id: String,
    val date: String,
    val id: String,
    val slots: List<Slot>
)