package com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment

data class ScheduleAppointmentResponse(
    val success:String,
    val message:String,
    val data: ScheduleAppointmentResponsePatientDetail
)

data class ScheduleAppointmentResponsePatientDetail(
    val patient:String?,
    val appointmentDate:String?,
    val appointmentTime:String,
    val nextAppointmentReminderSent:Boolean?,
    val nextAppointmentDateTime:String?,
    val department:String?,
    val doctor:String?,
    val visitType:String?,
    val status:String?,
    val createdBy:String?,
    val companyId:String?,
    val _id:String?,
    val createdAt:String?,
    val appointmentId:String?
)