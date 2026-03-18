package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Response

data class Data(
    val __v: Int,
    val _id: String,
    val appointment: String,
    val companyId: String,
    val createdAt: String,
    val date: String,
    val doctor: String,
    val followUpDate: String,
    val notes: Notes,
    val orders: Orders,
    val patient: String,
    val prescription: List<Prescription>,
    val prescriptionLanguage: String,
    val status: String,
    val updatedAt: String,
    val vitals: Vitals
)