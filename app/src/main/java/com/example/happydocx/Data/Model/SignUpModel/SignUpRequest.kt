package com.example.happydocx.Data.Model.SignUpModel

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("email")
    val email:String,
    @SerializedName("password")
    val password:String,
    @SerializedName("confirmPassword")
    val confirmPassword:String
)