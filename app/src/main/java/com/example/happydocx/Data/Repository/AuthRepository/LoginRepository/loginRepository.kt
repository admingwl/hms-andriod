package com.example.happydocx.Data.Repository.AuthRepository.LoginRepository

import android.content.Context
import android.util.Log
import com.example.happydocx.Data.Model.LoginModel.LoginRequest
import com.example.happydocx.Data.Model.LoginModel.LoginResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Data.UserStore
import com.example.happydocx.Utils.RetrofitInstance

// Repository is something going to communicate with the data source in my case (remote server)
class loginRepository(){
    private val apiService =
        RetrofitInstance.retrofit.create(ApiService::class.java)


    suspend fun login(email:String,password:String): Result<LoginResponse>{

        return try{
            val response = apiService.login(LoginRequest(email = email, password = password))
            //  This line only executes if the network call was successful (2xx)
            // AND the JSON body was successfully parsed into your LoginResponse data class.
            if(response.isSuccessful && response.body() !=null){
                /**
                 * When Result.success(response.body()!!) is returned, the following three things have successfully happened:
                 *
                 * Network Connection: The app talked to the server.
                 *
                 * Server Success: The server sent back a successful status code (e.g., 200 OK).
                 *
                 * Data Parsing: The JSON body was read and converted into your Kotlin LoginResponse and User data classes.
                 * The data is now safely inside the response.body()!! object.
                 */
                // here i save the token to data store on successful response from api which contain the token also
                Result.success(response.body()!!) // the data is here

            }else{
                //handle network/api error like (401,500)
                Result.failure(Exception("Login Failed ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}