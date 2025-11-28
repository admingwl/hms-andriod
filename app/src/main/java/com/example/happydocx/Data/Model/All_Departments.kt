package com.example.happydocx.Data.Model

data class AllDepartments(
    val message:String="",
    val departments: List<Departments> = emptyList()
)

data class Departments(
    val _id:String="",
    val departmentCode:String="",
    val companyId:String="",
    val departmentName:String="",
    val parentDepartmentName:String ? = null,
    val departmentDescription:String="",
    val departmentNoticeText:String="",
    val departmentHead:String="",
    val roomNumber:String="",
    val isActive:String="",
    val isAppointmentApplicable:String="",
    val createdAt:String="",
    val updatedAt:String="",
)
