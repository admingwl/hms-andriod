package com.example.happydocx.Data.Repository.Patient

import android.util.Log
import com.example.happydocx.Data.Model.PatientScreen.GeneralSavePatient.SavePatientRequestBody
import com.example.happydocx.Data.Model.PatientScreen.GeneralSavePatient.SavePatientResponseBody
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

class SavePatientInfo {

    // first we get the object of the api interface
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun SaveGeneralInformationOfPatient(
        token:String,
        requestBody: SavePatientRequestBody
    ):Result<SavePatientResponseBody>{

        return try{
            val result = apiService.savePatientGeneral(token = "Bearer $token",requestBody = requestBody)
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