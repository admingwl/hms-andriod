package com.example.happydocx.Data.Repository.QueueRepo

import android.util.Log
import com.example.happydocx.Data.Model.Queue.TodayQueueResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

class QueueRepository {

    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    // get allVitalSigns
    suspend fun getAllTodayQueue(
        token:String,
    ): Result<TodayQueueResponse>{
        return try{
            val result = apiService.getTodayQueue(
                token =  "Bearer $token",
            )
            if (result.isSuccessful && result.body() != null) {
                Log.d("ServerMessage", "Success: ${result.body()} and code is ${result.code()}")
                Result.success(result.body()!!)
            } else {
                val errorMessage = result.errorBody()?.string() ?: "Unknown server error"
                Result.failure(Exception(errorMessage))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}