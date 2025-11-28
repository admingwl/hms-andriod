package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName

data class AppointmentApiResponse(
    val message: AppointmentMessage
)

data class AppointmentMessage(
    val nextAppointmentDateTime: String?,
    @SerializedName("_id")
    val id: String,
    val reason: String,
    val patient: Patient,
    val appointmentDate: String,
    val appointmentTime: String,
    val department: Department,
    val doctor: Doctor,
    val visitType: String,
    val status: String,
    val createdBy: String,
    val companyId: String,
    val createdAt: String,
    val appointmentId: String,
    @SerializedName("__v")
    val version: Int
)

data class Patient(
    @SerializedName("_id")
    val id: String,
    val age: Age,
    val address: Address,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("previous_last_name")
    val previousLastName: String,
    val prefix: String,
    @SerializedName("Age")
    val ageFormatted: String,
    val dateOfBirth: String,
    val gender: String,
    val maritalStatus: String,
    val religion: String,
    val race: String,
    val bloodGroup: String,
    val contactNumber: String,
    val landlineNumber: String,
    val email: String,
    val isDialysisPatient: Boolean,
    val createdBy: String,
    val companyId: String,
    val createdAt: String,
    val updatedAt: String,
    val patientId: String,
    @SerializedName("__v")
    val version: Int,
    val allergies: List<Allergy>?,
    val conditions: List<Condition>?
)

data class Age(
    val value: Int,
    val unit: String
)

data class Address(
    val addressType: String,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val district: String,
    val wardNumber: String,
    val zipCode: String,
    val country: String
)

data class Allergy(
    @SerializedName("_id")
    val id: String,
    val allergen: String,
    val allergyType: String,
    val severity: String,
    val reaction: String,
    val recordedBy: String,
    val recordedDate: String
)

data class Condition(
    @SerializedName("_id")
    val id: String,
    val condition: String,
    val onsetDate: String,
    val status: String,
    val severity: String
)

data class Department(
    @SerializedName("_id")
    val id: String,
    val departmentCode: String,
    val companyId: String,
    val departmentName: String,
    val parentDepartmentName: String?,
    val departmentDescription: String,
    val departmentNoticeText: String,
    val departmentHead: String,
    val roomNumber: String,
    val isActive: String,
    val isAppointmentApplicable: String,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("__v")
    val version: Int
)

data class Doctor(
    @SerializedName("_id")
    val id: String,
    @SerializedName("contact_address")
    val contactAddress: Address,
    val salutation: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("middle_name")
    val middleName: String,
    @SerializedName("last_name")
    val lastName: String,
    val dateofbirth: String,
    val gender: String,
    @SerializedName("kmpdc_no")
    val kmpdcNo: Int,
    @SerializedName("knnc_no")
    val knncNo: Int,
    @SerializedName("knhpc_no")
    val knhpcNo: Int,
    @SerializedName("contact_no")
    val contactNo: Int,
    @SerializedName("email_id")
    val emailId: String,
    @SerializedName("signature_sort")
    val signatureSort: String,
    @SerializedName("signature_long")
    val signatureLong: String,
    @SerializedName("appointment_applicable")
    val appointmentApplicable: String,
    @SerializedName("employee_department")
    val employeeDepartment: String,
    @SerializedName("employee_role")
    val employeeRole: String,
    @SerializedName("employee_type")
    val employeeType: String,
    @SerializedName("date_of_joinning")
    val dateOfJoining: String,
    val krapin: String,
    val taxpercentage: String,
    val isIncentiveApplicable: String,
    val extension: String,
    val speeDial: String,
    val officeHour: String,
    val roomNumber: String,
    val bloodGroup: String,
    val drivingLicenceNo: String,
    val isAcive: String,
    val radiologySignature: String,
    val displaySequence: Int,
    val signatureImage: String,
    val companyId: String,
    val appointmentSlots: List<AppointmentSlot>?,
    val createdAt: String,
    val updatedAt: String,
    @SerializedName("__v")
    val version: Int,
    val isVerified: Boolean
)

data class AppointmentSlot(
    @SerializedName("_id")
    val id: String,
    val date: String,
    val slots: List<Slot>
)

data class Slot(
    @SerializedName("_id")
    val id: String,
    val slotTime: String,
    val period: String,
    val isBooked: Boolean
)