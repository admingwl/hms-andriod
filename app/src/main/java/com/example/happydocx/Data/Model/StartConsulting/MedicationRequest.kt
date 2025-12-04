package com.example.happydocx.Data.Model.StartConsulting

data class MedicationRequest(
    val patient:String,
    val medicationOrders: List<MedicationOrders>,
    val appointment:String,
    val physicianId:String
)
data class MedicationOrders(
    val medicationName:String,
    val dosage:String,
    val duration:String,
)
