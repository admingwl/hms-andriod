package com.example.happydocx.Data.Network

import com.example.happydocx.Data.Model.AllDepartments
import com.example.happydocx.Data.Model.DoctorAppointment.AppointmentResponse
import com.example.happydocx.Data.Model.FormModel.DoctorProfileResponse
import com.example.happydocx.Data.Model.LoginModel.LoginRequest
import com.example.happydocx.Data.Model.LoginModel.LoginResponse
import com.example.happydocx.Data.Model.SignUpModel.SignUpRequest
import com.example.happydocx.Data.Model.SignUpModel.SignUpResponse
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
import com.example.happydocx.Data.Model.StartConsulting.TestAndInvestigationResponse
import com.example.happydocx.Data.Model.StartConsulting.UpdateAppointmentStatusRequestBody
import com.example.happydocx.Data.Model.StartConsulting.UpdateAppointmentStatusResponseBody
import com.example.happydocx.Data.Model.StartConsulting.vitalSignList
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

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



    // Api integration for getting all the list of appointments of the doctor
    @GET("api/v1/appointment/doctor")
    suspend fun getDoctorAppointments(
      @Header("Authorization") token:String?, // bearer token
      @Query("page") page:Int = 1,
      @Query("limit") limit:Int = 10,
      @Query("showCompleted") showCompleted:Boolean = false
    ): Response<AppointmentResponse>


    // api for getting the all departments
    @GET("api/v1/department/all-departments")
    suspend fun getAllDepartments(
        @Header ("Authorization") token:String
    ): Response<AllDepartments>


    // api for getting the particular patient's appointment data
    // we use path here because the appointmentId is the part of the url .
    @GET("api/v1/appointment/{appointmentId}")
    suspend fun getParticularPatientAppointment(
          @Header("Authorization") token:String,
          @Path("appointmentId") appointmentId:String
    ): Response<AppointmentApiResponse>


    // post request for the submit of the symptoms and diagnosis and notes
    @POST("api/v1/investigation/add")
    suspend fun SubmitSymptomsDiagnosisNotes(
        @Header("Authentication") token:String,
        @Body body: SaveSymptomDiagnosisRequest,
    ): Response<SaveSymptomDiagnosisResponse>

    // post api for the save vital signs and symptoms of patient
    @POST("api/v1/vital-signs")
    suspend fun sendVitalSignsAndSymptoms(
        @Header("Authorization") token: String,
        @Body body: SaveSendVitalSignsAndSymptomsRequestBody
    ): Response<SaveSendVitalSignsResponseBody>


    // get all the list of vital signs and symptoms
    @GET("api/v1/vital-signs/all/{patientId}")
    suspend fun getAllVitalSignsAndSymptoms(
        @Header("Authorization") token: String,
        @Path("patientId") patientId: String
    ):Response<vitalSignList>

    // submit medication api
    @POST("api/v1/medical/medication")
    suspend fun sendMedication(
       @Header("Authorization") token:String,
       @Body body: MedicationRequest
    ): Response<MedicationResponse>

    // here is my api call for the test and investigation
    @POST("api/v1/medical-record/add-order")
    suspend fun submitTestAndInvestigation(
        @Header("Authorization") token: String,
        @Body body: TestAndInvestigationRequest
    ): Response<TestAndInvestigationResponse>

    @POST("api/v1/appointment/update/status/{id}") // id -> appointment Id
    suspend fun updateAppointmentStatus(
        @Header("Authorization") token:String,
        @Path("id") appointmentId:String,
        @Body body : UpdateAppointmentStatusRequestBody
    ): Response<UpdateAppointmentStatusResponseBody>

    @GET("api/v1/record/by-appointment/{appointmentId}")
    suspend fun getMedicalRecords(
        @Header("Authorization") token:String,
        @Path("appointmentId") appointmentId: String
    ): Response<List<PrescriptionRecord>>
}