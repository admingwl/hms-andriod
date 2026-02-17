package com.example.happydocx

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.happydocx.Data.ZoomMeetingManager.ZoomJWTGenerator
import com.example.happydocx.ui.Navigation.NavigationGraph
import com.example.happydocx.ui.theme.HappyDocxTheme
import us.zoom.sdk.ZoomError
import us.zoom.sdk.ZoomSDK
import us.zoom.sdk.ZoomSDKInitParams
import us.zoom.sdk.ZoomSDKInitializeListener


class MainActivity : ComponentActivity(){
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialize the zoom Video Sdk here.
        initZoomVideoSdk(this)
        enableEdgeToEdge()
        setContent {
            HappyDocxTheme {
                HappyDocx()
            }
        }
    }

    fun initZoomVideoSdk(context: Context){
        val zoomSdk = ZoomSDK.getInstance()

        if(zoomSdk.isInitialized){
            Log.d("ZoomSDK", "Already Initialized")
            return
        }

        val jwtTokenString = ZoomJWTGenerator.generateTokenZoom()
        // Token validation
        if (jwtTokenString.isEmpty()) {
            Log.e("ZoomSDK", "JWT Token is EMPTY!")
            return
        }

        Log.d("ZoomSDK", "Token length: ${jwtTokenString.length}")
        Log.d("ZoomSDK", "Token preview: ${jwtTokenString.take(50)}...")

        val params  = ZoomSDKInitParams().apply{
            domain = "zoom.us"
            enableLog = true
            jwtToken = jwtTokenString
        }

        val listener = object : ZoomSDKInitializeListener {
            override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
                when (errorCode) {
                    ZoomError.ZOOM_ERROR_SUCCESS -> {
                        Log.d("ZoomInit", " SUCCESS! SDK initialized")
                    }
                    ZoomError.ZOOM_ERROR_INVALID_ARGUMENTS -> {
                        Log.e("ZoomInit", " INVALID ARGUMENTS - Check JWT token format")
                    }
                    ZoomError.ZOOM_ERROR_ILLEGAL_APP_KEY_OR_SECRET -> {
                        Log.e("ZoomInit", " INVALID SDK KEY/SECRET")
                    }
                    else -> {
                        Log.e("ZoomInit", " Error: $errorCode, Internal: $internalErrorCode")
                    }
                }
            }

            override fun onZoomAuthIdentityExpired() {
                Log.w("ZoomInit", " JWT token expired - regenerate token")
            }
        }
        zoomSdk.initialize(context,listener,params)

    }
}
@Composable
fun HappyDocx(){
    NavigationGraph()
}
