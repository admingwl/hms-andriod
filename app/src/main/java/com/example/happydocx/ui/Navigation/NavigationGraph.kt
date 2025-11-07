package com.example.happydocx.ui.Navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.happydocx.ui.Screens.HomeScreen
import com.example.happydocx.ui.Screens.LoginPage
import com.example.happydocx.ui.Screens.SignUpForms.Form_One_Screen
import com.example.happydocx.ui.Screens.SignUpForms.Form_Two_Screen
import com.example.happydocx.ui.Screens.SignUpPage
import com.example.happydocx.ui.Screens.SignUpResponse
import com.example.happydocx.ui.ViewModels.FormViewModelFactory
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel
import com.example.happydocx.ui.ViewModels.ParticularUserSignUpViewModel
import com.example.happydocx.ui.ViewModels.formViewModel

@Composable
fun NavigationGraph() {

    val context = LocalContext.current
    val navController = rememberNavController()
    val userViewModel: ParticularUserSignInViewModel = viewModel()
    val particularUserSignUpViewModel: ParticularUserSignUpViewModel = viewModel()

    // Create viewModel at navigation graph level so it survives between screens
    val sharedViewModel: formViewModel = viewModel(
        factory = FormViewModelFactory(context)
    )
    NavHost(
        startDestination = "Login",
        navController = navController
    ) {

        composable("Home") {
            HomeScreen(navController = navController)
        }
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
    }
}