package com.example.happydocx.Data.Repository.StartConsulting.UpdatedVersion1_Repo

import android.util.Log
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsAndSymptomsRequestBody
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsResponseBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateRequestUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateResponseUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationRequest
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllLabResultResponse.LabResultResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetCurrentMedicationResponse.CurrentMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetParticularPatientAppointmentData.GetParticularPatientAppointemntDataResponse.PatientAppointmentData
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
        requestBody: Save_Vital_Signs_RequestBody,
        appointmentId: String
    ): Result<Save_vitalSigns_Response_Body> {
        return try {
            Log.d("REPO_SAVE", "Calling API...")
            Log.d("REPO_SAVE", "Token: Bearer ${token.take(20)}...")
            Log.d("REPO_SAVE", "Request: $requestBody")
            // call api here first
            val result = apiService.sendVitalSignsAndSymptoms(token = "Bearer $token", body = requestBody, appointmentId = appointmentId )
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


    suspend fun currentMedications(
        token:String,
        appointmentId:String
    ):Result<CurrentMedicationResponse>{
        return try{
            val result = apiService.getCurrentMedication(
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

    // function for getting all Lab results
    suspend fun labResults(
        token:String,
        patientId:String
    ): Result<LabResultResponse>{
        return try{
            val result = apiService.getCurrentLabResult(
                token =  "Bearer $token",
                patientId = patientId
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

    // function for create the new medication
    suspend fun createNewMedicationRepo(
        token:String,
        appointmentId: String,
        requestBody: CreateMedicationRequest
    ):Result<CreateMedicationResponse>{

        return try {
            Log.d("REPO_SAVE", "Calling API...")
            Log.d("REPO_SAVE", "Token: Bearer ${token.take(20)}...")
            Log.d("REPO_SAVE", "Request: $requestBody")
            // call api here first
            val result = apiService.createNewMedicationResult(token = "Bearer $token", body = requestBody, appointmentId = appointmentId )
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

    // create lab result manually
    suspend fun CreateLabResultManuallyRepo(
        token:String,
        requestBody: ManualLabReportCreateRequestUpdate1
    ): Result<ManualLabReportCreateResponseUpdate1>{
        return try {
            Log.d("REPO_SAVE", "Calling API...")
            Log.d("REPO_SAVE", "Token: Bearer ${token.take(20)}...")
            Log.d("REPO_SAVE", "Request: $requestBody")
            // call api here first
            val result = apiService.CreateLabResultManually(token = "Bearer $token", body = requestBody)
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


    // get particular patient Appointment data
    suspend fun particularPatientAppointmentDataRepo(
        token:String,
        appointmentId:String
    ): Result<PatientAppointmentData>{
        return try {
            Log.d("REPO_SAVE", "Calling API...")
            Log.d("REPO_SAVE", "Token: Bearer ${token.take(20)}...")
            // call api here first
            val result = apiService.getParticularPatientAppointmentUpdate1(token = "Bearer $token",appointmentId)
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