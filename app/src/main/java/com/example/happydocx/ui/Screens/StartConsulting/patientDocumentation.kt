package com.example.happydocx.ui.Screens.StartConsulting

import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.StartConsulting.PatientDocumentUploadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDocumentsUploading(
    modifier: Modifier = Modifier,
    viewModel: PatientDocumentUploadViewModel
) {

    val _state = viewModel._state.collectAsStateWithLifecycle().value

    val testResult = listOf(
        "Positive",
        "Negative",
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        // dash board
        MyDashedBoxForDocuments("Image")
        // text field
        TextField(
            value = _state.documentDiscription,
            onValueChange = {viewModel.onEnterDocumentDiscriptionChange(it)},
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xfff9fafb))
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Enter Document Description", color = Color(0xffa3aab5)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xfff9fafb),
                unfocusedContainerColor = Color(0xfff9fafb),
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        // drop down text field
        ExposedDropdownMenuBox(
            expanded = _state.testResultExpandingState,
            modifier = modifier.padding(16.dp),
            onExpandedChange = { viewModel.onTestResultExpandStateChange(it) },
        ) {
            OutlinedTextField(
                value = _state.testResultSelected,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Select Test Result", color = Color(0xffdae0e7)) },
                trailingIcon = {

                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xfff9fafb),
                    unfocusedContainerColor = Color(0xfff9fafb),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = _state.testResultExpandingState,
                onDismissRequest = {viewModel.onTestResultExpandStateChange(false)},
                containerColor = Color(0xffebedfc),
                matchTextFieldWidth = true,
                shape = RoundedCornerShape(30.dp)
            ) {
                testResult.forEach { it ->
                    DropdownMenuItem(
                        text = { Text(it, color = Color.Black) },
                        onClick = {
                            viewModel.onTestResultSelected(it)
                        }
                    )
                }
            }
        }
         // upload button
        FilledTonalButton(
            onClick = {
                // here i call the api for uploading the data to the server
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff2563eb)
            ),
            shape = RoundedCornerShape(0.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(paddingValues = PaddingValues(0.dp))
                .padding(16.dp)
        ) {
            Text("Upload", color = Color.White)
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(10){
                    ShowUploadedDocuments()
                }
            }
        }
    }
}

@Composable
fun MyDashedBoxForDocuments(
    fileType: String, // "image" or "pdf"
    selectedUri: Uri? = null,
    selectedName: String? = null,
    onFileSelected: (Uri?, String?) -> Unit = { _, _ -> },
    maxSizeBytes: Long = 5 * 1024 * 1024 // 5MB
) {
    val context = LocalContext.current
    var errorMessage = remember { mutableStateOf<String?>(null) }

    // Helper to get actual filename
    fun getFileName(uri: Uri): String {
        var result = "Unknown File"
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst() && nameIndex != -1) {
                result = cursor.getString(nameIndex)
            }
        }
        return result
    }

    //This helper function i create as Helper to check file size
    fun checkFileSize(uri: Uri): Boolean {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst() && sizeIndex != -1) {
                val size = cursor.getLong(sizeIndex)
                return size <= maxSizeBytes
            }
        }
        return false
    }

    val mediaPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            if (checkFileSize(it)) {
                val name = getFileName(it)
                onFileSelected(it, name)
                errorMessage.value = null
            } else {
                errorMessage.value = "File size exceeds 2MB"
            }
        }
    }

    val documentPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            if (checkFileSize(it)) {
                val name = getFileName(it)
                onFileSelected(it, name)
                errorMessage.value = null
            } else {
                errorMessage.value = "File size exceeds 2MB"
            }
        }
    }

    val stroke = Stroke(
        width = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .drawBehind {
                drawRoundRect(
                    color = if (errorMessage.value != null) Color.Red else Color.Black,
                    style = stroke
                )
            }
            .clickable {
                if (fileType == "image") {
                    mediaPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                } else {
                    documentPicker.launch("application/pdf")
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            when {
                errorMessage.value != null -> {
                    Text(
                        text = errorMessage.value!!,
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { errorMessage.value = null }) {
                        Text("Try Again")
                    }
                }

                selectedUri != null -> {
                    if (fileType == "image") {
                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context)
                                .data(selectedUri)
                                .crossfade(true)
                                .build()
                        )

                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = selectedName ?: "PDF Document",
                            fontSize = 14.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = {
                            onFileSelected(null, null)
                        }
                    ) {
                        Text("Remove", color = Color.Red)
                    }
                }

                else -> {
                    Icon(
                        painter = painterResource(R.drawable.baseline_cloud_24),
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xff2563eb)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Upload Document",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "PNG, JPG, PNG up to 5MB",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}


@Composable
fun ShowUploadedDocuments(
    modifier: Modifier = Modifier,
    date: String = "12-03-2023",
    doctor: String = "Deepak Guleria",
    result: String = "Negative Result",
    ) {
    Card(modifier = modifier.fillMaxWidth().padding(16.dp)) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color(0xfff0f5ff))
                .padding(12.dp)
        ) {
            // row one
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // first row element
                Row {
                    Icon(
                        painter = painterResource(R.drawable.pdf),
                        contentDescription = null,
                        modifier = modifier.size(25.dp),
                        tint = Color.Unspecified
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Blood Test Report",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                // second row component
                Spacer(modifier = modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.eye),
                        contentDescription = null,
                        modifier = modifier.size(20.dp),
                        tint = Color.Gray
                    )
                }
            }

            // row 2nd
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row {
                        Text("Date:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black)
                        Spacer(modifier.width(4.dp))
                        Text(date, fontSize = 16.sp,color = Color.Black)
                    }
                    Spacer(modifier.height(4.dp))
                    Row {
                        Text("Doctor:", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black)
                        Spacer(modifier.width(4.dp))
                        Text(doctor, fontSize = 16.sp,color = Color.Black)
                    }
                }
                Spacer(modifier.weight(1f))
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(R.drawable.download),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = modifier.size(20.dp)
                    )
                }
            }

            // 3 rd row
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Result:", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    Spacer(modifier.width(8.dp))

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = when (result) {
                            "Positive Result" -> {
                                Color(0xffdcfce7)
                            }

                            "Negative Result" -> {
                                Color(0xfffee2e2)
                            }

                            else -> {
                                Color(0xfffef9c3)
                            }
                        }
                    ) {
                        Text(
                            result,
                            modifier = modifier.padding(6.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (result) {
                                "Positive Result" -> {
                                    Color(0xff59aa77)
                                }

                                "Negative Result" -> {
                                    Color(0xffe65c5c)
                                }

                                else -> {
                                    Color(0xff966527)
                                }
                            }
                        )
                    }
                }
                Spacer(modifier.weight(1f))
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = modifier.size(20.dp)
                    )
                }
            }
        }
    }
}