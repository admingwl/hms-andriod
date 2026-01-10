package com.example.happydocx.PdfGeneration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.example.happydocx.Data.Model.StartConsulting.PrescriptionRecord
import com.example.happydocx.Data.Model.StartConsulting.VitalSign
import com.example.happydocx.Utils.DateUtils
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale


class PrescriptionPdfGenerator(private val context: Context) {

    private val pageWidth = 595 // A4 width in points
    private val pageHeight = 842 // A4 height in points
    private val margin = 40f
    private val contentWidth = pageWidth - (2 * margin)

    // Paint objects
    private val titlePaint = Paint().apply {
        color = Color.BLACK
        textSize = 20f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
    }

    private val headerPaint = Paint().apply {
        color = Color.BLACK
        textSize = 14f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
    }

    private val labelPaint = Paint().apply {
        color = Color.GRAY
        textSize = 10f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        isAntiAlias = true
    }

    private val valuePaint = Paint().apply {
        color = Color.BLACK
        textSize = 11f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        isAntiAlias = true
    }

    private val sectionTitlePaint = Paint().apply {
        color = Color.BLACK
        textSize = 11f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
        letterSpacing = 0.1f
    }

    private val linePaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 1f
        isAntiAlias = true
    }

    private val dividerPaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 0.5f
        isAntiAlias = true
    }

    // here the whole pdf is generated with the different designs .
    @RequiresApi(Build.VERSION_CODES.O)
    fun generatePdf(record: PrescriptionRecord): File? {
        val pdfDocument = PdfDocument()
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        var page = pdfDocument.startPage(pageInfo)
        var canvas = page.canvas
        var yPosition = margin

        try {
            // Header with logo and clinic info
            yPosition = drawHeader(canvas, yPosition)
            yPosition += 30f

            // Patient Info Section
            yPosition = drawPatientInfo(canvas, yPosition, record)
            yPosition += 25f

            // Patient Vital Signs Section
            yPosition = drawVitalSigns(canvas, yPosition, record)
            yPosition += 25f

            // Investigation Details
            yPosition = drawInvestigationDetails(canvas, yPosition, record)
            yPosition += 25f

            // Physician Notes
            yPosition = drawPhysicianNotes(canvas, yPosition, record)
            yPosition += 25f

            // Check if we need a new page for medication orders
            if (yPosition > pageHeight - 150) {
                pdfDocument.finishPage(page)
                pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 2).create()
                page = pdfDocument.startPage(pageInfo)
                canvas = page.canvas
                yPosition = margin
            }

            // Medication Orders
            yPosition = drawMedicationOrders(canvas, yPosition, record)

            pdfDocument.finishPage(page)

            // Save PDF
            val fileName = "Prescription_${record.patient?.firstName}_${System.currentTimeMillis()}.pdf"
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
                fileName
            )

            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            return file
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            return null
        }
    }

    private fun drawHeader(canvas: Canvas, startY: Float): Float {
        var y = startY

        // Draw clinic logo placeholder (shield icon)
        val logoSize = 40f
        val logoPaint = Paint().apply {
            color = Color.parseColor("#2E3B55")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawCircle(pageWidth / 2f, y + logoSize / 2, logoSize / 2, logoPaint)
        y += logoSize + 10f

        // Clinic name
        val clinicNamePaint = Paint().apply {
            color = Color.BLACK
            textSize = 18f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("ABC CLINIC", pageWidth / 2f, y, clinicNamePaint)
        y += 20f

        // Clinic address
        val addressPaint = Paint().apply {
            color = Color.GRAY
            textSize = 9f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("123 Medical Street, Healthcare District", pageWidth / 2f, y, addressPaint)
        y += 12f
        canvas.drawText("Utako Idoko Greeny, 252650 • Tel: +234 700 000 000", pageWidth / 2f, y, addressPaint)
        y += 15f

        // Horizontal line
        canvas.drawLine(margin, y, pageWidth - margin, y, linePaint)
        y += 20f

        return y
    }

    private fun drawPatientInfo(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY
        val patient = record.patient
        val appointment = record.appointment

        // Two column layout
        val col1X = margin
        val col2X = pageWidth / 2f + 20f

        // Column 1
        y = drawLabelValue(canvas, "PATIENT NAME",
            "${patient?.firstName ?: ""} ${patient?.lastName ?: ""}", col1X, y)
        y += 5f

        y = drawLabelValue(canvas, "APPOINTMENT DATE",
            formatDate(appointment?.appointmentDate), col1X, y)
        y += 5f

        y = drawLabelValue(canvas, "ATTENDING PHYSICIAN",
            "Dr. ${record.physician?.firstName ?: ""} ${record.physician?.lastName ?: ""}", col1X, y)
        y += 5f

        val addressLines = mutableListOf<String>()
        patient?.let { p ->
            val address = "Flat 402, Garden View Apartments, Langa, Eldoret"
            addressLines.add(address)
        }

        y = drawLabelValue(canvas, "ADDRESS", addressLines.firstOrNull() ?: "N/A", col1X, y)

        // Column 2 (reset y to startY)
        var y2 = startY
        y2 = drawLabelValue(canvas, "PATIENT NO.",
            "PAT-3e8b-ff8d", col2X, y2)
        y2 += 5f

        y2 = drawLabelValue(canvas, "AGE / GENDER",
            "${patient?.ageString ?: "N/A"} / ${patient?.gender ?: "N/A"}", col2X, y2)

        return maxOf(y, y2) + 5f
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawVitalSigns(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY

        // Section title
        canvas.drawText("PATIENT VITAL SIGNS", margin, y, sectionTitlePaint)
        y += 20f

        record.patientVitalSigns?.forEach { vital ->
            // Draw vital sign record
            y = drawVitalSignRecord(canvas, y, vital)
            y += 15f
        }

        return y
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun drawVitalSignRecord(canvas: Canvas, startY: Float, vital: VitalSign): Float {
        var y = startY

        // Record header
        val recordTitle = "RECORDED AT ${DateUtils.formatAppointmentDate(vital.recordedAt) ?: "N/A"}"
        canvas.drawText(recordTitle, margin, y, labelPaint)
        y += 15f

        // Create 4 columns for vital signs
        val colWidth = contentWidth / 4f
        val cols = listOf(margin, margin + colWidth, margin + 2 * colWidth, margin + 3 * colWidth)

        // Blood Pressure
        drawVitalItem(canvas, cols[0], y, "BLOOD\nPRESSURE", vital.bloodPressure ?: "N/A")

        // Heart Rate
        drawVitalItem(canvas, cols[1], y, "HEART RATE", "${vital.heartRate ?: "N/A"} bpm")

        // Temperature
        drawVitalItem(canvas, cols[2], y, "TEMP", "${vital.temperature ?: "N/A"}°C")

        y += 35f

        // Weight and Height
        drawVitalItem(canvas, cols[0], y, "WEIGHT", "${vital.weight ?: "N/A"} kg")
        drawVitalItem(canvas, cols[1], y, "HEIGHT", "${vital.height ?: "N/A"} cm")

        y += 30f

        // Bottom line
        canvas.drawLine(margin, y, pageWidth - margin, y, dividerPaint)
        y += 5f

        return y
    }

    private fun drawVitalItem(canvas: Canvas, x: Float, y: Float, label: String, value: String) {
        val lines = label.split("\n")
        var currentY = y

        lines.forEach { line ->
            canvas.drawText(line, x, currentY, labelPaint)
            currentY += 12f
        }

        canvas.drawText(value, x, currentY + 5f, valuePaint)
    }

    private fun drawInvestigationDetails(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY

        canvas.drawText("INVESTIGATION DETAILS", margin, y, sectionTitlePaint)
        y += 20f

        val investigation = record.investigation

        // Diagnosis
        canvas.drawText("DIAGNOSIS", margin, y, labelPaint)
        y += 15f

        investigation?.problemDiagnosis?.forEach { diagnosis ->
            canvas.drawText(diagnosis.diagnosis ?: "N/A", margin, y, valuePaint)
            y += 12f
            val details = "Severity: ${diagnosis.severity} • Duration: ${diagnosis.duration}"
            canvas.drawText(details, margin, y, labelPaint)
            y += 15f
        }

        // Symptoms
        canvas.drawText("PRIMARY SYMPTOM", margin, y, labelPaint)
        y += 15f

        investigation?.symptoms?.firstOrNull()?.let { symptom ->
            canvas.drawText(symptom.symptom ?: "N/A", margin, y, valuePaint)
            y += 12f
            val details = "Severity: ${symptom.severity} • Status: Active"
            canvas.drawText(details, margin, y, labelPaint)
            y += 15f
        }

        return y
    }

    private fun drawPhysicianNotes(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY

        canvas.drawText("PHYSICIAN NOTES", margin, y, sectionTitlePaint)
        y += 20f

        val notes = record.investigation?.notes ?: "No notes available"

        // Word wrap the notes
        val words = notes.split(" ")
        var line = ""
        val maxWidth = contentWidth

        words.forEach { word ->
            val testLine = if (line.isEmpty()) word else "$line $word"
            if (valuePaint.measureText(testLine) > maxWidth) {
                canvas.drawText(line, margin, y, valuePaint)
                y += 15f
                line = word
            } else {
                line = testLine
            }
        }

        if (line.isNotEmpty()) {
            canvas.drawText(line, margin, y, valuePaint)
            y += 15f
        }

        return y
    }

    private fun drawMedicationOrders(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY

        canvas.drawText("MEDICATION ORDERS", margin, y, sectionTitlePaint)
        y += 20f

        record.medicationOrders?.forEach { medication ->
            y = drawMedicationItem(canvas, y, medication)
            y += 20f
        }

        return y
    }

    private fun drawMedicationItem(canvas: Canvas, startY: Float, medication: com.example.happydocx.Data.Model.StartConsulting.MedicationOrderOne): Float {
        var y = startY

        // Create two columns
        val col1X = margin
        val col2X = pageWidth / 2f + 20f

        // Column 1
        y = drawLabelValue(canvas, "GENERIC", medication.genericName ?: "N/A", col1X, y)
        y += 5f
        y = drawLabelValue(canvas, "FREQUENCY", "2x Daily", col1X, y)
        y += 5f
        y = drawLabelValue(canvas, "DURATION", medication.duration ?: "N/A", col1X, y)
        y += 5f
        y = drawLabelValue(canvas, "SCHEDULE", "Morning, Empty Stomach", col1X, y)

        // Column 2 (reset y)
        var y2 = startY
        y2 = drawLabelValue(canvas, "BRAND", "Glucophage", col2X, y2)
        y2 += 5f
        y2 = drawLabelValue(canvas, "DOSAGE", medication.strength ?: "N/A", col2X, y2)
        y2 += 5f
        y2 = drawLabelValue(canvas, "ROUTE", "Oral", col2X, y2)

        return maxOf(y, y2)
    }

    private fun drawLabelValue(canvas: Canvas, label: String, value: String, x: Float, y: Float): Float {
        canvas.drawText(label, x, y, labelPaint)
        canvas.drawText(value, x, y + 12f, valuePaint)
        return y + 27f
    }

    private fun formatDate(dateString: String?): String {
        if (dateString == null) return "N/A"
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }
}