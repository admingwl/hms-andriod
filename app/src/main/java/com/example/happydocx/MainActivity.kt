package com.example.happydocx

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.happydocx.ui.Navigation.NavigationGraph
import com.example.happydocx.ui.theme.HappyDocxTheme
import us.zoom.sdk.ZoomVideoSDK
import us.zoom.sdk.ZoomVideoSDKErrors
import us.zoom.sdk.ZoomVideoSDKInitParams


class MainActivity : ComponentActivity(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize the zoom Video Sdk here.
        initZoomVideoSdk()
        enableEdgeToEdge()
        setContent {
            HappyDocxTheme {
                HappyDocx()
            }
        }
    }

    fun initZoomVideoSdk(){
        val zoomVideoSdk = ZoomVideoSDK.getInstance()

        val params = ZoomVideoSDKInitParams().apply {
            domain = "zoom.us"
            enableLog = true    // Taki logcat mein errors dikhein
        }

        val errorCode = zoomVideoSdk.initialize(this.applicationContext,params)
        // Check karo ki successfully init hua ya nahi
        if (errorCode == ZoomVideoSDKErrors.Errors_Success) {
            Log.d("ZoomVideoSDK", "Initialization Success! Ab tum sessions join kar sakte ho.")
        } else {
            Log.e("ZoomVideoSDK", "Initialization Failed! Error Code: $errorCode")
        }
    }
}
@Composable
fun HappyDocx(){
    NavigationGraph()
}
