package com.example.happydocx.Data.Repository.StartConsulting.UpdatedVersion1_Repo

import android.util.Log
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsAndSymptomsRequestBody
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsResponseBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Save_Vital_Signs_RequestBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Response.Save_vitalSigns_Response_Body
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

class StartConsultingRepo {

    // get object of the api Service
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    // get allVitalSigns
   suspend fun getAllMedicalRecords(
        token:String,
        appointmentId:String
    ): Result<AllVitalSignsResponse>{

       return try{
            val result = apiService.allMedicalRecords(
                token =  "Bearer $token" ,
                appointmentId = appointmentId
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


    // function for save the vital signs and symptoms
    suspend fun Save_Patient_Vitals_Signs_And_Symptoms(
        token: String,
        requestBody: Save_Vital_Signs_RequestBody
    ): Result<Save_vitalSigns_Response_Body> {
        return try {
            Log.d("REPO_SAVE", "Calling API...")
            Log.d("REPO_SAVE", "Token: Bearer ${token.take(20)}...")
            Log.d("REPO_SAVE", "Request: $requestBody")
            // call api here first
            val result = apiService.sendVitalSignsAndSymptoms(token = "Bearer $token", body = requestBody)
            Log.d("REPO_SAVE", "Response Code: ${result.code()}")
            Log.d("REPO_SAVE", "Response Body: ${result.body()}")

            if (result.isSuccessful && result.body() != null) {
                Log.d("REPO_SAVE", " API Success")
                Log.d("Server Code", "${result.code()}")
                Log.d("Server Response", "Success: ${result.body()}")
                Result.success(result.body()!!)
            } else {
                val errorMessage = result.errorBody()?.string() ?: "Unknown server error"
                Log.e("REPO_SAVE", " API Failed: $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Log.e("REPO_SAVE", " Exception: ${e.message}", e)
            Result.failure(e)
        }
    }

}