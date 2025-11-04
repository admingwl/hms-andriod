package com.example.happydocx.ui.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.happydocx.Data.TokenManager
import com.example.happydocx.ui.ViewModels.ParticularUserSignInViewModel

@Composable
fun HomeScreen(
    navController: NavController
) {
    // know we have data
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        LogoutButton(navController = navController)
    }
}

@Composable
fun LogoutButton(
    navController: NavController
) {
    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    val token = tokenManager.getToken()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$token", fontSize = 14.sp)
        Button(
            onClick = {
                // Clear token and user data
                tokenManager.clearToken()

                // Navigate back to login screen
                navController.navigate("Login") {
                    // Clear entire back stack
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
        {
            Text("Logout")
        }
    }
}
