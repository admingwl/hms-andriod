package com.example.happydocx.Utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap

object FileDownloadManager {
    // using download manager.
    fun downloadFile(
        context: Context,  // application or activity context.
        url:String, // remote url of the file you want to download.
        fileName:String, // desired file name.
        subPath:String = "HappyDocx" // optional subfolder inside the download.
    ): Long{
          return try{
              val sanitizedFileName  = sanitizeFileName(fileName)
              val mimeType = getMimeType(url) ?: "application/octet-stream"

              val request = DownloadManager.Request(Uri.parse(url)).apply {
                  setTitle(sanitizedFileName )
                  setDescription("Downloading medical document...")
                  setMimeType(mimeType)

                  // Save to Downloads/HappyDocx/<fileName>
                  setDestinationInExternalPublicDir(
                      Environment.DIRECTORY_DOWNLOADS,
                      "$subPath/$sanitizedFileName"
                  )

                  // Make the file visible in Downloads app after completion
                  setNotificationVisibility(
                      DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED
                  )
                  // Allow download over WiFi and mobile data
                  setAllowedNetworkTypes(
                      DownloadManager.Request.NETWORK_WIFI or
                              DownloadManager.Request.NETWORK_MOBILE
                  )
              }

              val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
              downloadManager.enqueue(request)
          }catch (e:Exception){
              e.printStackTrace()
              -1L
          }
    }

    /**
     * Removes invalid characters from file names.
     */
    private fun sanitizeFileName(name: String): String {
        return name
            .replace(Regex("[\\\\/:*?\"<>|]"), "_")
            .trim()
            .ifEmpty { "document_${System.currentTimeMillis()}" }
    }

    /**
     * Extracts MIME type from file URL extension.
     */
    private fun getMimeType(url: String): String? {
        val extension = MimeTypeMap.getFileExtensionFromUrl(url)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
    }
}

