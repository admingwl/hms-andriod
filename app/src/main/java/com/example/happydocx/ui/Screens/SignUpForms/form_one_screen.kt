package com.example.happydocx.ui.Screens.SignUpForms

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Form_One_Screen() {

    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val gradient_colors = Brush.linearGradient(listOf(
            Color(0xff586AE5), Color(0xff717FE8),
            Color(0xff7785E9)
        ))
    val scrollState = rememberScrollState()

    Scaffold(
        /*In Compose, when you use a collapsing or moving TopAppBar (like enterAlwaysScrollBehavior()),
          you must attach the scroll behavior to the scrollable content using this modifier:*/
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        // adding top bar
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Doctor Registration", fontWeight = FontWeight.Bold)
                        Text(
                            "Please fill in your personal and professional details",
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize().background(Color.White).verticalScroll(scrollState).padding(paddingValues = paddingValues)
        ) {

        }
    }
}