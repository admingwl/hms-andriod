package com.example.happydocx.Data.Repository.StartConsulting

import com.example.happydocx.Data.Model.StartConsulting.AppointmentApiResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

class BasicInformationRepository {

    // first create the object of the api service
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

    // fun to get the data from the api
    suspend fun getBasicInformation(
        appointmentId: String,
        token:String,
    ): Result<AppointmentApiResponse> {

        return try {
            val result = apiService.getParticularPatientAppointment(
                token = "Bearer $token",
                appointmentId = appointmentId,
                )
            if (result.isSuccessful && result.body() != null) {
                Result.success(result.body()!!)
            } else {
                Result.failure(Exception(result.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}
