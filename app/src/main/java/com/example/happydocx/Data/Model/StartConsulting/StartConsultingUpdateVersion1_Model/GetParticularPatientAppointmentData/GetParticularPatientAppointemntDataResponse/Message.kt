package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetParticularPatientAppointmentData.GetParticularPatientAppointemntDataResponse

data class Message(
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
    val patient: Patient,
    val status: String,
    val tokenNumber: Any,
    val visitType: String
)