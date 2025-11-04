package com.example.happydocx.Data.Model.FormModel

// requested body to api
data class SaveDraftRequest(
    val personalDetails: PersonalDetails,
    val professionalDetail: ProfessionalDetail,
    val address: Address
)

// request model
data class PersonalDetails(
    val fullName: String,
    val phone: String
)

data class ProfessionalDetail(
    val specialization: String,
    val yearsOfExperience: Int
)

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val pincode: String
)

