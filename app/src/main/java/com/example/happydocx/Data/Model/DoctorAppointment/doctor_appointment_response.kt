package com.example.happydocx.Data.Model.DoctorAppointment

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import java.io.Serial

@Immutable
/**
 * Explanation of @Immutable:
 *
 * Imagine you have a toy car made of LEGO bricks. You can change its parts anytime. This is Mutable (changeable).
 * Now, imagine you have another toy car carved from a single piece of wood. You can't change it; it stays as it is. This is Immutable (unchangeable).
 *
 * Jetpack Compose builds your app's screen (UI).
 *
 * - When you give it the LEGO car (mutable data), Compose has to repeatedly check, "Has any part of this car changed?" This takes time.
 * - But when you give it the wooden car and put an `@Immutable` "promise" sticker on it, you're telling Compose, "Hey, this car will never change, so you don't need to check it again and again."
 *
 * This makes Compose's job easier and helps your app run faster and more smoothly.
 */
data class AppointmentResponse(
    @SerializedName("success")
    val success: Boolean,
    val mode:String,
    val total:Int,
    @SerializedName("page")
    val page: Int? = null,
    val limit:Int? = null,
    val data : List<Appointment>
)
data class Appointment(
    @SerializedName("_id")  // Maps JSON "_id" to your "id" field
    val id: String,
    val status:String,
    val patient:Patient,
    @SerializedName("appointmentDate") // slot
    val date:String,
    val appointmentTime:String,
    val nextAppointmentReminderSent:Boolean,
    val nextAppointmentDateTime:String?,
    val department:String,
    val doctor:String,
    val createdBy:String,
    val createdAt:String,
    val appointmentId:String,
    val companyId: String,
    @SerializedName("visitType")
    val visitType:String = "",
    val reason:String? = null
)

data class Patient(
    val age: PatientAge,
    val address: PatientAddress?,
    @SerializedName("_id")
    val _id: String,
    @SerializedName("first_name")
    val first_name : String,
    val middle_name:String,
    val last_name:String,
    val previous_last_name:String,
    val prefix:String,
    val Age:String,
    val dateOfBirth:String,
    val maritalStatus:String,
    val religion:String,
    val race:String,
    val bloodGroup:String,
    val landlineNumber:String,
    val allergies:List<String> = emptyList(),
    val email:String,
    val createdBy:String,
    val companyId:String,
    val currentMedications:List<CurrentMedicationsDoctorAppointments> = emptyList(),
    val isDialysisPatient:Boolean,
    @SerializedName("gender")
    val gender:String,
    @SerializedName("contactNumber")
    val contactNumber:String,
    @SerializedName("createdAt")
    val createdAt :String,
    val updatedAt:String,
    val patientId:String,
    val userv2Id: String? = null
)
// patient created at -> is last visit

data class PatientAge(
    val value:Int?=null,
    val unit:String
)

data class PatientAddress(
    val addressType:String,
    val addressLine1:String
)

data class CurrentMedicationsDoctorAppointments(
    val name:String,
    val dosage:String,
    val frequency:String,
    val duration:String,
    val instructions:String,
    val route:String,
    val timing:String,
    val startDate:String,
    val active:Boolean,
    val _id:String
)