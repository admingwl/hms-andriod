package com.example.happydocx.Utils

import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher

/**
 * Sealed class representing every possible state of a download action.
 * Keeps logic clean and observable.
 */
sealed class DownloadStatus {
    object Idle : DownloadStatus()
    data class Started(val downloadId: Long) : DownloadStatus()
    data class Error(val message: String) : DownloadStatus()
}


fun handleDownloadWithPermission(
    context: Context,
    url: String?,
    fileName: String?,
    permissionLauncher: ManagedActivityResultLauncher<String, Boolean>,
    onStatusChange: (DownloadStatus) -> Unit
) {
    if (url.isNullOrBlank()) {
        onStatusChange(DownloadStatus.Error("Invalid file URL"))
        return
    }

    if (PermissionHandler.hasStoragePermission(context)) {
        // Permission already granted — start download
        startDownload(
            context = context,
            url = url,
            fileName = fileName,
            onStatusChange = onStatusChange
        )
    } else {
        // Request permission (only on Android < 10)
        val permission = PermissionHandler.requiredPermission()
        if (permission != null) {
            permissionLauncher.launch(permission)
        }
    }
}

/**
 * Triggers the actual file download via FileDownloadManager.
 */
fun startDownload(
    context: Context,
    url: String?,
    fileName: String?,
    onStatusChange: (DownloadStatus) -> Unit
) {
    if (url.isNullOrBlank()) {
        onStatusChange(DownloadStatus.Error("Download URL is missing"))
        return
    }

    val resolvedName = fileName
        ?.takeIf { it.isNotBlank() }
        ?.let { name ->
            // Auto-append extension from URL if not present
            val urlExtension = url.substringAfterLast(".", "")
                .substringBefore("?")  // strip query params
                .take(5)               // cap extension length
            if (name.contains(".")) name else "$name.$urlExtension"
        } ?: "document_${System.currentTimeMillis()}"

    val downloadId = FileDownloadManager.downloadFile(
        context = context,
        url = url,
        fileName = resolvedName
    )

    if (downloadId != -1L) {
        onStatusChange(DownloadStatus.Started(downloadId))
    } else {
        onStatusChange(DownloadStatus.Error("Failed to start download"))
    }
}