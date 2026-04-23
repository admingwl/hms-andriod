package com.example.happydocx.ui.Navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.ui.Screens.Authentication.ForgotPasswordScreen
import com.example.happydocx.ui.Screens.Authentication.LoginPage
import com.example.happydocx.ui.Screens.Authentication.SignInWithPhone.EnterOtpScreen
import com.example.happydocx.ui.Screens.Authentication.SignInWithPhone.EnterPhoneNumberScreen
import com.example.happydocx.ui.Screens.Authentication.SignUpPage
import com.example.happydocx.ui.Screens.Authentication.UpdatePasswordScreen
import com.example.happydocx.ui.Screens.CreatePatient.AddNewPatientScreen
import com.example.happydocx.ui.Screens.CreatePatient.PatientListScreen
import com.example.happydocx.ui.Screens.CreatePatient.ScheduleAppointmentScreen
import com.example.happydocx.ui.Screens.DoctorAppointments.DoctorAppointmentScreen
import com.example.happydocx.ui.Screens.QueueScreen
import com.example.happydocx.ui.Screens.SignUpForms.Form_One_Screen
import com.example.happydocx.ui.Screens.SignUpForms.Form_Two_Screen
import com.example.happydocx.ui.Screens.StartConsulting.AddSymptomScreen
import com.example.happydocx.ui.Screens.StartConsulting.InvoicesScreen
import com.example.happydocx.ui.Screens.StartConsulting.StartConsultingScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.AddNewLabResultScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.AddNewMedicationScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.AddNewOrderTestScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.AddNewVitalSignsScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.BasicInfoOfPatient
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.EditPatientInfoScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.ManualEntryScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.ShowCompleteDocumentImage
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.UploadDocumentScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.VisitHistoryScreen
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModel
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModelFactory
import com.example.happydocx.ui.ViewModels.FormViewModelFactory
import com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel.EnterOtpViewModel
import com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel.EnterOtpViewModelFactory
import com.example.happydocx.ui.ViewModels.OtpBasedSignedInViewModel.EnterPhoneNumberViewModel
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel
import com.example.happydocx.ui.ViewModels.PatientListViewModelFactory
import com.example.happydocx.ui.ViewModels.PatientViewModel.GetTimeSlotsForAppointmentViewModel
import com.example.happydocx.ui.ViewModels.PatientViewModel.PatientListViewModel
import com.example.happydocx.ui.ViewModels.PatientViewModel.SavePatientViewModel
import com.example.happydocx.ui.ViewModels.Queue.QueueViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.BasicInformationViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.PatientDocumentUploadViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.StartConsultingViewModel
import com.example.happydocx.ui.ViewModels.formViewModel
import java.util.Queue

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {

    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    val navController = rememberNavController()
    val userViewModel: ParticularUserSignInViewModel = viewModel()
    val BasicPatientInformationViewModel: BasicInformationViewModel = viewModel()


    // Create viewModel at navigation graph level so it survives between screens
    val sharedViewModel: formViewModel = viewModel(factory = FormViewModelFactory(context))
    val doctorAppointmentViewModel: DoctorAppointmentsViewModel =
        viewModel(factory = DoctorAppointmentsViewModelFactory(context))
    val SavePatientGeneralViewModel: SavePatientViewModel = viewModel()
    val getTimeSlotsViewModel: GetTimeSlotsForAppointmentViewModel = viewModel()
    val enterPhoneNumberViewModel: EnterPhoneNumberViewModel = viewModel()
    val enterOtpViewModel: EnterOtpViewModel =
        viewModel(factory = EnterOtpViewModelFactory(tokenManager = tokenManager))
    // update version
    val startConsultingViewModel: StartConsultingViewModel = viewModel()
    val queueViewModel: QueueViewModel = viewModel()
    val startDestination = remember {
        if (tokenManager.getToken() != null) {
            "AppointmentsScreen/${tokenManager.getToken()}"
        } else {
            "Login"
        }
    }
    NavHost(startDestination = startDestination, navController = navController) {

        composable(route = "Login") {
            LoginPage(navController = navController, userViewModel = userViewModel)
        }
        composable(route = "SignUp") {
            SignUpPage(navController = navController)
        }
        composable(
            route = "first_form/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            Form_One_Screen(
                doctorId = doctorId,
                navController = navController,
                viewModel = sharedViewModel
            )
        }
        composable(
            route = "second_form/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            Form_Two_Screen(
                doctorId = doctorId,
                navController = navController,
                viewModel = sharedViewModel
            )
        }
        composable(
            "AppointmentsScreen/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            Log.d("DEBUG_NAV", "Navigation: Token = $token")
            DoctorAppointmentScreen(
                token = token,
                navController = navController,
                viewModel = doctorAppointmentViewModel
            )
        }
        // patient list screen.
        composable(
            "patientScreen/{token}",
            arguments = listOf(navArgument("token") {
                type = NavType.StringType
                nullable = false
            }
            )) { backStackEntry ->
            val tokenOne = backStackEntry.arguments?.getString("token") ?: ""

            val patientListViewModel: PatientListViewModel = viewModel(
                factory = PatientListViewModelFactory(context)
            )
            PatientListScreen(
                viewModel = patientListViewModel,
                token = tokenOne,
                navController = navController
            )
        }
        composable(
            route = "addSymptoms/{token}/{patientId}/{appointmentId}",
            arguments = listOf(
                navArgument("token") { type = NavType.StringType },
                navArgument("patientId") { type = NavType.StringType },
                navArgument("appointmentId") { type = NavType.StringType }
            )
        )
        { backStack ->
            val token = backStack.arguments?.getString("token") ?: ""
            val patientId = backStack.arguments?.getString("patientId") ?: ""
            val appointmentId = backStack.arguments?.getString("appointmentId") ?: ""
            AddSymptomScreen(
                viewModel = BasicPatientInformationViewModel,
                navController = navController,
                token = token,
                patinetId = patientId,
                appointmentId = appointmentId
            )
        }

        composable(route = "invoiceScreen") {
            InvoicesScreen()
        }
//        composable(
//            route = "SartConsultationScreen/{patientId}/{token}/{appointmentId}",
//            arguments = listOf(
//                navArgument("patientId") { type = NavType.StringType },
//                navArgument("token") { type = NavType.StringType },
//                navArgument("appointmentId") { type = NavType.StringType }
//            )
//        ) { backStack ->
//            val patientId = backStack.arguments?.getString("patientId") ?: ""
//            val token = backStack.arguments?.getString("token") ?: ""
//            val appointmentId = backStack.arguments?.getString("appointmentId") ?: ""
//
//            StartConsultingScreen(
//                viewModel = BasicPatientInformationViewModel,
//                navController = navController,
//                patientId = patientId,
//                token = token,
//                appointmentID = appointmentId,
//                documentViewModel = documentUploadViewModel
//            )
//        }
        composable(
            "NewPage/{patientId}/{token}",
            arguments = listOf(
                navArgument("patientId") { type = NavType.StringType },
                navArgument("token") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            val token = backStackEntry.arguments?.getString("token") ?: ""

            Log.d("DEBUG_NAV", "NewPage - PatientId: $patientId, Token: ${token.take(10)}...")

        }

        composable(
            route = "AddNewPatientScreen/{token}",
            arguments = listOf(
                navArgument("token") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            AddNewPatientScreen(
                navController = navController,
                viewModel = SavePatientGeneralViewModel,
                token = token
            )
        }

        composable(
            route = "scheduleAppointmentScreen/{token}/{patientId}/{perfectPatientId}/{firstName}/{middleName}/{lastName}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
                navArgument(name = "patientId") { type = NavType.StringType },
                navArgument(name = "perfectPatientId") { type = NavType.StringType },
                navArgument(name = "firstName") { type = NavType.StringType },
                navArgument(name = "middleName") { type = NavType.StringType },
                navArgument(name = "lastName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            val perfectPatientId = backStackEntry.arguments?.getString("perfectPatientId") ?: ""
            val firstName = backStackEntry.arguments?.getString("firstName") ?: ""
            val middleName = backStackEntry.arguments?.getString("middleName") ?: ""
            val lastName = backStackEntry.arguments?.getString("lastName") ?: ""

            ScheduleAppointmentScreen(
                navController = navController,
                token = token,
                viewModel = getTimeSlotsViewModel,
                patientId = patientId,
                perfectPatientId = perfectPatientId,
                patientFirstName = firstName,
                patientMiddleName = middleName,
                patientLastName = lastName
            )
        }

        // forgot password screen enter email.
        composable(route = "forgotPasswordScreen") {
            ForgotPasswordScreen(navController = navController)
        }

        composable(route = "updatePasswordScreen") {
            UpdatePasswordScreen(navController = navController)
        }
        //--------------------new Consulting Screen-----------------------------------------------//
        composable(
            route = "StartConsultation/{token}/{appointmentId}/{patientId}/{doctorId}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
                navArgument(name = "appointmentId") { type = NavType.StringType },
                navArgument(name = "patientId") { type = NavType.StringType },
                navArgument(name = "doctorId") { type = NavType.StringType }
            )) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            BasicInfoOfPatient(
                startConsultingViewModel = startConsultingViewModel,
                token = token,
                appointmentId = appointmentId,
                navController = navController,
                patientId = patientId,
                doctorId = doctorId
            )
        }

        composable(
            route = "addNewVitalSigns/{token}/{appointmentId}/{patientId}/{doctorId}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
                navArgument(name = "appointmentId") { type = NavType.StringType },
                navArgument(name = "patientId") { type = NavType.StringType },
                navArgument(name = "doctorId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            AddNewVitalSignsScreen(
                navController = navController,
                startConsultingViewModel = startConsultingViewModel,
                patientId = patientId,
                doctorId = doctorId,
                token = token,
                appointmentId = appointmentId
            )
        }

        composable(
            "addMedication/{appointmentId}/{token}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
                navArgument(name = "appointmentId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            AddNewMedicationScreen(
                navController = navController,
                startConsultingViewModel = startConsultingViewModel,
                appointmentId = appointmentId,
                token = token
            )
        }

        composable(
            "addResultScreen/{token}/{patientId}/{doctorId}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
                navArgument(name = "patientId") { type = NavType.StringType },
                navArgument(name = "doctorId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            AddNewLabResultScreen(
                navController = navController,
                startConsultingViewModel = startConsultingViewModel,
                token = token,
                patientId = patientId,
                doctorId = doctorId
            )
        }
        composable(route = "addNewOrderTestScreen") {
            AddNewOrderTestScreen(navController = navController)
        }

        composable(
            route = "submitDocumentScreen/{token}/{patientId}/{appointmentId}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
                navArgument(name = "patientId") { type = NavType.StringType },
                navArgument(name = "appointmentId") { type = NavType.StringType }
            )) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val patientId = backStackEntry.arguments?.getString("patientId") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            UploadDocumentScreen(
                startConsultingViewModel = startConsultingViewModel,
                token = token,
                patientId = patientId,
                appointmentId = appointmentId
            )
        }

        composable(
            route = "editAppointmentData/{token}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            EditPatientInfoScreen(
                navController = navController,
                startConsultingViewModel = startConsultingViewModel,
                token = token
            )
        }

        composable(route = "enterNumberScreen") {
            EnterPhoneNumberScreen(
                navController = navController,
                enterPhoneNumberViewModel = enterPhoneNumberViewModel
            )
        }

        composable(route = "enterOtpScreen") {
            EnterOtpScreen(
                navController = navController,
                enterOtpViewModel = enterOtpViewModel,
                enterPhoneNumberViewModel = enterPhoneNumberViewModel
            )
        }

        composable(
            route = "visitHistoryScreen/{token}/{appointmentId}",
            arguments = listOf(
                navArgument(name = "token") { type = NavType.StringType },
                navArgument(name = "appointmentId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            val appointmentId = backStackEntry.arguments?.getString("appointmentId") ?: ""
            VisitHistoryScreen(
                token = token,
                appointmentId = appointmentId,
                startConsultingViewModel = startConsultingViewModel
            )
        }

        // show document Image Screen
        composable("documentImage/{imageUrl}", arguments = listOf(
            navArgument(name = "imageUrl") { type = NavType.StringType },
        )){backStackEntry->
            val imageUrl = backStackEntry.arguments?.getString("imageUrl") ?: ""
            ShowCompleteDocumentImage(
                imageUrl = imageUrl
            )
        }

        // queue screen
        composable("queueScreen/{token}", arguments = listOf(
            navArgument(name = "token") { type = NavType.StringType },
        )){backStackEntry->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            QueueScreen(
                token = token,
                queueViewModel = queueViewModel
            )
        }
        //--------------------new Consulting Screen-----------------------------------------------//

    }
}


// custom top bar in Appointment screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    SearchBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp),
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onSearch = {},
                expanded = false,
                onExpandedChange = {},
                placeholder = {
                    Text(
                        text = "Search Appointments",
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                },
                colors = SearchBarDefaults.inputFieldColors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                )
            )
        },
        expanded = false,
        onExpandedChange = {},
        shape = RoundedCornerShape(8.dp),
        colors = SearchBarDefaults.colors(
            containerColor = Color(0xFFF3F4F6),
        ),
        tonalElevation = 0.dp
    ) {}
}

