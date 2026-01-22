package com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment

data class scheduleAppointmentTimeResponse(
    val date:String,
    val slots:List<AppointmentTimeSlots>
)
data class AppointmentTimeSlots(
    val slotTime:String,
    val period:String,
    val isBooked:Boolean,
    val _id:String
)