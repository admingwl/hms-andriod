package com.example.happydocx.ui.Navigation

import android.os.Build
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.R
import com.example.happydocx.ui.Screens.DoctorAppointments.DoctorAppointmentScreen
import com.example.happydocx.ui.Screens.DoctorAppointments.gradient_colors
import com.example.happydocx.ui.Screens.LoginPage
import com.example.happydocx.ui.Screens.Patient.ParticularPatientScreen
import com.example.happydocx.ui.Screens.SignUpForms.Form_One_Screen
import com.example.happydocx.ui.Screens.SignUpForms.Form_Two_Screen
import com.example.happydocx.ui.Screens.SignUpPage
import com.example.happydocx.ui.Screens.SignUpResponse
import com.example.happydocx.ui.Screens.StartConsulting.ConsultingMainScreen
import com.example.happydocx.ui.ViewModels.DoctorAppointmentsViewModel
import com.example.happydocx.ui.ViewModels.FormViewModelFactory
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel
import com.example.happydocx.ui.ViewModels.ParticularUserSignUpViewModel
import com.example.happydocx.ui.ViewModels.StartConsulting.BasicInformationViewModel
import com.example.happydocx.ui.ViewModels.formViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {

    val context = LocalContext.current
    val navController = rememberNavController()
    val userViewModel: ParticularUserSignInViewModel = viewModel()
    val particularUserSignUpViewModel: ParticularUserSignUpViewModel = viewModel()
    val BasicPatientInformationViewModel: BasicInformationViewModel = viewModel()

    // Create viewModel at navigation graph level so it survives between screens
    val sharedViewModel: formViewModel = viewModel(
        factory = FormViewModelFactory(context)
    )
    val doctorAppointmentViewModel : DoctorAppointmentsViewModel = viewModel()

    NavHost(
        startDestination = "mainconsultingscreen",
        navController = navController
    ) {

        composable(route = "Login") {
            LoginPage(navController = navController, userViewModel = userViewModel)
        }
        composable(route = "SignUp") {
            SignUpPage(navController = navController)
        }
        composable("SignUpResponse") {
            SignUpResponse(particularUserSignUpViewModel = particularUserSignUpViewModel)
        }
        composable(
            route = "first_form/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            Form_One_Screen(doctorId = doctorId, navController = navController, viewModel = sharedViewModel)
        }
        composable(
            route = "second_form/{doctorId}",
            arguments = listOf(navArgument("doctorId") { type = NavType.StringType })
        ) { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: ""
            Form_Two_Screen(doctorId = doctorId, navController = navController, viewModel = sharedViewModel)
        }
        composable("AppointmentsScreen/{token}",
            arguments = listOf(
                navArgument("token"){
                    type = NavType.StringType
                    nullable = false
                }
            )) {backStackEntry->
            val token_one = backStackEntry.arguments?.getString("token")?:""
            Log.d("DEBUG_NAV", "Navigation: Token = $token_one")
            DoctorAppointmentScreen(token = token_one, navController = navController, viewModel = doctorAppointmentViewModel)
        }
        composable("ParticularPatientScreen/{patientId}",
            arguments = listOf(
                navArgument("patientId"){type = NavType.StringType})
        ) {backStack->
            val patientId = backStack.arguments?.getString("patientId")?:""
            ParticularPatientScreen(
                patientId = patientId,
                viewModel = doctorAppointmentViewModel,
                navController = navController
            )
        }

        composable("mainconsultingscreen") {
            ConsultingMainScreen(navController = navController, viewModel = BasicPatientInformationViewModel)
        }
    }
}




// custom top bar in Appointment screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppointmentTopBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onMenuClick: () -> Unit,
    onLogoutClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(

        title = {
            // Integrated Search Bar in Title

            SearchBar(
                tonalElevation = 9.dp,
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onSearch = { /* Handle search */ },
                active = false, // Always collapsed in topbar
                onActiveChange = { },
                placeholder = {
                    Text(
                        "Search appointments...",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 18.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { onSearchQueryChange("") }) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Clear",
                                tint = Color.White
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                    dividerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(10.dp),
            ) { }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        modifier = Modifier.background(brush = gradient_colors).padding(vertical = 10.dp),
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onLogoutClick) {
                Icon(
                    painter = painterResource(R.drawable.outline_logout_24),
                    contentDescription = "Logout",
                    tint = Color.White
                )
            }
        }
    )
}