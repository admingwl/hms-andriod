package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse

data class Data(
    val __v: Int,
    val _id: String,
    val appointment: Appointment,
    val companyId: String,
    val createdAt: String,
    val date: String,
    val doctor: Doctor,
    val followUpDate: String,
    val notes: Notes,
    val orders: Orders,
    val patient: Patient,
    val prescription: List<Prescription>,
    val prescriptionLanguage: String,
    val status: String,
    val updatedAt: String,
    val vitals: Vitals
)