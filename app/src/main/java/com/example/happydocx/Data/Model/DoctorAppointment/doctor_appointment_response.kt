package com.example.happydocx.Data.Model.DoctorAppointment

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("success")
    val success: Boolean,  // Changed to Boolean (matches doc)
    val total:Int,
    @SerializedName("page")
    val page: Int? = null,
    val limit:Int? = null,
    val data : List<Appointment>
)
data class Appointment(
    @SerializedName("_id")  // Maps JSON "_id" to your "id" field
    val id: String,
    val status:String,
    val patient:Patient,
    @SerializedName("date")
    val date:String,
    val companyId: String
)

data class Patient(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("name")
    val name : String
)