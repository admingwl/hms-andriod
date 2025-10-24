package com.example.happydocx.Data.Model.LoginModel

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token:String,
    val message: String,
    val user: User
)


data class User(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val email: String,
    val role: String,
    val companyId: String,
    val doctorId: String
)