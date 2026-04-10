package com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.happydocx.R
import com.example.happydocx.Utils.DateUtils
import com.example.happydocx.Utils.DownloadStatus
import com.example.happydocx.Utils.handleDownloadWithPermission
import com.example.happydocx.Utils.startDownload
import com.example.happydocx.ui.ViewModels.StartConsulting.MedicalDocumentListUiState
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.UploadPatientDocumentUIState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AllDocumentsScreen(
    modifier: Modifier = Modifier,
    token: String,
    patientId: String,
    appointmentId: String,
    startConsultingViewModel: StartConsultingViewModel,
    navController: NavController
) {
    val allDocumentListState =
        startConsultingViewModel._medicalDocumentRecords.collectAsStateWithLifecycle().value
    LaunchedEffect(token) {
        startConsultingViewModel.getAllMedicalRecordsViewModel(
            token = token,
            patientId = patientId
        )
    }
    when (val state = allDocumentListState) {
        is MedicalDocumentListUiState.Idle -> {}
        is MedicalDocumentListUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is MedicalDocumentListUiState.Success -> {
            val data = state.data
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color(0xffFAFAFA))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xffFAFAFA))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Medical Documents",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Button(
                        onClick = { navController.navigate("submitDocumentScreen/$token/$patientId/$appointmentId") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xff1D4ED8),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp),
                    ) {
                        Text(
                            "Upload",
                            color = Color.White
                        )
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(6.dp)
                ) {
                    items(data) { it ->
                        DocumentItem(
                            testName = it.documentName,
                            documentType = it.documentType,
                            doctorName = "${it.uploadedBy?.salutation} ${it.uploadedBy?.first_name} ${it.uploadedBy?.last_name}",
                            imageUrl = it.signedUrl,
                            navController = navController,
                            date = it.reportDate
                        )
                    }
                }
            }
        }

        is MedicalDocumentListUiState.Error -> {}
        else -> {}
    }

}

@Suppress("EffectKeys")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadDocumentScreen(
    modifier: Modifier = Modifier,
    onClose: () -> Unit = {},
    token: String,
    appointmentId: String,
    patientId: String,
    startConsultingViewModel: StartConsultingViewModel
) {

    var datePickerState by rememberSaveable { mutableStateOf(false) }
    val datePickerStateRemember = rememberDatePickerState()
    var documentTypeExpandState by rememberSaveable { mutableStateOf(false) }
    val documentUploadUiState =
        startConsultingViewModel._uploadDocumentState.collectAsStateWithLifecycle().value
    val uploadState =
        startConsultingViewModel._uploadDocumentNetworkState.collectAsStateWithLifecycle().value
    val documentTypeList = listOf<String>(
        "Lab Report",
        "Prescription",
        "Imaging(X-Ray/MRI)",
        "Insurance",
        "Discharge Summary",
        "Other"
    )
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    // add file picker
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            val fileName = context.contentResolver.query(uri, null, null, null, null)
                ?.use { cursor ->
                    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    cursor.moveToFirst()
                    cursor.getString(nameIndex)
                } ?: "attachment"
            startConsultingViewModel.onAttachmentChange(uri, fileName)
        }
    }
    when (uploadState) {
        is UploadPatientDocumentUIState.Loading -> {
            CircularProgressIndicator()
        }

        is UploadPatientDocumentUIState.Success -> {
            // show toast or navigate back
            Toast.makeText(context, uploadState.data.message, Toast.LENGTH_SHORT).show()
        }

        is UploadPatientDocumentUIState.Error -> {
            Toast.makeText(context, uploadState.message, Toast.LENGTH_SHORT).show()
        }

        else -> {}
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Upload Patient Document", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff2563EB)
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "arrow back",
                            tint = Color.White,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "arrow back",
                            tint = Color.White,
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffFBFCFD))
                .padding(paddingValues)
                .padding(12.dp)
        ) {

            LabelText("Document Name")
            OutlinedTextField(
                value = documentUploadUiState.documentName,
                onValueChange = { startConsultingViewModel.onDocumentNameChanged(it) },
                placeholder = {
                    Text(
                        "e.g., CBC Blood Test",
                        color = Color(0xFF94A3B8)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = customTextFieldColors()
            )
            Spacer(modifier = Modifier.height(16.dp))

            LabelText("Document Type")
            ExposedDropdownMenuBox(
                expanded = documentTypeExpandState,
                onExpandedChange = { documentTypeExpandState = !documentTypeExpandState },
            ) {
                OutlinedTextField(
                    value = documentUploadUiState.documentType,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Select Type", color = Color(0xFF94A3B8)) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffF8FAFC),
                        unfocusedContainerColor = Color(0xffF8FAFC),
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                )
                ExposedDropdownMenu(
                    expanded = documentTypeExpandState,
                    onDismissRequest = { documentTypeExpandState = !documentTypeExpandState },
                    containerColor = Color(0xffF8FAFC),
                    matchTextFieldWidth = true,
                ) {
                    documentTypeList.forEach { it ->
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                startConsultingViewModel.onDocumentTypeChanged(it)
                                documentTypeExpandState = false
                            }
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(16.dp))

            // --- Report Date ---
            LabelText("Report Date")
            OutlinedTextField(
                value = documentUploadUiState.reportDate,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("mm/dd/yyyy", color = Color(0xFF94A3B8)) },
                trailingIcon = {
                    IconButton(
                        onClick = { datePickerState = !datePickerState }
                    ) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xffF8FAFC),
                    unfocusedContainerColor = Color(0xffF8FAFC),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (datePickerState) {
                DatePickerDialog(
                    shape = RoundedCornerShape(30.dp),
                    colors = DatePickerDefaults.colors(
                        // add color to date picker dialog
                        containerColor = Color(0xffebedfc)
                    ),
                    onDismissRequest = { datePickerState = false },
                    confirmButton = {
                        TextButton(onClick = {
                            // Convert milliseconds to formatted date string
                            val selectedMillis = datePickerStateRemember.selectedDateMillis
                            if (selectedMillis != null) {
                                val formattedDate =
                                    SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
                                        .format(Date(selectedMillis))
                                startConsultingViewModel.onDocumentDateChanged(formattedDate) //  Save to ViewModel
                            }
                            datePickerState = false
                        }) { Text("OK", color = Color.Black) }
                    },
                    dismissButton = {
                        TextButton(onClick = { datePickerState = false }) {
                            Text("Cancel", color = Color.Black)
                        }
                    }
                ) {
                    DatePicker(
                        state = datePickerStateRemember,
                        colors = DatePickerDefaults.colors(
                            containerColor = Color(0xffebedfc),
                            dayContentColor = Color.Black,
                            titleContentColor = Color.Black,
                            weekdayContentColor = Color.Black,
                            headlineContentColor = Color.Black,
                            navigationContentColor = Color.Black,
                            subheadContentColor = Color.Black,
                            dateTextFieldColors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            )
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // --- Attachment Section ---
            LabelText("Attachment")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .dashedBorder(1.dp, Color(0xFFCBD5E1), 12.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .clickable {
                        filePickerLauncher.launch(
                            arrayOf("application/pdf", "image/jpeg", "image/png")
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (documentUploadUiState.attachmentURI != null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFDCFCE7), // green tint
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle, // or any file icon
                                    contentDescription = null,
                                    tint = Color(0xFF16A34A),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = documentUploadUiState.attachmentName, //  file name shown here
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = Color(0xFF475569),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tap to change file",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF1F5F9),
                            modifier = Modifier.size(48.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    painter = painterResource(R.drawable.baseline_cloud_24),
                                    contentDescription = null,
                                    tint = Color(0xFF64748B),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Click to upload document",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color(0xFF475569)
                        )
                        Text(
                            text = "PDF, JPG, PNG (Max 5MB)",
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- Buttons ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onClose,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                    modifier = Modifier
                        .height(44.dp)
                        .padding(horizontal = 8.dp)
                ) {
                    Text("Cancel", color = Color(0xFF475569))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        scope.launch {
                            startConsultingViewModel.UploadPatientDocumentClicked(
                                context = context,
                                token = token,
                                appointmentId = appointmentId,
                                patientId = patientId
                            )
                        }
                    },
                    enabled = uploadState !is UploadPatientDocumentUIState.Loading,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB)),
                    modifier = Modifier.height(44.dp)
                ) {
                    if (uploadState is UploadPatientDocumentUIState.Loading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.baseline_cloud_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Upload Document", color = Color.White)
                    }
                }
            }
        }
    }


}

@Composable
fun LabelText(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF475569),
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = Color(0xFFCBD5E1),
    focusedBorderColor = Color(0xFF2563EB),
    unfocusedContainerColor = Color.White,
    focusedContainerColor = Color.White,
    focusedTextColor = Color.Black,
    unfocusedTextColor = Color.Black
)

// Extension for the dashed border
fun Modifier.dashedBorder(width: Dp, color: Color, cornerRadius: Dp) = drawBehind {
    val stroke = Stroke(
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )
    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = CornerRadius(cornerRadius.toPx())
    )
}


@Composable
fun DocumentItem(
    testName: String?,
    doctorName: String?,
    documentType: String?,
    imageUrl: String?,
    navController: NavController,
    date: String?,
) {

    var showDownloadDialog by remember { mutableStateOf(false) }
    var downloadStatus by remember { mutableStateOf<DownloadStatus>(DownloadStatus.Idle) }
    val context = LocalContext.current

    // Permission launcher (only triggers on Android < 10)
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted — proceed with download
            startDownload(
                context = context,
                url = imageUrl,
                fileName = testName,
                onStatusChange = { downloadStatus = it }
            )
        } else {
            downloadStatus = DownloadStatus.Error("Storage permission denied")
        }
    }

    // Show a brief toast when status changes
    LaunchedEffect(downloadStatus) {
        when (downloadStatus) {
            is DownloadStatus.Started -> {
                Toast.makeText(
                    context,
                    "Download started. Check notifications.",
                    Toast.LENGTH_SHORT
                ).show()
                // Reset after showing
                downloadStatus = DownloadStatus.Idle
            }
            is DownloadStatus.Error -> {
                Toast.makeText(
                    context,
                    (downloadStatus as DownloadStatus.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
                downloadStatus = DownloadStatus.Idle
            }
            else -> {}
        }
    }

    // ---- Download Confirmation Dialog ----
    if (showDownloadDialog) {
        DownloadConfirmationDialog(
            fileName = testName,
            onDismiss = { showDownloadDialog = false },
            onConfirm = {
                showDownloadDialog = false
                handleDownloadWithPermission(
                    context = context,
                    url = imageUrl,
                    fileName = testName,
                    permissionLauncher = permissionLauncher,
                    onStatusChange = { downloadStatus = it }
                )
            }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.document),
                    contentDescription = "Document Icon",
                    tint = Color.Blue,
                    modifier = Modifier.size(30.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (testName != null) {
                        Text(
                            text = testName,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Blue,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFEFF6FF), // light blue background,
                    ) {
                        if (documentType != null) {
                            Text(
                                text = documentType,
                                color = Color.Gray,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(4.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                if (doctorName != null) {
                    Text(
                        text = doctorName,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = DateUtils.gettingOnlyDate(date),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {
                    // navigate to show image screen.
                    val encodedUrl = Uri.encode(imageUrl)
                    navController.navigate("documentImage/$encodedUrl")

                }) {
                    Icon(
                        painter = painterResource(R.drawable.eye),
                        contentDescription = "View Document",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    onClick = {showDownloadDialog = true},
                    modifier = Modifier.background(color = Color.White, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.download),
                        contentDescription = "Download Document",
                        tint = Color.Blue,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }

        }
    }
}

@Composable
fun ShowCompleteDocumentImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
) {

    // mutable state for holding the offset and scale values.
    var scale by remember {
        mutableStateOf(1f)
    }
    var offSetX by remember {
        mutableStateOf(0f)
    }
    var offSetY by remember {
        mutableStateOf(0f)
    }

    val minScale = 1f
    val maxScale = 4f

    // remember initial offSet
    var initialOffSet by remember {
        mutableStateOf(Offset(0f, 0f))
    }

    // Coefficient for slowing down movement
    val slowMovement = 0.5f
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    // update the scale with the zoom
                    val newScale = scale * zoom
                    scale = newScale.coerceIn(minScale, maxScale)
                    // Calculate new offsets based on zoom and pan
                    val centerX = size.width / 2
                    val centerY = size.height / 2
                    val offsetXChange = (centerX - offSetX) * (newScale / scale - 1)
                    val offsetYChange = (centerY - offSetY) * (newScale / scale - 1)

                    // Calculate min and max offsets
                    val maxOffsetX = (size.width / 2) * (scale - 1)
                    val minOffsetX = -maxOffsetX
                    val maxOffsetY = (size.height / 2) * (scale - 1)
                    val minOffsetY = -maxOffsetY

                    // Update offsets while ensuring they stay within bounds
                    if (scale * zoom <= maxScale) {
                        offSetX = (offSetX + pan.x * scale * slowMovement + offsetXChange)
                            .coerceIn(minOffsetX, maxOffsetX)
                        offSetY = (offSetY + pan.y * scale * slowMovement + offsetYChange)
                            .coerceIn(minOffsetY, maxOffsetY)
                    }
                    // Store initial offset on pan
                    if (pan != Offset(0f, 0f) && initialOffSet == Offset(0f, 0f)) {
                        initialOffSet = Offset(offSetX, offSetY)
                    }
                }
            }
            .pointerInput(
                Unit
            ) {
                detectTapGestures(
                    onDoubleTap = {
                        // Reset scale and offset on double tap
                        if (scale != 1f) {
                            scale = 1f
                            offSetX = initialOffSet.x
                            offSetY = initialOffSet.y
                        } else {
                            scale = 2f
                        }
                    }
                )
            }
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offSetX
                translationY = offSetY
            },
        //  contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Document Image",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop,
        )
    }
}

// Confirmation Dialog for download file
@Composable
fun DownloadConfirmationDialog(
    fileName: String?,
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFEFF6FF),
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(R.drawable.download),
                            contentDescription = "Download Icon",
                            tint = Color(0xFF2563EB),
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // --- Title ---
                Text(
                    text = "Download Document",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF1E293B)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // --- Subtitle ---
                Text(
                    text = "Do you want to download",
                    fontSize = 14.sp,
                    color = Color(0xFF64748B),
                    textAlign = TextAlign.Center
                )

                // --- File Name ---
                Text(
                    text = "File Name",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF2563EB),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // --- Action Buttons ---
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Cancel
                    OutlinedButton(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, Color(0xFFCBD5E1)
                        )
                    ) {
                        Text("Cancel", color = Color(0xFF475569), fontWeight = FontWeight.Medium)
                    }

                    // Download
                    OutlinedButton(
                        onClick = {onConfirm()},
                        modifier = Modifier
                            .weight(1f)
                            .height(44.dp),
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp, Color(0xFFCBD5E1)
                        )
                    ) {
                        Text("Download", color = Color(0xFF475569), fontWeight = FontWeight.Medium)
                    }
                }
                }
            }
        }
    }



