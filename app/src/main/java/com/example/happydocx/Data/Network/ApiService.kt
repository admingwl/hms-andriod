package com.example.happydocx.Data.Network

import com.example.happydocx.Data.Model.FormModel.DoctorProfileResponse
import com.example.happydocx.Data.Model.LoginModel.LoginRequest
import com.example.happydocx.Data.Model.LoginModel.LoginResponse
import com.example.happydocx.Data.Model.SignUpModel.SignUpRequest
import com.example.happydocx.Data.Model.SignUpModel.SignUpResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService{
    // here is the end point in post(parameter)
    // api end point for Sign in User
    @POST("api/v1/user/sign-in")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    // here is the end point in the post(parameter)
    // api end point for signup user
    @POST("api/v1/user/sign-up-new")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponse>


    //Yeh annotation function ke upar lagana compulsory hai. Yeh Retrofit ko batata hai ki
    // is call mein JSON nahi, balki multipart/form-data(text + image) bhejna hai.
    @Multipart
    @PUT("api/v1/doctor/{doctorId}")
    suspend fun updateDoctorProfile(
        @Path("doctorId") doctorId: String,

        // Personal Information
        @Part("salutation") salutation: RequestBody,
        @Part("first_name") firstName: RequestBody,
        @Part("middle_name") middleName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("dateofbirth") dateOfBirth: RequestBody,  // Format: "2002-02-06T00:00:00Z"
        @Part("gender") gender: RequestBody,

        // Contact Information
        @Part("email") email: RequestBody,
        @Part("contact_no") contactNo: RequestBody,  // Should be number as string

        // Professional Details
        @Part("employee_department") employeeDepartment: RequestBody,  // ObjectId string
        @Part("date_of_joinning") dateOfJoining: RequestBody,  // Format: "2023-02-08T00:00:00Z"
        @Part("bloodGroup") bloodGroup: RequestBody,

        // Contact Address - nested object structure
        @Part("contact_address[addressType]") addressType: RequestBody,
        @Part("contact_address[addressLine1]") addressLine1: RequestBody,
        @Part("contact_address[addressLine2]") addressLine2: RequestBody,
        @Part("contact_address[city]") city: RequestBody,
        @Part("contact_address[state]") state: RequestBody,
        @Part("contact_address[district]") district: RequestBody,
        @Part("contact_address[zipCode]") zipCode: RequestBody,
        @Part("contact_address[country]") country: RequestBody,
        @Part("contact_address[clinicLocationUrl]") clinicLocationUrl: RequestBody,

        // File uploads
        @Part imageUrl: MultipartBody.Part?,
        @Part signatureImage: MultipartBody.Part?,
        @Part doctorIdProof: MultipartBody.Part?,
        @Part doctorLicense: MultipartBody.Part?,
        @Part mbbsCertificate: MultipartBody.Part?,
        @Part experienceCertificate: MultipartBody.Part?
    ): Response<DoctorProfileResponse>
}