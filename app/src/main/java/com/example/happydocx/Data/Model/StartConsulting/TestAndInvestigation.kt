package com.example.happydocx.Data.Model.StartConsulting

data class TestAndInvestigationRequest(
    val patient:String,
    val physicianId:String,
    val appointment:String,
    val investigationOrders:List<TestAndInvestigationOrders>
)
data class TestAndInvestigationOrders(
    val unit:String="",
    val duration:String="",
    val severity:String="",
    val name:String,
    val reason:String,
)
