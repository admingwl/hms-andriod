package com.example.happydocx.Data.Model.StartConsulting

data class UpdateAppointmentStatusResponseBody(
    val message:String,
    val data: UpdateAppointmentDataResponse
)

data class UpdateAppointmentDataResponse(
    val _id:String,
    val firstName:String,
    val lastName:String,
    val gender:String,
    val appointmentData: String,
    val appointmentTime:String,
    val department:String,
    val doctor:String,
    val visitType:String,
    val status:String,
    val nextAppointmentDateTime:String,
    val reason:String,
    val reasonForCancellation:String
)