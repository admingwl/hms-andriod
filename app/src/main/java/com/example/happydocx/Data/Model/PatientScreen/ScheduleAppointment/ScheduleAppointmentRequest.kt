package com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment

data class ScheduleAppointmentRequest(
    val appointmentDate:String,
    val appointmentTime:String,
    val patient:String,
    val reason:String
)
