package com.example.happydocx.Data.Repository.FormRepository

import com.example.happydocx.Data.Model.FormModel.DoctorProfileResponse
import com.example.happydocx.Data.Model.FormModel.UpdateDoctorProfileRequest
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class FormOneRepository {

    private val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)

        // Helper function to convert dd-MM-yyyy to ISO format
        private fun convertToISODate(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'00:00:00'Z'", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            ""
        }
    }

    suspend fun updateDoctorProfile(
        doctorId: String,
        request: UpdateDoctorProfileRequest,
        imageFile: File?,
        signatureFile: File?,
        idProofFile: File?,
        licenseFile: File?,
        mbbsFile: File?,
        experienceFile: File?
    ): Result<DoctorProfileResponse> {
        return try {
            // Convert dates to ISO format
            val dobISO = convertToISODate(request.dateofbirth)
            val dojISO = convertToISODate(request.date_of_joinning)

            // Personal Information
            val salutationBody = request.salutation.toRequestBody("text/plain".toMediaTypeOrNull())
            val firstNameBody = request.first_name.toRequestBody("text/plain".toMediaTypeOrNull())
            val middleNameBody = request.middle_name.toRequestBody("text/plain".toMediaTypeOrNull())
            val lastNameBody = request.last_name.toRequestBody("text/plain".toMediaTypeOrNull())
            val dobBody = dobISO.toRequestBody("text/plain".toMediaTypeOrNull())
            val genderBody = request.gender.toRequestBody("text/plain".toMediaTypeOrNull())

            // Contact Information
            val emailBody = request.email.toRequestBody("text/plain".toMediaTypeOrNull())
            val contactNoBody = request.contact_no.toString().toRequestBody("text/plain".toMediaTypeOrNull())

            // Professional Details
            val departmentBody = request.employee_department.toRequestBody("text/plain".toMediaTypeOrNull())
            val dateOfJoiningBody = dojISO.toRequestBody("text/plain".toMediaTypeOrNull())
            val bloodGroupBody = request.bloodGroup.toRequestBody("text/plain".toMediaTypeOrNull())

            // Contact Address (nested using array notation)
            val addressTypeBody = request.contact_address.addressType.toRequestBody("text/plain".toMediaTypeOrNull())
            val addressLine1Body = request.contact_address.addressLine1.toRequestBody("text/plain".toMediaTypeOrNull())
            val addressLine2Body = request.contact_address.addressLine2.toRequestBody("text/plain".toMediaTypeOrNull())
            val cityBody = request.contact_address.city.toRequestBody("text/plain".toMediaTypeOrNull())
            val stateBody = request.contact_address.state.toRequestBody("text/plain".toMediaTypeOrNull())
            val districtBody = request.contact_address.district.toRequestBody("text/plain".toMediaTypeOrNull())
            val zipCodeBody = request.contact_address.zipCode.toRequestBody("text/plain".toMediaTypeOrNull())
            val countryBody = request.contact_address.country.toRequestBody("text/plain".toMediaTypeOrNull())
            val clinicLocationUrlBody = request.contact_address.clinicLocationUrl.toRequestBody("text/plain".toMediaTypeOrNull())

            // Convert Files to MultipartBody.Part
            val imageUrlPart = imageFile?.let {
                MultipartBody.Part.createFormData(
                    "imageUrl",
                    it.name,
                    it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                )
            }

            val signaturePart = signatureFile?.let {
                MultipartBody.Part.createFormData(
                    "signatureImage",
                    it.name,
                    it.asRequestBody("image/jpeg".toMediaTypeOrNull())
                )
            }

            val idProofPart = idProofFile?.let {
                MultipartBody.Part.createFormData(
                    "doctorIdProof",
                    it.name,
                    it.asRequestBody("application/pdf".toMediaTypeOrNull())
                )
            }

            val licensePart = licenseFile?.let {
                MultipartBody.Part.createFormData(
                    "doctorLicense",
                    it.name,
                    it.asRequestBody("application/pdf".toMediaTypeOrNull())
                )
            }

            val mbbsPart = mbbsFile?.let {
                MultipartBody.Part.createFormData(
                    "mbbsCertificate",
                    it.name,
                    it.asRequestBody("application/pdf".toMediaTypeOrNull())
                )
            }

            val experiencePart = experienceFile?.let {
                MultipartBody.Part.createFormData(
                    "experienceCertificate",
                    it.name,
                    it.asRequestBody("application/pdf".toMediaTypeOrNull())
                )
            }

            // Make the API call
            val response = apiService.updateDoctorProfile(
                doctorId = doctorId,
                salutation = salutationBody,
                firstName = firstNameBody,
                middleName = middleNameBody,
                lastName = lastNameBody,
                dateOfBirth = dobBody,
                gender = genderBody,
                email = emailBody,
                contactNo = contactNoBody,
                employeeDepartment = departmentBody,
                dateOfJoining = dateOfJoiningBody,
                bloodGroup = bloodGroupBody,
                addressType = addressTypeBody,
                addressLine1 = addressLine1Body,
                addressLine2 = addressLine2Body,
                city = cityBody,
                state = stateBody,
                district = districtBody,
                zipCode = zipCodeBody,
                country = countryBody,
                clinicLocationUrl = clinicLocationUrlBody,
                imageUrl = imageUrlPart,
                signatureImage = signaturePart,
                doctorIdProof = idProofPart,
                doctorLicense = licensePart,
                mbbsCertificate = mbbsPart,
                experienceCertificate = experiencePart
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("API Error ${response.code()}: $errorBody"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}