package com.example.happydocx.ui.Screens.SignUpForms

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.happydocx.R
import com.example.happydocx.ui.ViewModels.FormViewModelFactory
import com.example.happydocx.ui.ViewModels.formViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form_Two_Screen(
    doctorId: String,
    navController: NavController,
    viewModel: formViewModel
) {


    val doctorProfileState by viewModel._doctorProfileState.collectAsStateWithLifecycle()
    val gradient_colors = Brush.linearGradient(
        listOf(
            Color(0xff586AE5), Color(0xff717FE8),
            Color(0xff7785E9)
        )
    )
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current
    // getting form state
    val formState by viewModel._formState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Document Upload",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            "Upload required documents to complete your profile",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent // keep the same gradient
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(gradient_colors),
                scrollBehavior = scrollBehaviour
            )

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = innerPadding)
        ) {
            // section 4
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomNumberDisplay(4)
                Spacer(Modifier.width(8.dp))
                Text(
                    "Required Documents",
                    fontSize = 18.sp,
                    color = Color(0xff4f61e3),
                    fontWeight = FontWeight.Bold
                )
            }
            // adding the profile photo
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(
                    "Profile Photo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text("*", color = Color.Red)
            }
            // know i add the add box section
            MyDashedBox(
                fileType = "image",
                selectedUri = formState.profilePhotoUri,
                selectedName = formState.profilePhotoName,
                onFileSelected = { uri, name ->
                    viewModel.updateProfilePhoto(uri, name)
                })


            // adding Signature
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(
                    "Signature",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text("*", color = Color.Red)
            }
            // know i add the add box section
            MyDashedBox(
                "image",
                selectedUri = formState.signatureUri,
                selectedName = formState.signatureName,
                onFileSelected = { uri, name ->
                    viewModel.updateSignature(uri, name)
                })

            // adding the Doctor ID Proof
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(
                    "Doctor ID Proof",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text("*", color = Color.Red)
            }
            // know i add the add box section
            MyDashedBox(
                fileType = "document",
                selectedUri = formState.doctorIdProofUri,
                selectedName = formState.doctorIdProofName,
                onFileSelected = { uri, name ->
                    viewModel.updateDoctorIdProof(uri, name)
                })

            // adding the Doctor License
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(
                    "Doctor License",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text("*", color = Color.Red)
            }
            // know i add the add box section
            MyDashedBox(
                "document",
                selectedUri = formState.doctorLicenseUri,
                selectedName = formState.doctorLicenseName,
                onFileSelected = { uri, name ->
                    viewModel.updateDoctorLicense(uri, name)
                })

            // adding the MBBS Certificate
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(
                    "MBBS Certificate",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text("*", color = Color.Red)
            }
            // know i add the add box section
            MyDashedBox(
                "document",
                selectedUri = formState.mbbsCertificateUri,
                selectedName = formState.mbbsCertificateName,
                onFileSelected = { uri, name ->
                    viewModel.updateMbbsCertificate(uri, name)
                })

            // adding the Experience Certificate
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                Text(
                    "Experience Certificate",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text("*", color = Color.Red)
            }
            // know i add the add box section
            MyDashedBox(
                "document",
                selectedUri = formState.experienceCertificateUri,
                selectedName = formState.experienceCertificateName,
                onFileSelected = { uri, name ->
                    viewModel.updateExperienceCertificate(uri, name)
                })


            // Final Registration  Button
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                FilledTonalButton(
                    onClick = {
                        // Validate required fields first
                        if (formState.profilePhotoUri == null ||
                            formState.signatureUri == null ||
                            formState.doctorIdProofUri == null ||
                            formState.doctorLicenseUri == null ||
                            formState.mbbsCertificateUri == null ||
                            formState.experienceCertificateUri == null
                        ) {
                            Toast.makeText(
                                context,
                                "Please upload all required documents",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            // Call the API with actual doctorId
                            viewModel.CompleteRegistration(doctorId = doctorId)
                        }
                    },
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xff16a34a),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = doctorProfileState !is formViewModel.DoctorProfileUiState.Loading
                ) {
                    if (doctorProfileState is formViewModel.DoctorProfileUiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    Text(
                        if (doctorProfileState is formViewModel.DoctorProfileUiState.Loading) "Uploading..." else "Complete Registration",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 13.sp
                    )
                }
            }
            when (val state = doctorProfileState) {
                is formViewModel.DoctorProfileUiState.Success -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            "Registration Completed Successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                        // Navigate to Login screen and clear back stack
                        navController.navigate("Login") {
                            popUpTo(0) { inclusive = true } // Clear entire back stack
                            launchSingleTop = true
                        }
                    }
                }

                is formViewModel.DoctorProfileUiState.Error -> {
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            "Error: ${state.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                else -> { /* Idle or Loading - no action needed */
                }
            }
        }
    }
}

@Composable
fun MyDashedBox(
    fileType: String, // "image" or "pdf"
    selectedUri: Uri?,
    selectedName: String?,
    onFileSelected: (Uri?, String?) -> Unit,
    maxSizeBytes: Long = 2 * 1024 * 1024 // 2MB
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
            .padding(horizontal = 8.dp)
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
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (fileType == "image") "Upload Image" else "Upload PDF",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = if (fileType == "image") "PNG, JPG up to 2MB" else "PDF up to 2MB",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}
