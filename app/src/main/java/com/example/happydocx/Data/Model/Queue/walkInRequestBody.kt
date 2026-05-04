package com.example.happydocx.Data.Model.Queue

data class walkInRequestBody(
    val patient:String,
    val reason:String = "Walk-in"
)