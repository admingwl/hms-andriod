package com.example.happydocx.Data.Network

import com.example.happydocx.Data.Model.LoginModel.LoginRequest
import com.example.happydocx.Data.Model.LoginModel.LoginResponse
import com.example.happydocx.Data.Model.SignUpModel.SignUpRequest
import com.example.happydocx.Data.Model.SignUpModel.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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
}