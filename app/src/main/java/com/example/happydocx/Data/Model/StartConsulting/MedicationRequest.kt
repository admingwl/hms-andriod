package com.example.happydocx.Data.Model.StartConsulting

data class MedicationRequest(
    val patient:String,
    val medicationOrders: List<MedicationsOrders>,
    val appointment:String,
    val physicianId:String
)
data class MedicationsOrders(
    val id:String="",
    val name:String,
    val power:String,
    val duration:String,

)
