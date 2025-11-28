package com.example.happydocx.Data.Repository.Departments

import android.util.Log
import com.example.happydocx.Data.Model.AllDepartments
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

class AllDepartmentRepo {

    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun getAllDepartments(
        token:String
    ): Result<AllDepartments>{
        return try {
            // Add "Bearer " prefix if your API expects it
            val authHeader = "Bearer $token"
            val result = apiService.getAllDepartments(token = authHeader)
            Log.d("DEBUG_REPO", "Response code: ${result.code()}")
            Log.d("DEBUG_REPO", "Response successful: ${result.isSuccessful}")
            Log.d("DEBUG_REPO", "Response body: ${result.body()}")
            Log.d("DEBUG_REPO", "Response error body: ${result.errorBody()?.string()}")
            if(result.isSuccessful && result.body()!=null){
                Result.success(result.body()!!) // we got the data
            }else{
                Result.failure(Exception("Failed to fetch data"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

}