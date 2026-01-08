package com.example.happydocx.Data.Model.StartConsulting

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class PrescriptionRecord(

    val _id: String,
    val investigation: InvestigationOne?,
    val notes: PriorityNotes?,
    val orders: Orders?,
    val patient: PatientOne?,
    val physician: Physician?,
    val appointment: Appointment?,
    val status: String?,
    val encounterDate: String?,
    val patientVitalSigns: List<VitalSign>?,
    val medicationOrders: List<MedicationOrderOne>?,
    val investigationOrders: List<Any>?, // Type unknown from empty array
    val createdAt: String?,
    val updatedAt: String?,
    val version: Int?,
    val prescription: List<Any>? // Type unknown from empty array
)

data class InvestigationOne(
    val problemDiagnosis: List<Diagnosis>?,
    val notes: String?,
    val symptoms: List<Symptom>?,
    val status: String?
)

data class Diagnosis(
    val diagnosis: String?,
    val severity: String?,
    val duration: String?,
    @SerializedName("_id") val id: String
)

data class Symptom(
    val symptom: String?,
    val severity: String?,
    val duration: String?,
    @SerializedName("_id") val id: String
)

data class PriorityNotes(
    val priority: String?
)

data class Orders(
    val labTests: List<Any>? // Type unknown from empty array
)

data class PatientOne(
    @SerializedName("_id") val id: String,
    val age: AgeDetail?,
    val allergies: List<Any>?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("middle_name") val middleName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("previous_last_name") val previousLastName: String?,
    val prefix: String?,
    @SerializedName("Age") val ageString: String?, // Note: JSON has both 'age' (obj) and 'Age' (string)
    val dateOfBirth: String?,
    val gender: String?,
    val maritalStatus: String?,
    val religion: String?,
    val race: String?,
    val bloodGroup: String?,
    val contactNumber: String?,
    val landlineNumber: String?,
    val email: String?,
    val isDialysisPatient: Boolean?,
    val createdBy: String?,
    val companyId: String?,
    val createdAt: String?,
    val updatedAt: String?,
    @SerializedName("__v") val version: Int?,
    val currentMedications: List<Any>?
)

data class AgeDetail(
    val value: Int?,
    val unit: String?
)

data class Physician(
    @SerializedName("_id") val id: String,
    @SerializedName("contact_address") val contactAddress: AddressOne?,
    val salutation: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("middle_name") val middleName: String?,
    @SerializedName("last_name") val lastName: String?,
    val dateofbirth: String?,
    val gender: String?,
    @SerializedName("kmpdc_no") val kmpdcNo: Long?,
    @SerializedName("knnc_no") val knncNo: Long?,
    @SerializedName("knhpc_no") val knhpcNo: Long?,
    @SerializedName("contact_no") val contactNo: Long?,
    @SerializedName("email_id") val emailId: String?,
    @SerializedName("signature_sort") val signatureSort: String?,
    @SerializedName("signature_long") val signatureLong: String?,
    @SerializedName("appointment_applicable") val appointmentApplicable: String?,
    @SerializedName("employee_department") val employeeDepartment: String?,
    @SerializedName("employee_role") val employeeRole: String?,
    @SerializedName("employee_type") val employeeType: String?,
    @SerializedName("date_of_joinning") val dateOfJoining: String?,
    val krapin: String?,
    val taxpercentage: String?,
    val isIncentiveApplicable: String?,
    val extension: String?,
    val speeDial: String?,
    val officeHour: String?,
    val roomNumber: String?,
    val bloodGroup: String?,
    val drivingLicenceNo: String?,
    val isAcive: String?,
    val radiologySignature: String?,
    val displaySequence: Int?,
    val signatureImage: String?,
    val companyId: String?,
    val appointmentSlots: List<AppointmentDay>?,
    val doctorId: String?
)

data class AddressOne(
    val addressType: String?,
    val addressLine1: String?,
    val addressLine2: String?,
    val city: String?,
    val state: String?,
    val district: String?,
    val wardNumber: String?,
    val zipCode: String?,
    val country: String?
)

data class AppointmentDay(
    val date: String?,
    val slots: List<TimeSlot>?,
    @SerializedName("_id") val id: String
)

data class TimeSlot(
    val slotTime: String?,
    val period: String?,
    val isBooked: Boolean?,
    @SerializedName("_id") val id: String
)

data class Appointment(
    @SerializedName("_id") val id: String,
    val nextAppointmentReminderSent: Boolean?,
    val reason: String?,
    val patient: String?,
    val appointmentDate: String?,
    val appointmentTime: String?,
    val nextAppointmentDateTime: String?,
    val department: String?,
    val doctor: String?,
    val visitType: String?,
    val status: String?,
    val createdBy: String?,
    val companyId: String?,
    val createdAt: String?,
    val appointmentId: String?
)

data class VitalSign(
    // These are Strings in your JSON ("1", "80"), not Ints
    val bloodPressure: String?,
    val heartRate: String?,
    val temperature: String?,
    val oxigenSaturation: String?,
    val height: String?,
    val weight: String?,
    @SerializedName("_id")
    val id: String,
    val recordedAt: String?
)

data class MedicationOrderOne(
    val genericName: String?,
    val strength: String?,
    val duration: String?,
    @SerializedName("_id") val id: String
)