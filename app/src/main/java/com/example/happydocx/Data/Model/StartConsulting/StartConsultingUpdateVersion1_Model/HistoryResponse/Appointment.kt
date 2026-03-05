package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.HistoryResponse

data class Appointment(
    val __v: Int,
    val _id: String,
    val appointmentDate: String,
    val appointmentId: String,
    val appointmentTime: String,
    val companyId: String,
    val createdAt: String,
    val createdBy: String,
    val department: Department,
    val doctor: Doctor,
    val nextAppointmentDateTime: Any,
    val nextAppointmentReminderSent: Boolean,
    val patient: String,
    val reason: String,
    val status: String,
    val tokenNumber: Any,
    val visitType: String
)