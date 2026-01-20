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
                val authHeader = token?.let { "Bearer $it" }
                val response = patientListApiResponse.getAllPatients(
                    token = authHeader,
                    page = page,
                    limit = limit)
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