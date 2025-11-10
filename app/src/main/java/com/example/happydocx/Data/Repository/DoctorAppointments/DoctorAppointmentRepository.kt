package com.example.happydocx.Data.Repository.DoctorAppointments

import android.util.Log
import com.example.happydocx.Data.Model.DoctorAppointment.AppointmentResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DoctorAppointmentRepository {

    // first we have to create the object of our api service
    private val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun getDoctorAppointments(
        token: String?,
        page: Int = 1,
        limit: Int = 10,
        showCompleted: Boolean = false
    ): Result<AppointmentResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("DEBUG_REPO", "Making API call...")
                Log.d("DEBUG_REPO", "Token: $token")
                val authHeader = token?.let { "Bearer $it" }
                Log.d("DEBUG_REPO", "Auth Header: $authHeader")
                val response = apiService.getDoctorAppointments(
                    token = authHeader,
                    page = page,
                    limit = limit,
                    showCompleted = showCompleted
                )
                Log.d("DEBUG_REPO", "Response Code: ${response.code()}")
                Log.d("DEBUG_REPO", "Response Success: ${response.isSuccessful}")
                Log.d("DEBUG_REPO", "Response Body: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    Log.d("DEBUG_REPO", "Returning success")
                    Result.success(response.body()!!)
                } else {

                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))

                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}