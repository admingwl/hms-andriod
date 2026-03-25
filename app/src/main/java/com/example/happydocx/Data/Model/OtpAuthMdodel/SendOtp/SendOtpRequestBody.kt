package com.example.happydocx.Data.Model.OtpAuthMdodel.SendOtp

data class SendOtpRequestBody(
    val phone:String,
    // set by default role as doctor.
    val role:String = "doctor"
)