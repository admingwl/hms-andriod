package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse

data class Appointment(
    val __v: Int,
    val _id: String,
    val appointmentDate: String,
    val appointmentId: String,
    val appointmentTime: String,
    val companyId: String,
    val createdAt: String,
    val createdBy: String,
    val department: String,
    val doctor: String,
    val nextAppointmentDateTime: String,
    val nextAppointmentReminderSent: Boolean,
    val patient: String,
    val reason: String,
    val status: String,
    val tokenNumber: Any,
    val visitType: String
)