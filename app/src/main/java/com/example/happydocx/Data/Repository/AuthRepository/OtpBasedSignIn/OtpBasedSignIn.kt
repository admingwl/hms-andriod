package com.example.happydocx.Data.Repository.AuthRepository.OtpBasedSignIn

import android.util.Log
import com.example.happydocx.Data.Model.OtpAuthMdodel.SendOtp.SendOtpRequestBody
import com.example.happydocx.Data.Model.OtpAuthMdodel.SendOtp.SendOtpResponseBody
import com.example.happydocx.Data.Model.OtpAuthMdodel.VerifyOtp.VerifyOtpRequest
import com.example.happydocx.Data.Model.OtpAuthMdodel.VerifyOtp.VerifyOtpResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance

class OtpBasedSignIn {
    val apiSignUpService = RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun gettingOtp(
        phone:String
    ):Result<SendOtpResponseBody> {
        return  try {
            val response = apiSignUpService.getOtp(
              requestBody =   SendOtpRequestBody(
                   phone =  phone
                )
            )
            if (response.isSuccessful && response.body() != null) {
                Log.d("server Response", "gettingOtp: ${response.body()}")
                Result.success(response.body()!!) // data is here
            } else {
                Result.failure(Exception("SignUp Failed ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun verifyOtp(
        otp:String,
        phone:String
    ):Result<VerifyOtpResponse> {
        return  try {
            val response = apiSignUpService.verifyOtp(
                requestBody =   VerifyOtpRequest(
                    otp = otp,
                    phone =  phone
                )
            )
            if (response.isSuccessful && response.body() != null) {
                Log.d("server Response", "verify OTP: ${response.body()}")
                Result.success(response.body()!!) // data is here
            } else {
                Result.failure(Exception("SignUp Failed ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}