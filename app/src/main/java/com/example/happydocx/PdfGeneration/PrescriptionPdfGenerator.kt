package com.example.happydocx.PdfGeneration

import android.content.Context
import android.graphics.BitmapFactory
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
import androidx.core.graphics.toColorInt
import coil3.Bitmap
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils

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

    private val sectionBackgroundPaint = Paint().apply {
        color = Color.parseColor("#F8FAFC")
        style = Paint.Style.FILL
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
            y += 20f

            // Vital Signs
            // Vital Signs - Now returns updated page and canvas because it might span pages
            val vitalResult = drawVitalSigns(pdfDocument, page, canvas, y, record, pageNumber)
            page = vitalResult.first
            canvas = vitalResult.second
            y = vitalResult.third
            pageNumber = pdfDocument.pages.size // Update current page count
            y += 20f

            // Investigation Details
            y = ensureSpace(pdfDocument, page, y, 240f, ++pageNumber) { page = it; canvas = page.canvas }
            y = drawInvestigationDetails(canvas, y, record)
            y += 20f

            // Physician Notes (multi-page capable)
            val (newPage, newY) = drawPhysicianNotes(pdfDocument, page, canvas, y, record, pageNumber)
            page = newPage
            canvas = page.canvas
            y = newY
            y += 20f
            pageNumber = pdfDocument.pages.size

            // Medication Orders
            val medRes = drawMedicationOrders(pdfDocument, page, canvas, y, record, pageNumber)
            page = medRes.first; canvas = medRes.second; y = medRes.third; pageNumber = pdfDocument.pages.size
            y += 20f

            // Investigation Orders (NEW SECTION)
            val invRes = drawInvestigationOrders(pdfDocument, page, canvas, y, record, pageNumber)
            page = invRes.first; canvas = invRes.second; y = invRes.third

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
        val leftMargin = 40f           // ← page left margin (adjust if needed)

        // ── Logo (left side) ────────────────────────────────────────
        val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.applogofinal)

        val logoWidth = 90f
        val logoHeight = 70f

        val scaledLogo = Bitmap.createScaledBitmap(
            logoBitmap,
            logoWidth.toInt(),
            logoHeight.toInt(),
            true
        )

        val logoLeft = leftMargin
        val logoTop = y + 4f           // slight top padding

        canvas.drawBitmap(scaledLogo, logoLeft, logoTop, null)


        val textLeft = leftMargin + logoWidth + 18f
        var textY = y + 32f

        // Clinic Name - bold & bigger
        val clinicNamePaint = Paint().apply {
            color = Color.BLACK
            textSize = 22f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
            textAlign = Paint.Align.LEFT
        }

        canvas.drawText("ABC CLINIC", textLeft, textY, clinicNamePaint)
        textY += 26f

        // Address lines - smaller & lighter
        val addressPaint = Paint().apply {
            color = Color.rgb(70, 70, 70)   //  dark gray
            textSize = 11f
            typeface = Typeface.DEFAULT
            isAntiAlias = true
            textAlign = Paint.Align.LEFT
        }

        canvas.drawText("100 Doctor Hill, Eldoret, Langas,", textLeft, textY, addressPaint)
        textY += 15f

        canvas.drawText("Ward B, Building D, Uasin Gishu County-30100", textLeft, textY, addressPaint)
        textY += 15f


        // ── Final height calculation ────────────────────────────────
        val bottomOfLogo = logoTop + logoHeight
        val bottomOfText = textY

        y = maxOf(bottomOfLogo, bottomOfText) + 28f   // breathing space after header

        // Optional separator line
        val linePaint = Paint().apply {
            color = Color.rgb(120, 120, 120)
            strokeWidth = 1.2f
        }
        canvas.drawLine(leftMargin, y - 12f, PAGE_WIDTH - leftMargin, y - 12f, linePaint)

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

    private fun drawVitalSigns(
        pdfDocument: PdfDocument,
        currentPage: PdfDocument.Page,
        currentCanvas: Canvas,
        startY: Float,
        record: PrescriptionRecord,
        pageNo: Int
    ): Triple<PdfDocument.Page, Canvas, Float> {
        var page = currentPage
        var canvas = currentCanvas
        var y = startY
        var currentPageNumber = pageNo

        // 1. Initial check for the Section Title
        if (y + 40f > PAGE_HEIGHT - BOTTOM_MARGIN) {
            pdfDocument.finishPage(page)
            currentPageNumber++
            page = pdfDocument.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, currentPageNumber).create())
            canvas = page.canvas
            y = TOP_MARGIN
        }

        canvas.drawText("PATIENT VITAL SIGNS", 40f, y, sectionTitlePaint)
        y += 32f

        if (record.patientVitalSigns.isNullOrEmpty()) {
            canvas.drawText("No vital signs recorded", 40f, y, valuePaint)
            return Triple(page, canvas, y + 40f)
        }

        record.patientVitalSigns?.forEachIndexed { index, vital ->
            // 2. Check space before each record (estimated height of one vital block is ~100f)
            val recordHeight = 105f
            if (y + recordHeight > PAGE_HEIGHT - BOTTOM_MARGIN) {
                pdfDocument.finishPage(page)
                currentPageNumber++
                page = pdfDocument.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, currentPageNumber).create())
                canvas = page.canvas
                y = TOP_MARGIN
            }

            y = drawVitalSignRecord(canvas, y, vital)

            if (index < record.patientVitalSigns!!.size - 1) {
                y += 4f
            }
        }

        return Triple(page, canvas, y)
    }

    private fun drawVitalSignRecord(canvas: Canvas, startY: Float, vital: VitalSign): Float {
        var y = startY

        canvas.drawText("RECORDED AT ${DateUtils.formatAppointmentDate(vital.recordedAt) ?: "N/A"}", 40f, y, labelPaint)
        y += 24f

        val colWidth = contentWidth / 3f
        val cols = listOf(40f, 40f + colWidth, 40f + 2 * colWidth, 40f + 3 * colWidth)

        drawVitalItem(canvas, cols[0], y, "BLOOD PRESSURE", vital.bloodPressure ?: "N/A")
        drawVitalItem(canvas, cols[1], y, "HEART RATE", "${vital.heartRate ?: "N/A"} bpm")
        drawVitalItem(canvas, cols[2], y, "TEMP", "${vital.temperature ?: "N/A"}°C")

        y += 38f

        drawVitalItem(canvas, cols[0], y, "WEIGHT", "${vital.weight ?: "N/A"} kg")
        drawVitalItem(canvas, cols[1], y, "HEIGHT", "${vital.height ?: "N/A"} cm")
        drawVitalItem(canvas, cols[2], y, "Oxygen Saturation", vital.oxygenSaturation ?: "N/A")

        y += 25f
        canvas.drawLine(40f, y, PAGE_WIDTH - 40f, y, dividerPaint)
        y += 9f

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

    private fun drawMedicationOrders(
        pdfDocument: PdfDocument,
        currentPage: PdfDocument.Page,
        currentCanvas: Canvas,
        startY: Float,
        record: PrescriptionRecord,
        pageNo: Int
    ): Triple<PdfDocument.Page, Canvas, Float> {
        var page = currentPage
        var canvas = currentCanvas
        var y = startY
        var currentPageNumber = pageNo

        // Check space for Section Title
        if (y + 60f > PAGE_HEIGHT - BOTTOM_MARGIN) {
            pdfDocument.finishPage(page)
            currentPageNumber++
            page = pdfDocument.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, currentPageNumber).create())
            canvas = page.canvas
            y = TOP_MARGIN
        }

        canvas.drawText("MEDICATION ORDERS", 40f, y, sectionTitlePaint)
        y += 25f

        if (record.medicationOrders.isNullOrEmpty()) {
            canvas.drawText("No medication orders", 40f, y, valuePaint)
            return Triple(page, canvas, y + 30f)
        }

        // Helper to draw the table header
        fun drawMedHeader(c: Canvas, currentY: Float): Float {
            val headerBgPaint = Paint().apply { color = Color.parseColor("#60A5FA"); style = Paint.Style.FILL; isAntiAlias = true }
            c.drawRect(40f, currentY - 5f, PAGE_WIDTH - 40f, currentY + 20f, headerBgPaint)
            val hText = Paint().apply { color = Color.WHITE; textSize = 11f; typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD); isAntiAlias = true }

            val cols = listOf(43f, 123f, 203f, 293f, 373f, 453f, 513f)
            val labels = listOf("Generic Name", "Brand", "Frequency", "Dosage", "Duration", "Route", "Schedule")
            labels.forEachIndexed { i, label -> c.drawText(label, cols[i], currentY + 12f, hText) }
            return currentY + 25f
        }

        y = drawMedHeader(canvas, y)

        record.medicationOrders?.forEach { med ->
            val rowHeight = 35f // 20f text + 15f spacing
            if (y + rowHeight > PAGE_HEIGHT - BOTTOM_MARGIN) {
                pdfDocument.finishPage(page)
                currentPageNumber++
                page = pdfDocument.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, currentPageNumber).create())
                canvas = page.canvas
                y = TOP_MARGIN
                y = drawMedHeader(canvas, y) // Redraw header on new page
            }

            // Draw Row Data
            canvas.drawText(med.genericName ?: "—", 43f, y + 15f, valuePaint)
            canvas.drawText("—", 123f, y + 15f, valuePaint)
            canvas.drawText("—", 203f, y + 15f, valuePaint)
            canvas.drawText(med.strength ?: "—", 293f, y + 15f, valuePaint)
            canvas.drawText(med.duration ?: "—", 373f, y + 15f, valuePaint)
            canvas.drawText("—", 453f, y + 15f, valuePaint)
            canvas.drawText("—", 513f, y + 15f, valuePaint)

            y += 20f
            canvas.drawLine(40f, y + 5f, PAGE_WIDTH - 40f, y + 5f, dividerPaint)
            y += 15f
        }

        return Triple(page, canvas, y)
    }

    private fun drawInvestigationOrders(
        pdfDocument: PdfDocument,
        currentPage: PdfDocument.Page,
        currentCanvas: Canvas,
        startY: Float,
        record: PrescriptionRecord,
        pageNo: Int
    ): Triple<PdfDocument.Page, Canvas, Float> {
        var page = currentPage
        var canvas = currentCanvas
        var y = startY
        var currentPageNumber = pageNo

        if (y + 60f > PAGE_HEIGHT - BOTTOM_MARGIN) {
            pdfDocument.finishPage(page)
            currentPageNumber++
            page = pdfDocument.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, currentPageNumber).create())
            canvas = page.canvas
            y = TOP_MARGIN
        }

        canvas.drawText("INVESTIGATION ORDERS", 40f, y, sectionTitlePaint)
        y += 25f

        if (record.investigationOrders.isNullOrEmpty()) {
            canvas.drawText("No investigation orders", 40f, y, valuePaint)
            return Triple(page, canvas, y + 30f)
        }

        fun drawInvHeader(c: Canvas, currentY: Float): Float {
            val headerBgPaint = Paint().apply { color = Color.parseColor("#4ADE80"); style = Paint.Style.FILL; isAntiAlias = true }
            c.drawRect(40f, currentY - 5f, PAGE_WIDTH - 40f, currentY + 20f, headerBgPaint)
            val hText = Paint().apply { color = Color.WHITE; textSize = 13f; typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD); isAntiAlias = true }
            c.drawText("Test Name", 45f, currentY + 12f, hText)
            c.drawText("Reason", 245f, currentY + 12f, hText)
            c.drawText("Status", 445f, currentY + 12f, hText)
            c.drawText("Date", 505f, currentY + 12f, hText)
            return currentY + 25f
        }

        y = drawInvHeader(canvas, y)

        record.investigationOrders?.forEach { order ->
            val reasonWrapped = wrapText(order.reason ?: "—", 180f, valuePaint)
            val lineCount = reasonWrapped.split("\n").size
            val rowHeight = (lineCount * 16f) + 25f // text height + padding

            if (y + rowHeight > PAGE_HEIGHT - BOTTOM_MARGIN) {
                pdfDocument.finishPage(page)
                currentPageNumber++
                page = pdfDocument.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, currentPageNumber).create())
                canvas = page.canvas
                y = TOP_MARGIN
                y = drawInvHeader(canvas, y)
            }

            canvas.drawText(order.testName ?: "N/A", 45f, y + 12f, valuePaint)

            var reasonY = y + 12f
            reasonWrapped.split("\n").forEach { line ->
                canvas.drawText(line, 245f, reasonY, valuePaint)
                reasonY += 16f
            }

            canvas.drawText("—", 445f, y + 12f, valuePaint)
            canvas.drawText("—", 505f, y + 12f, valuePaint)

            y = maxOf(y + 20f, reasonY)
            canvas.drawLine(40f, y, PAGE_WIDTH - 40f, y, dividerPaint)
            y += 15f
        }

        return Triple(page, canvas, y)
    }

    // Helper method to wrap text
    private fun wrapText(text: String, maxWidth: Float, paint: Paint): String {
        if (paint.measureText(text) <= maxWidth) {
            return text
        }

        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var currentLine = ""

        words.forEach { word ->
            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            if (paint.measureText(testLine) <= maxWidth) {
                currentLine = testLine
            } else {
                if (currentLine.isNotEmpty()) {
                    lines.add(currentLine)
                }
                currentLine = word
            }
        }

        if (currentLine.isNotEmpty()) {
            lines.add(currentLine)
        }

        return lines.joinToString("\n")
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