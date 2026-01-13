package com.example.happydocx.PdfGeneration

import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.example.happydocx.MainActivity
import java.io.File

@RequiresApi(Build.VERSION_CODES.Q)
fun downloadPdfToDownloadsFolder(
    context: MainActivity,
    pdfFile: File){

  val contentValues = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME,"prescription_${System.currentTimeMillis()}.pdf")
        put(MediaStore.Downloads.MIME_TYPE,"application/pdf")
        put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    val uri = context.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI,contentValues)

    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { output->
            pdfFile.inputStream().use { input->
                input.copyTo(output)
            }
        }
    }

}