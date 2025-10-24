package com.example.happydocx.Data.Repository.AuthRepository.SignUpRepository

import com.example.happydocx.Data.Model.SignUpModel.SignUpRequest
import com.example.happydocx.Data.Model.SignUpModel.SignUpResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

// this is going to communicate with the data source in my case it is (remote server)
class SignUpRepository {

    // get apiService object
    val apiSignUpService = RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun signUp(email: String, password: String, confirmPassword: String):Result<SignUpResponse> {
      return  try {
            val response = apiSignUpService.signUp(
                SignUpRequest(
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword
                )
            )
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!) // data is here
            } else {
                Result.failure(Exception("SignUp Failed ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}