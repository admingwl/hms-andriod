package com.example.happydocx.Data.Model.StartConsulting

data class TestAndInvestigationRequest(
    val patient:String,
    val physicianId:String,
    val appointment:String,
    val investigationOrders:List<TestAndInvestigationOrders>
)
data class TestAndInvestigationOrders(
    val name:String,
    val reason:String,
)
