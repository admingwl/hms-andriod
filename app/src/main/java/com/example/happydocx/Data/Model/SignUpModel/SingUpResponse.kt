package com.example.happydocx.Data.Model.SignUpModel

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
   val message:String,
   val user: SignUpUser
)

data class SignUpUser(
    @SerializedName("_id")
    val id:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("role")
    val role:String,
    @SerializedName("doctorId")
    val doctorId:String,
    @SerializedName("companyId")
    val companyId:String
)
