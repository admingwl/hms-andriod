package com.example.happydocx.Data.Model.OtpAuthMdodel.VerifyOtp

data class VerifyOtpResponse(
    val message: String,
    val success: Boolean,
    val token: String
)