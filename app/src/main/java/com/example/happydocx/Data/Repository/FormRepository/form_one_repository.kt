package com.example.happydocx.Data.Repository.FormRepository

import com.example.happydocx.Data.Model.FormModel.Address
import com.example.happydocx.Data.Model.FormModel.PersonalDetails
import com.example.happydocx.Data.Model.FormModel.ProfessionalDetail
import com.example.happydocx.Data.Model.FormModel.SaveDraftRequest
import com.example.happydocx.Data.Model.FormModel.SaveDraftResponse
import com.example.happydocx.Data.Network.ApiService
import com.example.happydocx.Utils.RetrofitInstance
import retrofit2.Response
import retrofit2.Retrofit

class FormOneRepository() {

    val apiService =
        RetrofitInstance.retrofit.create(ApiService::class.java)

    suspend fun SaveDraft(
        doctorId:String,
        personalDetails: PersonalDetails,
        professionalDetail: ProfessionalDetail,
        address: Address
    ): Result<SaveDraftResponse>{
        return try{
           val request = SaveDraftRequest(
               personalDetails = personalDetails,
               professionalDetail = professionalDetail,
               address = address
           )

            // call api with doctor Id
            val response = apiService.saveDraft(doctorId = doctorId, request = request)

            if(response.isSuccessful && response.body()!=null){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error : ${response.code()} - ${response.message()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}