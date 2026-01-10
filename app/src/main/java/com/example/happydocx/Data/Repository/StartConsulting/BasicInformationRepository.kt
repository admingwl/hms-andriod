package com.example.happydocx.Data.Repository.StartConsulting

import android.util.Log
import com.example.happydocx.Data.Model.StartConsulting.AppointmentApiResponse
import com.example.happydocx.Data.Model.StartConsulting.MedicationRequest
import com.example.happydocx.Data.Model.StartConsulting.MedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.ParticularPatient
import com.example.happydocx.Data.Model.StartConsulting.PrescriptionRecord
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsAndSymptomsRequestBody
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsResponseBody
import com.example.happydocx.Data.Model.StartConsulting.SaveSymptomDiagnosisRequest
import com.example.happydocx.Data.Model.StartConsulting.SaveSymptomDiagnosisResponse
import com.example.happydocx.Data.Model.StartConsulting.TestAndInvestigationRequest
import com.example.happydocx.Data.Model.StartConsulting.TestInvestigationResponse
import com.example.happydocx.Data.Model.StartConsulting.UpdateAppointmentStatusRequestBody
import com.example.happydocx.Data.Model.StartConsulting.UpdateAppointmentStatusResponseBody
import com.example.happydocx.Data.Model.StartConsulting.vitalSignList
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance
import okhttp3.Request

class BasicInformationRepository {

    // first create the object of the api service
    val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)


    // fun to get the data from the api
    suspend fun getBasicInformation(
        appointmentId: String,
        token: String,
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

    //fun for uploading the symtoms and diagnosis to data base
    suspend fun submitSymptomsDiagnosisNotes(
        requestBody: SaveSymptomDiagnosisRequest,
        token: String
    ): Result<SaveSymptomDiagnosisResponse> {
        return try {
            // api call
//            Log.d("Api Request", "Token: Bearer $token")
//            Log.d("Api Request", "Body: $requestBody")
            val result =
                apiService.SubmitSymptomsDiagnosisNotes(body = requestBody, token = "Bearer $token")
//            Log.d("Api Response method","Api call is successful")
            // handle response
            if (result.isSuccessful && result.body() != null) {
//                Log.d("Api Response", "Success: ${result.body()}")
                Result.success(result.body()!!)
            } else {
                val errorMessage = result.errorBody()?.string() ?: "Unknown server error"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            // Handle network or parsing errors
            Result.failure(e)
        }
    }

    // fun for repo to communicate with the server
    suspend fun sendVitalSignsAndSymptoms(
        token: String,
        requestBody: SaveSendVitalSignsAndSymptomsRequestBody
    ): Result<SaveSendVitalSignsResponseBody> {

        return try {
            Log.d("REPO_SAVE", "Calling API...")
            Log.d("REPO_SAVE", "Token: Bearer ${token.take(20)}...")
            Log.d("REPO_SAVE", "Request: $requestBody")
            // call api here first
            val result =
                apiService.sendVitalSignsAndSymptoms(token = "Bearer $token", body = requestBody)
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


    // function to get all vital signs and symptoms list
    suspend fun getAllSignsAndSymptomsList(
        token: String,
        patientId: String
    ): Result<vitalSignList> {
        return try {
            val result = apiService.getAllVitalSignsAndSymptoms(
                token = "Bearer $token",
                patientId = patientId
            )
            Log.d("API_CALL", "URL: ${result.raw().request.url}")
            Log.d("API_CALL", "Response Code: ${result.code()}")
            if (result.isSuccessful && result.body() != null) {
                Log.d("ServerMessage", "Success: ${result.body()} and code is ${result.code()}")
                Result.success(result.body()!!)
            } else {
                val errorMessage = result.errorBody()?.string() ?: "Unknown server error"
                Result.failure(Exception(errorMessage))
            }
        } catch (E: Exception) {
            Result.failure(E)
        }
    }

    // function to send the medication detail
    suspend fun sendMedicationReport(
        token: String,
        requestBody: MedicationRequest
    ): Result<MedicationResponse> {
        return try {
            val result = apiService.sendMedication(token = "Bearer $token", requestBody)
            if (result.isSuccessful && result.body() != null) {
//                Log.d("Server Code","${result.code()}")
//                Log.d("Server Response","Success: ${result.body()}")
                Result.success(result.body()!!)
            } else {
                val errorMessage = result.errorBody()?.string() ?: "Unknown server error"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // function for the test and investigation
    suspend fun submitTestAndInvestigation(
        token: String,
        requestBody: TestAndInvestigationRequest
    ): Result<TestInvestigationResponse> {

        return try {
            val result =
                apiService.submitTestAndInvestigation(token = "Bearer $token", body = requestBody)
            if (result.isSuccessful && result.body() != null) {
//                Log.d("Server Code","${result.code()} , ${result.body()}")
                Result.success(result.body()!!)
            } else {
                val errorMessage = result.errorBody()?.string() ?: "unknown server error"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // function for updating the Appointment status of the patient.
    suspend fun updateAppointmentStatus(
        token: String,
        appointmentId: String,
        status: String
    ): Result<UpdateAppointmentStatusResponseBody>{

        return try{
            Log.d("REPO_STATUS_UPDATE", "Updating appointment status...")
            Log.d("REPO_STATUS_UPDATE", "Appointment ID: $appointmentId")
            Log.d("REPO_STATUS_UPDATE", "Status: $status")

            val requestBody = UpdateAppointmentStatusRequestBody(status = status)
            val result = apiService.updateAppointmentStatus(
                token = "Bearer $token",
                appointmentId = appointmentId,
                body = requestBody
            )
            Log.d("REPO_STATUS_UPDATE", "Response Code: ${result.code()}")
            if(result.isSuccessful && result.body()!=null){
                Log.d("REPO_STATUS_UPDATE", " Status Update Success")
                Log.d("REPO_STATUS_UPDATE", "Response: ${result.body()}")
                Result.success(result.body()!!)
            }else{
                val errorMessage = result.errorBody()?.string() ?: "Unknown server error"
                Log.e("REPO_STATUS_UPDATE", " Status Update Failed: $errorMessage")
                Result.failure(Exception(errorMessage))
            }
        }catch(e:Exception){
            Log.e("REPO_STATUS_UPDATE", "Exception: ${e.message}", e)
            Result.failure(e)
        }
    }


    // get particular patient health record.
    suspend fun getHealthRecord(
        token:String,
        appointmentId:String,
    ): Result<List<PrescriptionRecord>>{
        return try {
            val result = apiService.getMedicalRecords(
                token = "Bearer $token",
               appointmentId = appointmentId
            )
            Log.d("API_CALL", "URL: ${result.raw().request.url}")
            Log.d("API_CALL", "Response Code: ${result.code()}")
            if (result.isSuccessful && result.body() != null) {
                Log.d("ServerKaMessage", "Success: ${result.body()} and code is ${result.code()}")
                Result.success(result.body()!!)
            } else {
                val errorMessage = result.errorBody()?.string() ?: "Unknown server error"
                Log.d("API_ERROR", "Error message: $errorMessage")
                Result.failure(Exception(errorMessage))

            }
        } catch (E: Exception) {
            Result.failure(E)
        }
    }
}
