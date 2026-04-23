package com.example.happydocx.Data.Model.Queue

data class Data(
    val _id: String,
    val appointment: Appointment,
    val assignedAt: String,
    val companyId: String,
    val date: String,
    val doctor: String,
    val estimatedStartTime: String,
    val estimatedWaitMinutes: Int,
    val isWalkIn: Boolean,
    val noShowHoldMinutes: Int,
    val patient: Patient,
    val status: String,
    val tokenNumber: Int
)