package com.example.happydocx.Data.Model.OtpAuthMdodel.SendOtp

data class SendOtpResponseBody(
    val message: String,
    val success: Boolean,
    val testOTP: String
)