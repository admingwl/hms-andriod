package com.example.happydocx.Data.Repository.StartConsulting.UpdatedVersion1_Repo

import android.content.Context
import android.util.Log
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsAndSymptomsRequestBody
import com.example.happydocx.Data.Model.StartConsulting.SaveSendVitalSignsResponseBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateRequestUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewLabResults.Manualy.ManualLabReportCreateResponseUpdate1
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationRequest
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.CreateNewMedication.CreateMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllLabResultResponse.LabResultResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllMedicalRecords.GetAllMedicalRecordsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllMedicalRecords.GetAllMedicalRecordsResponseItem
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetAllVitalSignsResponse.AllVitalSignsResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetCurrentMedicationResponse.CurrentMedicationResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.GetParticularPatientAppointmentData.GetParticularPatientAppointemntDataResponse.PatientAppointmentData
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.HistoryResponse.GetAllHistoryResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Request.Save_Vital_Signs_RequestBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.SavePatientsVitalSigns.Response.Save_vitalSigns_Response_Body
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UpdateAppointmentDetail.UpdateAppointmentDetailRequest
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UpdateAppointmentDetail.UpdateAppointmentDetailResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadDocuements.UploadDocumentResponse
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Request.UploadNotesRequestBody
import com.example.happydocx.Data.Model.StartConsulting.StartConsultingUpdateVersion1_Model.UploadNotes.Response.UploadNotesResponseBody
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance
import com.example.happydocx.ui.uiStates.StartConsulting.UploadDocumentUpdate1
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

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

    suspend fun historyRepo(
        token:String,
        patientId:String,
        page:Int = 1,
        limit:Int = 10
    ): Result<GetAllHistoryResponse>{
        return try{
            val result = apiService.historiesList(
                token =  "Bearer $token",
                patient = patientId,
                page = page,
                limit = limit
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

    suspend fun getAllMedicationDocumentListRepo(
        token:String,
        patientId:String,
    ):Result<List<GetAllMedicalRecordsResponseItem>>{
        return try{
            val result = apiService.getAllMedicalDocuments(
                token =  "Bearer $token",
                patientId = patientId,
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

    suspend fun uploadPatientDocuments(
        context: Context,
        token:String,
        appointmentId:String,
        patientId:String,
        state: UploadDocumentUpdate1
    ): Result<UploadDocumentResponse>{

        val documentName = state.documentName.toRequestBody("text/plain".toMediaType())
        val documentType = state.documentType.toRequestBody("text/plain".toMediaType())
        val reportDate = state.reportDate.toRequestBody("text/plain".toMediaType())
        val appointmentId = appointmentId.toRequestBody("text/plain".toMediaType())
        val patientId = patientId.toRequestBody("text/plain".toMediaType())

        // convert URI to binary
        val byteArray = context.contentResolver.openInputStream(state.attachmentURI!!)?.use { it.readBytes() }

        val mimeType = context.contentResolver
            .getType(state.attachmentURI) ?: "application/octet-stream"

        val fileRequestBody = byteArray!!
            .toRequestBody(mimeType.toMediaType())

        // "file" must match the key name API expects
        val filePart = MultipartBody.Part.createFormData(
            "file",                  //  key name
            state.attachmentName,    //  file name
            fileRequestBody          //  binary
        )

        return try {
            val response = apiService.uploadMedicalDocuments(
                token = "Bearer $token",
                documentName = documentName,
                documentType = documentType,
                reportDate = reportDate,
                appointmentId = appointmentId,
                patientId = patientId,
                attachment = filePart
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)   //  wrap in Result.success
            } else {
                Result.failure(Exception(response.message()))  //  wrap error
            }
        } catch (e: Exception) {
            Result.failure(e)   //  wrap exception
        }
    }


    // repo for the uploading notes
    suspend fun uploadNotesRepo(
        token:String,
        appointmentId:String,
        requestBody:UploadNotesRequestBody
    ):Result<UploadNotesResponseBody>{
        return try{
            val result = apiService.uploadNotes(
                token =  "Bearer $token",
                appointmentId = appointmentId,
                requestBody = requestBody
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


    suspend fun updateAppointmentDetail(
        token:String,
        requestBody:UpdateAppointmentDetailRequest
    ):Result<UpdateAppointmentDetailResponse>{
        return try{
            val result = apiService.updateAppointmentDetails(
                token =  "Bearer $token",
                requestBody = requestBody
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