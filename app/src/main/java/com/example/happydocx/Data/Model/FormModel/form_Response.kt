package com.example.happydocx.Data.Model.FormModel

import com.google.gson.annotations.SerializedName

// response from save draft api
data class DoctorProfileResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("doctor")
    val doctor: Doctor
)

data class Doctor(
    @SerializedName("_id")
    val id: String,
    @SerializedName("fullName")
    val fullName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("specialization")
    val specialization: String,
    @SerializedName("qualification")
    val qualification: String,
    @SerializedName("registrationNumber")
    val registrationNumber: String,
    @SerializedName("employee_department")
    val employee_department: String,
    @SerializedName("experienceYears")
    val experienceYears: Int,
    @SerializedName("address")
    val address: ContactAddress,
    @SerializedName("imageUrl")
    val imageUrl: String,  // Now a URL (post-upload)
    @SerializedName("signatureImage")
    val signatureImage: String,  // URL
    @SerializedName("doctorIdProof")
    val doctorIdProof: String,  // URL
    @SerializedName("doctorLicense")
    val doctorLicense: String,  // URL
    @SerializedName("mbbsCertificate")
    val mbbsCertificate: String,  // URL
    @SerializedName("experienceCertificate")
    val experienceCertificate: String  // URL
)
