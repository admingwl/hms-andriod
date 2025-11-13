package com.example.happydocx.Data.Model.DoctorAppointment

import com.google.gson.annotations.SerializedName
import java.io.Serial

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
    @SerializedName("appointmentDate") // slot
    val date:String,
    val companyId: String,
    @SerializedName("visitType")
    val visitType:String = "",
)

data class Patient(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("first_name")
    val first_name : String,
    @SerializedName("last_name")
    val last_name : String,
    @SerializedName("gender")
    val gender:String="",
    @SerializedName("contactNumber")
    val contactNumber:String = "",
    @SerializedName("createdAt")
    val createdAt :String=""
)
// patient created at -> is last visit