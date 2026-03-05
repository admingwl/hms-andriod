package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.HistoryResponse

data class GetAllHistoryResponse(
    val appointments: List<Appointment>,
    val limit: Int,
    val page: Int,
    val totalPages: Int,
    val totalRecords: Int
)