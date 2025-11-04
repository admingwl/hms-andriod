package com.example.happydocx.ui.uiStates

import android.net.Uri

data class FormInformation(
    val firstName: String = "",
    val middleName: String = "",
    val lastName: String = "",
    val dateOfBirth:String="",
    val contactNumber: String = "",
    val email: String = "",
    val dateOfJoining:String="",
    val addressLineOne: String = "",
    val addressLineTwo: String = "",
    val city: String = "",
    val state: String = "",
    val district: String = "",
    val zipCode: String = "",
    val country: String = "",
    val clinicLocationUrl: String = "",
    val salutationDropDownexpandedState:Boolean = false,
    val salutationoptionList:List<String> = emptyList(),
    val salutationselectedOptions:String="",
    val genderexpandedState:Boolean = false,
    val genderoptionList:List<String> = emptyList(),
    val genderselectedOptions:String="",
    val departmentexpandedState:Boolean = false,
    val departmentoptionList:List<String> = emptyList(),
    val departmentselectedOptions:String="",
    val bloodGroupexpandedState:Boolean = false,
    val bloodGroupoptionList:List<String> = emptyList(),
    val bloodGroupselectedOptions:String="",
    val addressexpandedState:Boolean = false,
    val addressoptionList:List<String> = emptyList(),
    val addressselectedOptions:String="",
    val profilePhotoUri: Uri? = null,
    val profilePhotoName: String? = null,
    val signatureUri: Uri? = null,
    val signatureName: String? = null,
    val doctorIdProofUri: Uri? = null,
    val doctorIdProofName: String? = null,
    val doctorLicenseUri: Uri? = null,
    val doctorLicenseName: String? = null,
    val mbbsCertificateUri: Uri? = null,
    val mbbsCertificateName: String? = null,
    val experienceCertificateUri: Uri? = null,
    val experienceCertificateName: String? = null

    )


