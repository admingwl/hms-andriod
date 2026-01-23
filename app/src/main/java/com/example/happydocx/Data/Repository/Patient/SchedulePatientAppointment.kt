package com.example.happydocx.Data.Repository.Patient

import android.util.Log
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.ScheduleAppointmentRequest
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.ScheduleAppointmentResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

class SchedulePatientAppointment {

    val apiObject2 = RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun ScheduleAppoitmentForPatientRepo(
        token:String,
        requestBody:ScheduleAppointmentRequest
    ):Result<ScheduleAppointmentResponse>{

        return try{
            val result = apiObject2.scheduleAppointment(
                token ="Bearer $token",requestBody = requestBody
            )
            if(result.isSuccessful && result.body()!=null){
                Log.d("Server Response","${result.code()} ${result.body()}")
                Result.success(result.body()!!)
            }else{
                val errorMessage = result.errorBody()?.string() ?: "unknown server error"
                Result.failure(Exception(errorMessage))

            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}