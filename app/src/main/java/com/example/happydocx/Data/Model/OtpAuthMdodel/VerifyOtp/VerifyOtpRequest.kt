package com.example.happydocx.Data.Model.OtpAuthMdodel.VerifyOtp

data class VerifyOtpRequest(
    val otp:String,
    val phone:String,
    val role:String = "doctor"
)