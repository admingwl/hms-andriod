package com.example.happydocx.PdfGeneration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import androidx.annotation.RequiresApi
import com.example.happydocx.Data.Model.StartConsulting.PrescriptionRecord
import com.example.happydocx.Data.Model.StartConsulting.VitalSign
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.max

private const val TOP_MARGIN = 40f
private const val BOTTOM_MARGIN = 60f
private const val PAGE_WIDTH = 595    // A4 in points
private const val PAGE_HEIGHT = 842

@RequiresApi(Build.VERSION_CODES.O)
class PrescriptionPdfGenerator(private val context: Context) {

    // Paints
    private val titlePaint = Paint().apply {
        color = Color.BLACK
        textSize = 30f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
    }

    private val headerPaint = Paint().apply {
        color = Color.BLACK
        textSize = 24f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
    }

    private val labelPaint = Paint().apply {
        color = Color.GRAY
        textSize = 14f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        isAntiAlias = true
    }

    private val valuePaint = Paint().apply {
        color = Color.BLACK
        textSize = 13f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        isAntiAlias = true
    }

    private val sectionTitlePaint = Paint().apply {
        color = Color.BLACK
        textSize = 17f
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

    private val contentWidth = PAGE_WIDTH - (2 * 40f) // 40f margin both sides

    fun generatePdf(record: PrescriptionRecord): File? {
        val pdfDocument = PdfDocument()

        var pageNumber = 1
        var page = pdfDocument.startPage(
            PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create()
        )
        var canvas: Canvas = page.canvas
        var y = TOP_MARGIN

        try {
            // Header
            y = drawHeader(canvas, y)
            y += 30f

            // Patient Info
            y = ensureSpace(pdfDocument, page, y, 140f, ++pageNumber) { page = it; canvas = page.canvas }
            y = drawPatientInfo(canvas, y, record)
            y += 30f

            // Vital Signs
            y = ensureSpace(pdfDocument, page, y, 220f, ++pageNumber) { page = it; canvas = page.canvas }
            y = drawVitalSigns(canvas, y, record)
            y += 30f

            // Investigation Details
            y = ensureSpace(pdfDocument, page, y, 240f, ++pageNumber) { page = it; canvas = page.canvas }
            y = drawInvestigationDetails(canvas, y, record)
            y += 30f

            // Physician Notes (multi-page capable)
            val (newPage, newY) = drawPhysicianNotes(pdfDocument, page, canvas, y, record, pageNumber)
            page = newPage
            canvas = page.canvas
            y = newY
            y += 30f
            pageNumber = pdfDocument.pages.size

            // Medication Orders
            y = ensureSpace(pdfDocument, page, y, 300f, ++pageNumber) { page = it; canvas = page.canvas }
            y = drawMedicationOrders(canvas, y, record)

            pdfDocument.finishPage(page)

            // Save file
            val timestamp = System.currentTimeMillis()
            val patientName = "${record.patient?.firstName.orEmpty()}_${record.patient?.lastName.orEmpty()}".trim()
            val fileName = if (patientName.isNotEmpty()) {
                "Prescription_${patientName}_$timestamp.pdf"
            } else {
                "Prescription_$timestamp.pdf"
            }

            val dir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) ?: return null
            val file = File(dir, fileName)

            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()

            return file
        } catch (e: Exception) {
            e.printStackTrace()
            pdfDocument.close()
            return null
        }
    }

    /**
     * Checks if there's enough space left on current page.
     * If not → finishes current page and starts new one.
     */
    private inline fun ensureSpace(
        pdfDocument: PdfDocument,
        currentPage: PdfDocument.Page,
        currentY: Float,
        requiredHeight: Float,
        nextPageNumber: Int,
        updatePage: (PdfDocument.Page) -> Unit
    ): Float {
        if (currentY + requiredHeight > PAGE_HEIGHT - BOTTOM_MARGIN) {
            pdfDocument.finishPage(currentPage)
            val newPage = pdfDocument.startPage(
                PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, nextPageNumber).create()
            )
            updatePage(newPage)
            return TOP_MARGIN
        }
        return currentY
    }

    private fun drawHeader(canvas: Canvas, startY: Float): Float {
        var y = startY
        val logoSize = 40f

        // Logo placeholder
        val logoPaint = Paint().apply {
            color = Color.parseColor("#2E3B55")
            style = Paint.Style.FILL
            isAntiAlias = true
        }
        canvas.drawCircle(PAGE_WIDTH / 2f, y + logoSize / 2, logoSize / 2, logoPaint)
        y += logoSize + 10f

        // Clinic name
        val clinicNamePaint = Paint().apply {
            color = Color.BLACK
            textSize = 18f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("ABC CLINIC", PAGE_WIDTH / 2f, y, clinicNamePaint)
        y += 20f

        // Address
        val addressPaint = Paint().apply {
            color = Color.GRAY
            textSize = 9f
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
        canvas.drawText("123 Medical Street, Healthcare District", PAGE_WIDTH / 2f, y, addressPaint)
        y += 12f
        canvas.drawText("Utako Idoko Greeny, 252650 • Tel: +234 700 000 000", PAGE_WIDTH / 2f, y, addressPaint)
        y += 15f

        // Line
        canvas.drawLine(40f, y, PAGE_WIDTH - 40f, y, linePaint)
        y += 20f

        return y
    }

    private fun drawPatientInfo(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY
        val patient = record.patient
        val appointment = record.appointment

        val col1X = 40f
        val col2X = PAGE_WIDTH / 2f + 20f

        // Left column
        y = drawLabelValue(canvas, "PATIENT NAME", "${patient?.firstName.orEmpty()} ${patient?.lastName.orEmpty()}", col1X, y)
        y = drawLabelValue(canvas, "APPOINTMENT DATE", formatDate(appointment?.appointmentDate), col1X, y)
        y = drawLabelValue(canvas, "ATTENDING PHYSICIAN", "Dr. ${record.physician?.firstName.orEmpty()} ${record.physician?.lastName.orEmpty()}", col1X, y)

        val address = patient?.let { "Flat 402, Garden View Apartments, Langa, Eldoret" } ?: "N/A"
        y = drawLabelValue(canvas, "ADDRESS", address, col1X, y)

        // Right column
        var y2 = startY
        y2 = drawLabelValue(canvas, "PATIENT NO.", "PAT-3e8b-ff8d", col2X, y2)
        y2 = drawLabelValue(canvas, "AGE / GENDER", "${patient?.ageString ?: "N/A"} / ${patient?.gender ?: "N/A"}", col2X, y2)

        return max(y, y2) + 10f
    }

    private fun drawVitalSigns(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY

        canvas.drawText("PATIENT VITAL SIGNS", 40f, y, sectionTitlePaint)
        y += 25f

        record.patientVitalSigns?.forEach { vital ->
            y = drawVitalSignRecord(canvas, y, vital)
            y += 20f
        }

        return y
    }

    private fun drawVitalSignRecord(canvas: Canvas, startY: Float, vital: VitalSign): Float {
        var y = startY

        canvas.drawText("RECORDED AT ${vital.recordedAt ?: "N/A"}", 40f, y, labelPaint)
        y += 32f

        val colWidth = contentWidth / 4f
        val cols = listOf(40f, 40f + colWidth, 40f + 2 * colWidth, 40f + 3 * colWidth)

        drawVitalItem(canvas, cols[0], y, "BLOOD PRESSURE", vital.bloodPressure ?: "N/A")
        drawVitalItem(canvas, cols[1], y, "HEART RATE", "${vital.heartRate ?: "N/A"} bpm")
        drawVitalItem(canvas, cols[2], y, "TEMP", "${vital.temperature ?: "N/A"}°C")

        y += 35f

        drawVitalItem(canvas, cols[0], y, "WEIGHT", "${vital.weight ?: "N/A"} kg")
        drawVitalItem(canvas, cols[1], y, "HEIGHT", "${vital.height ?: "N/A"} cm")

        y += 30f
        canvas.drawLine(40f, y, PAGE_WIDTH - 40f, y, dividerPaint)
        y += 10f

        return y
    }

    private fun drawVitalItem(canvas: Canvas, x: Float, y: Float, label: String, value: String) {
        canvas.drawText(label, x, y, labelPaint)
        canvas.drawText(value, x, y + 16f, valuePaint)
    }

    private fun drawInvestigationDetails(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY

        canvas.drawText("INVESTIGATION DETAILS", 40f, y, sectionTitlePaint)
        y += 25f

        val investigation = record.investigation

        // Diagnosis
        canvas.drawText("DIAGNOSIS", 40f, y, labelPaint)
        y += 18f

        investigation?.problemDiagnosis?.forEach { diag ->
            canvas.drawText(diag.diagnosis ?: "N/A", 40f, y, valuePaint)
            y += 14f
            val details = "Severity: ${diag.severity} • Duration: ${diag.duration}"
            canvas.drawText(details, 40f, y, labelPaint)
            y += 18f
        }

        y += 8f

        // Primary Symptom
        canvas.drawText("PRIMARY SYMPTOM", 40f, y, labelPaint)
        y += 18f

        investigation?.symptoms?.firstOrNull()?.let { symptom ->
            canvas.drawText(symptom.symptom ?: "N/A", 40f, y, valuePaint)
            y += 14f
            val details = "Severity: ${symptom.severity} • Status: Active"
            canvas.drawText(details, 40f, y, labelPaint)
            y += 20f
        } ?: run {
            canvas.drawText("No primary symptom recorded", 40f, y, valuePaint)
            y += 20f
        }

        return y
    }

    private fun drawPhysicianNotes(
        pdfDocument: PdfDocument,
        currentPage: PdfDocument.Page,
        canvas: Canvas,
        startY: Float,
        record: PrescriptionRecord,
        pageNumber: Int
    ): Pair<PdfDocument.Page, Float> {

        var page = currentPage
        var y = startY
        var currentCanvas = canvas
        var currentPageNumber = pageNumber

        val notes = record.investigation?.notes ?: "No physician notes available."

        val textPaint = TextPaint(valuePaint).apply { textSize = 12f }

        val staticLayout = StaticLayout.Builder.obtain(
            notes, 0, notes.length, textPaint, contentWidth.toInt()
        )
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(4f, 1f)
            .build()

        val neededHeight = 25f + staticLayout.height + 30f

        if (y + neededHeight > PAGE_HEIGHT - BOTTOM_MARGIN) {
            pdfDocument.finishPage(page)
            currentPageNumber++
            page = pdfDocument.startPage(
                PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, currentPageNumber).create()
            )
            currentCanvas = page.canvas
            y = TOP_MARGIN
        }

        currentCanvas.drawText("PHYSICIAN NOTES", 40f, y, sectionTitlePaint)
        y += 25f

        currentCanvas.save()
        currentCanvas.translate(40f, y)
        staticLayout.draw(currentCanvas)
        currentCanvas.restore()

        y += staticLayout.height + 30f

        return page to y
    }

    private fun drawMedicationOrders(canvas: Canvas, startY: Float, record: PrescriptionRecord): Float {
        var y = startY

        canvas.drawText("MEDICATION ORDERS", 40f, y, sectionTitlePaint)
        y += 25f

        record.medicationOrders?.forEach { med ->
            y = drawMedicationItem(canvas, y, med)
            y += 25f
        }

        return y
    }

    private fun drawMedicationItem(
        canvas: Canvas,
        startY: Float,
        medication: com.example.happydocx.Data.Model.StartConsulting.MedicationOrderOne
    ): Float {

        var y = startY
        val col1X = 40f
        val col2X = PAGE_WIDTH / 2f + 20f

        // Left column
        y = drawLabelValue(canvas, "GENERIC", medication.genericName ?: "N/A", col1X, y)
        y = drawLabelValue(canvas, "FREQUENCY", "2x Daily", col1X, y)
        y = drawLabelValue(canvas, "DURATION", medication.duration ?: "N/A", col1X, y)
        y = drawLabelValue(canvas, "SCHEDULE", "Morning, Empty Stomach", col1X, y)

        // Right column
        var y2 = startY
        y2 = drawLabelValue(canvas, "BRAND", "Glucophage", col2X, y2)
        y2 = drawLabelValue(canvas, "DOSAGE", medication.strength ?: "N/A", col2X, y2)
        y2 = drawLabelValue(canvas, "ROUTE", "Oral", col2X, y2)

        return max(y, y2) + 10f
    }

    private fun drawLabelValue(
        canvas: Canvas,
        label: String,
        value: String,
        x: Float,
        y: Float
    ): Float {
        canvas.drawText(label, x, y, labelPaint)
        //control vertical space here ↓↓↓
        val offsetValue =  18f
        canvas.drawText(value, x, y + offsetValue, valuePaint)
        val rowHeight = 40f
        return y + rowHeight
    }

    private fun formatDate(dateString: String?): String {
        if (dateString.isNullOrBlank()) return "N/A"

        return try {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val output = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            input.parse(dateString)?.let { output.format(it) } ?: dateString
        } catch (e: Exception) {
            dateString
        }
    }
}