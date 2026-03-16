package com.example.happydocx.ui.uiStates.StartConsulting

import android.net.Uri
import kotlinx.serialization.BinaryFormat

data class StartConsultingUiStateUpdated1(
  // save Vital Signs State
    val heartRate:String = "",
    val oxygenSaturation:String = "",
    val systolicBp:String = "",
    val diastolicBp:String = "",
    val respiratoryRate:String = "",
    val temperature:String = "",
    val weight:String = "",
)

// add medication ui state
data class AddMedicationUpdated1(
        val medicationName:String="",
        val medicationDosage:String="",
        val medicationFrequency:String = "",
        val medicationDuration:String="",
        val medicationRoute:String ="",
        val medicationTiming:String = "",
        val medicationDate:String="",
        val medicationNotes:String=""
)

// manual entry for creating lab report
data class AddLabResultManualUpdate1(
    val testName:String = "",
    val resultValue:String = "",
    val unit:String = "",
    val testDate:String = "",
    val normalRange:String="",
    val status:String="",
    val notes:String= ""
)

// upload lab report
data class UploadLabReportUpdate1(
    val reportDate:String = "",
    val reportType:String="",
    val laboratoryName:String="",
    val notes:String=""
)

data class UploadDocumentUpdate1(
    val documentName:String = "",
    val documentType:String = "",
    val reportDate:String = "",
    val attachmentURI: Uri? = null,
    val attachmentName:String = ""
)

// notes ui state
data class ConsultationNotesUpdate1(
    val chiefComplaint:String = "",
    val historyOfPresentIllness:String = "",
    val physicalExamination:String ="",
    val assessmentAndDiagnosis:String = "",
    val treatmentPlan:String = "",
    val followUp:String = "",
    val priority:String = "",

    val medications:List<MedicationItem> = listOf(MedicationItem(id = 0)),

    val labTest:String = "",
    val imagingStudies:String = "",
    val referrals:String = "",
    val urgency:String = "",
    val expectedTimeline:String = "",
)

data class MedicationItem(
    val id: Int = 0,
    val medicationName: String = "",
    val dosage: String = "",
    val frequency: String = "",
    val duration: String = "",
    val mealTime: String = "",
    val specialInstructions: String = ""
)