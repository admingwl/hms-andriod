package com.example.happydocx.Data.Model.FormModel

// requested body to api

data class UpdateDoctorProfileRequest(
    // Personal Information
    val salutation: String,
    val first_name: String,
    val middle_name: String,
    val last_name: String,
    val dateofbirth: String,  // Will be converted to ISO in repository
    val gender: String,

    // Contact Information
    val email: String,
    val contact_no: Long,

    // Professional Details
    val employee_department: String,
    val date_of_joinning: String,  // Will be converted to ISO in repository
    val bloodGroup: String,

    // Address
    val contact_address: ContactAddress
)

data class ContactAddress(
    val addressType: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val district: String,
    val zipCode: String,
    val country: String,
    val clinicLocationUrl: String
)
