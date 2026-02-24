package com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request

import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Request_Vitals

data class Save_Vital_Signs_RequestBody(
    val appointment: String,
    val doctor: String,
    val patient: String,
    val vitals: Request_Vitals
)