package com.example.happydocx.Data.Repository.Patient

import android.util.Log
import com.example.happydocx.Data.Model.PatientScreen.ScheduleAppointment.scheduleAppointmentTimeResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance
import okhttp3.Response

class ScheduleAppointmentRepo {

    val apiValue = RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun getTimeSlots(
        token:String,
        date:String,
    ): Result<scheduleAppointmentTimeResponse>{

        return try{
            val result = apiValue.getTimeSlotsForParticularAppointmentDate(
                token = "Bearer $token",date = date
            )
            if(result.isSuccessful && result.body()!=null){
                Log.d("Server Code","${result.code()} , ${result.body()}")
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