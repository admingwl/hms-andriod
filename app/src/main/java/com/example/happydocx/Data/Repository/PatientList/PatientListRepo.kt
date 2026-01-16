package com.example.happydocx.Data.Repository.PatientList

import android.util.Log
import com.example.happydocx.Data.Model.PatientScreen.PatientListResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class PatientListRepo {

    // first we have to get the implementation class object of the api interface
    val patientListApiResponse = RetrofitInstance.retrofit.create(ApiService::class.java)

    // fun to get the data from the api .
    suspend fun getPatientList(
        token:String?,
        page:Int = 1,
        limit:Int = 10
    ):Result<PatientListResponse>{

        return withContext(Dispatchers.IO){
            try{
                Log.d("DEBUG_REPO", "Making API call...")
                Log.d("DEBUG_REPO", "Token: $token")
                val authHeader = token?.let { "Bearer $it" }
                Log.d("DEBUG_REPO", "Auth Header: $authHeader")

                val response = patientListApiResponse.getAllPatients(
                    token = authHeader,
                    page = page,
                    limit = limit)

                Log.d("DEBUG_REPO", "Response Code: ${response.code()}")
                Log.d("DEBUG_REPO", "Response Success: ${response.isSuccessful}")
                Log.d("DEBUG_REPO", "Response Body: ${response.body()}")

                if(response.isSuccessful && response.body()!=null){
                    Log.d("DEBUG_REPO", "Returning success")
                    Result.success(response.body()!!)
                }else{
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            }catch (e: Exception){
                Result.failure(e)
            }
        }
    }
}