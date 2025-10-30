package com.example.happydocx.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.happydocx.ui.Screens.HomeScreen
import com.example.happydocx.ui.Screens.LoginPage
import com.example.happydocx.ui.Screens.SignUpForms.Form_One_Screen
import com.example.happydocx.ui.Screens.SignUpForms.Form_Two_Screen
import com.example.happydocx.ui.Screens.SignUpPage
import com.example.happydocx.ui.Screens.SignUpResponse
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel
import com.example.happydocx.ui.ViewModels.ParticularUserSignUpViewModel

@Composable
fun NavigationGraph(){

    val navController = rememberNavController()
    val userViewModel : ParticularUserSignInViewModel  = viewModel()
    val particularUserSignUpViewModel: ParticularUserSignUpViewModel = viewModel()
    NavHost(
        startDestination = "second_form",
        navController = navController
    ){

        composable("Home") {
            HomeScreen(userViewModel = userViewModel)
        }
        composable(route = "Login"){
            LoginPage(navController = navController, userViewModel = userViewModel)
        }
        composable(route = "SignUp"){
            SignUpPage(navController = navController)
        }
        composable("SignUpResponse") {
            SignUpResponse(particularUserSignUpViewModel = particularUserSignUpViewModel)
        }
        composable("first_form"){
            Form_One_Screen()
        }
        composable("second_form") {
            Form_Two_Screen()
        }
    }
}