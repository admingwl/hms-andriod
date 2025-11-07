package com.example.happydocx.ui.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happydocx.Data.Model.FormModel.ContactAddress
import com.example.happydocx.Data.Model.FormModel.DoctorProfileResponse
import com.example.happydocx.Data.Model.FormModel.UpdateDoctorProfileRequest
import com.example.happydocx.Data.Repository.FormRepository.FormOneRepository
import com.example.happydocx.ui.uiStates.FormInformation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class formViewModel(private val context: Context) : ViewModel() {

    // get repository object
    val formRepository = FormOneRepository()
    val salutationItemList = listOf("Dr.", "Prof.", "Mr.", "Mrs", "Ms.")
    val genderList = listOf("Male", "Female", "Other")
    val bloodGroupList = listOf("A+", "B+", "A-", "B-", "AB+", "AB-", "O+", "O-")
    val departmentList = listOf(
        "Cardiology",
        "Orthopedic",
        "Radiology",
        "Dermatology",
        "General Surgery",
        "Casuality",
        "Neurology",
        "Oncology",
        "Gynecology",
        "ENT(Ear,Nose,Throat)"
    )
    val addressList = listOf("Permanent", "Temporary")


    // Department Name to ObjectId Mapping
    private val departmentIdMap = mapOf(
        "Cardiology" to "6814b0f652bfa886f0390ef0",
        "Orthopedic" to "688a1a01967b4cfd2f36e2e9",
        "Radiology" to "6814b3fc52bfa886f0390ef4",
        "Dermatology" to "688a1a1e967b4cfd2f36e2eb",
        "General Surgery" to "681b2e987d828184cf8b16f4",
        "Casuality" to "681b41647d828184cf8b16f7",
        "Neurology" to "688a19ec967b4cfd2f36e2e7",
        "Oncology" to "688a1a7f967b4cfd2f36e2f5",
        "Gynecology" to "688a1a90967b4cfd2f36e2f7",
        "ENT(Ear,Nose,Throat)" to "688a1a47967b4cfd2f36e2ef"
    )

    // form state
     private var formState = MutableStateFlow(FormInformation())
    val _formState = formState.asStateFlow()

    // network state
    private  val doctorProfileState = MutableStateFlow<DoctorProfileUiState>(DoctorProfileUiState.Idle)
    val _doctorProfileState = doctorProfileState.asStateFlow()


    fun onFirstNameChanged(firstName: String) {
        formState.update { it ->
            it.copy(
                firstName = firstName
            )
        }
    }

    fun onMiddleNameChanged(middleName: String) {
        formState.update { it ->
            it.copy(
                middleName = middleName
            )
        }
    }

    fun onLastNameChanged(lastName: String) {
        formState.update { it ->
            it.copy(
                lastName = lastName
            )
        }
    }

    fun onContactNumberChanged(newPhoneNumber: String) {
        formState.update { it ->
            it.copy(
                contactNumber = newPhoneNumber
            )
        }
    }

    fun onEmailChanged(newEmail: String) {
        formState.update { it ->
            it.copy(
                email = newEmail
            )
        }
    }

    fun onAddressLineOneChanged(newAddress1: String) {
        formState.update { it ->
            it.copy(
                addressLineOne = newAddress1
            )
        }
    }

    fun onAddressLineTwoChanged(newAddress2: String) {
        formState.update { it ->
            it.copy(
                addressLineTwo = newAddress2
            )
        }
    }

    fun city(newCity: String) {
        formState.update { it ->
            it.copy(
                city = newCity
            )
        }
    }

    fun state(newState: String) {
        formState.update {
            it.copy(
                state = newState
            )
        }
    }

    fun district(newDistrict: String) {
        formState.update {
            it.copy(
                district = newDistrict
            )
        }
    }

    fun zipCode(newZipCode: String) {
        formState.update {
            it.copy(
                zipCode = newZipCode
            )
        }
    }

    fun country(newCountry: String) {
        formState.update {
            it.copy(
                country = newCountry
            )
        }
    }

    fun clinicLocationUrl(Url: String) {
        formState.update {
            it.copy(
                clinicLocationUrl = Url
            )
        }
    }

    fun onSalutationDropDownPressed(newValue: Boolean) {
        formState.update { it ->
            it.copy(
                salutationDropDownexpandedState = newValue,
                salutationoptionList = salutationItemList
            )
        }
    }

    fun onSalutationItemClicked(newItem: String) {
        formState.update { it ->
            it.copy(
                salutationselectedOptions = newItem,
                salutationDropDownexpandedState = false
            )
        }
    }

    fun onGenderDropDownPressed(newValue: Boolean) {
        formState.update { it ->
            it.copy(
                genderexpandedState = newValue,
                genderoptionList = genderList
            )
        }
    }

    fun onGenderItemClicked(newItem: String) {
        formState.update { it ->
            it.copy(
                genderselectedOptions = newItem,
                genderexpandedState = false
            )
        }
    }

    fun onDepartmentDropDownPressed(newValue: Boolean) {
        formState.update { it ->
            it.copy(
                departmentexpandedState = newValue,
                departmentoptionList = departmentList
            )
        }
    }

    fun onDepartmentItemClicked(newItem: String) {
        formState.update { it ->
            it.copy(
                departmentselectedOptions = newItem,
                departmentexpandedState = false
            )
        }
    }

    fun onBloodGroupDropDownPressed(newValue: Boolean) {
        formState.update { it ->
            it.copy(
                bloodGroupexpandedState = newValue,
                bloodGroupoptionList = bloodGroupList
            )
        }
    }

    fun onBloodGroupItemClicked(newItem: String) {
        formState.update { it ->
            it.copy(
                bloodGroupselectedOptions = newItem,
                bloodGroupexpandedState = false
            )
        }
    }


    fun onAddressDropDownPressed(newValue: Boolean) {
        formState.update { it ->
            it.copy(
                addressexpandedState = newValue,
                addressoptionList = addressList
            )
        }
    }

    fun onAddressItemClicked(newItem: String) {
        formState.update { it ->
            it.copy(
                addressselectedOptions = newItem,
                addressexpandedState = false
            )
        }
    }

    // function to update the date fields
    fun onDateOfBirthChange(newDateOfBirth: String) {
        formState.update { it ->
            it.copy(
                dateOfBirth = newDateOfBirth
            )
        }
    }

    fun onDateOfJoiningChange(newDateOfJoining: String) {
        formState.update { it ->
            it.copy(
                dateOfJoining = newDateOfJoining
            )
        }
    }


    // update document fields
    fun updateProfilePhoto(uri: Uri?, name: String?) {
       formState.update {
           it.copy(
               profilePhotoUri = uri,
               profilePhotoName = name
           )
       }
    }

    fun updateSignature(uri: Uri?, name: String?) {
        formState.update {
            it.copy(
                signatureUri = uri,
                signatureName = name
            )
        }
    }

    fun updateDoctorIdProof(uri: Uri?, name: String?) {
        formState.update {
            it.copy(
                doctorIdProofName = name,
                doctorIdProofUri = uri
            )
        }
    }

    fun updateDoctorLicense(uri: Uri?, name: String?) {
        formState.update {
            it.copy(
                doctorLicenseUri = uri,
                doctorLicenseName = name
            )
        }
    }

    fun updateMbbsCertificate(uri: Uri?, name: String?) {
        formState.update {
            it.copy(
                mbbsCertificateUri = uri,
                mbbsCertificateName = name
            )
        }
    }

    fun updateExperienceCertificate(uri: Uri?, name: String?) {
        formState.update {
            it.copy(
                experienceCertificateUri = uri,
                experienceCertificateName = name
            )
        }
    }

    // create completeRegistration process function
    // In formViewModel.kt - Replace the CompleteRegistration function

    fun CompleteRegistration(doctorId: String) {
        viewModelScope.launch {
            doctorProfileState.value = DoctorProfileUiState.Loading
            val currentState = formState.value

            Log.d("FormDebug", "Starting registration for doctorId: $doctorId")
            Log.d("FormDebug", "Department Selected: ${currentState.departmentselectedOptions}")
            // Debug log
            //  DETAILED LOGGING FOR DEBUG
            Log.d("FormDebug", "=== Registration Started ===")
            Log.d("FormDebug", "Doctor ID: $doctorId")
            Log.d("FormDebug", "Salutation: '${currentState.salutationselectedOptions}'")
            Log.d("FormDebug", "First Name: '${currentState.firstName}'")
            Log.d("FormDebug", "Middle Name: '${currentState.middleName}'")
            Log.d("FormDebug", "Last Name: '${currentState.lastName}'")
            Log.d("FormDebug", "DOB: '${currentState.dateOfBirth}'")
            Log.d("FormDebug", "Gender: '${currentState.genderselectedOptions}'")

            Log.d("FormDebug", "Email: '${currentState.email}'")
            Log.d("FormDebug", "Contact: '${currentState.contactNumber}'")
            Log.d("FormDebug", "Department: '${currentState.departmentselectedOptions}'")
            Log.d("FormDebug", "DOJ: '${currentState.dateOfJoining}'")
            Log.d("FormDebug", "Blood Group: '${currentState.bloodGroupselectedOptions}'")

            Log.d("FormDebug", "Address Options: '${currentState.addressselectedOptions}'")
            Log.d("FormDebug", "Address line 1: '${currentState.addressLineOne}'")
            Log.d("FormDebug", "address line 2: '${currentState.addressLineTwo}'")
            Log.d("FormDebug", "City: '${currentState.city}'")
            Log.d("FormDebug", "State: '${currentState.state}'")
            Log.d("FormDebug", "District: '${currentState.district}'")
            Log.d("FormDebug", "zipCode: '${currentState.zipCode}'")
            Log.d("FormDebug", "Country: '${currentState.country}'")
            Log.d("FormDebug", "clinicLocation Url: '${currentState.clinicLocationUrl}'")


            // Validate all required fields before proceeding
            if (currentState.salutationselectedOptions.isEmpty() ||
                currentState.firstName.isEmpty() ||
                currentState.lastName.isEmpty() ||
                currentState.dateOfBirth.isEmpty() ||
                currentState.genderselectedOptions.isEmpty() ||
                currentState.email.isEmpty() ||
                currentState.contactNumber.isEmpty() ||
                currentState.departmentselectedOptions.isEmpty() ||
                currentState.dateOfJoining.isEmpty() ||
                currentState.bloodGroupselectedOptions.isEmpty() ||
                currentState.addressselectedOptions.isEmpty() ||
                currentState.addressLineOne.isEmpty() ||
                currentState.city.isEmpty() ||
                currentState.state.isEmpty() ||
                currentState.zipCode.isEmpty() ||
                currentState.country.isEmpty()
            ) {
                doctorProfileState.value = DoctorProfileUiState.Error("Please fill all required fields")
                return@launch
            }

            // departmentId store the value of the particular key
            val departmentId = departmentIdMap[currentState.departmentselectedOptions] // here the departmentSelectedOptions is key

            if (departmentId == null) {
                Log.e("FormError", "Department ID not found for: ${currentState.departmentselectedOptions}")
                doctorProfileState.value = DoctorProfileUiState.Error(
                    "Invalid department selected. Please contact support or select 'Cardiology' to test."
                )
                return@launch
            }

            Log.d("FormDebug", "Department ID mapped to: $departmentId")

            val request = UpdateDoctorProfileRequest(
                // Personal Information
                salutation = currentState.salutationselectedOptions,
                first_name = currentState.firstName,
                middle_name = currentState.middleName,
                last_name = currentState.lastName,
                dateofbirth = currentState.dateOfBirth,  // Repository will convert to ISO
                gender = currentState.genderselectedOptions,

                // Contact Information
                email = currentState.email,
                contact_no = currentState.contactNumber.toLongOrNull() ?: 0L,

                // Professional Details
                employee_department = departmentId,
                date_of_joinning = currentState.dateOfJoining,  // Repository will convert to ISO
                bloodGroup = currentState.bloodGroupselectedOptions,

                // Address with all fields
                contact_address = ContactAddress(
                    addressType = currentState.addressselectedOptions,
                    addressLine1 = currentState.addressLineOne,
                    addressLine2 = currentState.addressLineTwo,
                    city = currentState.city,
                    state = currentState.state,
                    district = currentState.district,
                    zipCode = currentState.zipCode,
                    country = currentState.country,
                    clinicLocationUrl = currentState.clinicLocationUrl
                )
            )


            Log.d("FormDebug", "Request created successfully")


            // Convert Uri to File
            val profilePhotoFile = currentState.profilePhotoUri?.let { uri ->
                uriToTempFile(uri, currentState.profilePhotoName ?: "profile_photo.jpg", "image/jpeg")
            }
            val signatureFile = currentState.signatureUri?.let { uri ->
                uriToTempFile(uri, currentState.signatureName ?: "signature.jpg", "image/jpeg")
            }
            val idProofFile = currentState.doctorIdProofUri?.let { uri ->
                uriToTempFile(uri, currentState.doctorIdProofName ?: "id_proof.pdf", "application/pdf")
            }
            val licenseFile = currentState.doctorLicenseUri?.let { uri ->
                uriToTempFile(uri, currentState.doctorLicenseName ?: "license.pdf", "application/pdf")
            }
            val mbbsFile = currentState.mbbsCertificateUri?.let { uri ->
                uriToTempFile(uri, currentState.mbbsCertificateName ?: "mbbs.pdf", "application/pdf")
            }
            val experienceFile = currentState.experienceCertificateUri?.let { uri ->
                uriToTempFile(uri, currentState.experienceCertificateName ?: "experience.pdf", "application/pdf")
            }

            Log.d("FormDebug", "Files converted. Calling API...")

            val result = formRepository.updateDoctorProfile(
                doctorId = doctorId,
                request = request,
                imageFile = profilePhotoFile,
                signatureFile = signatureFile,
                idProofFile = idProofFile,
                licenseFile = licenseFile,
                mbbsFile = mbbsFile,
                experienceFile = experienceFile
            )

            result.fold(
                onSuccess = { response ->
                    Log.d("FormDebug", " SUCCESS! Response: $response")
                    doctorProfileState.value = DoctorProfileUiState.Success(response)
                },
                onFailure = { exception ->
                    Log.e("FormError", "FAILURE: ${exception.message}", exception)
                    doctorProfileState.value = DoctorProfileUiState.Error(exception.message ?: "Unknown error occurred")
                }
            )
        }
    }

    // Helper function for calculating experience
    private fun calculateYearsOfExperience(dateOfJoining: String): Int {
        return try {
            val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val joiningDate = formatter.parse(dateOfJoining)
            val currentDate = Date()
            val diff = currentDate.time - (joiningDate?.time ?: 0)
            val years = diff / (1000L * 60 * 60 * 24 * 365)
            years.toInt()
        } catch (e: Exception) {
            0
        }
    }

    // This should already be in your formViewModel class
    private fun uriToTempFile(uri: Uri, fileName: String, mimeType: String): File {
        val tempFile = File(context.cacheDir, fileName)
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        } ?: throw IllegalStateException("Failed to read Uri: $uri")
        return tempFile
    }

    sealed class DoctorProfileUiState {
        data object Idle : DoctorProfileUiState()
        data object Loading : DoctorProfileUiState()
        data class Success(val response: DoctorProfileResponse) : DoctorProfileUiState()
        data class Error(val message: String) : DoctorProfileUiState()
    }
}