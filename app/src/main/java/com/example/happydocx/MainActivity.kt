package com.example.happydocx

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.happydocx.ui.Navigation.NavigationGraph
import com.example.happydocx.ui.theme.HappyDocxTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyDocxTheme {
                HappyDocx() 
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HappyDocx(){
    NavigationGraph()
}
