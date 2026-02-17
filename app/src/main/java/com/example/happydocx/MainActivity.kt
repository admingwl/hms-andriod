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
import com.example.happydocx.Data.ZoomMeetingManager.ZoomCallScreen
import com.example.happydocx.Data.ZoomMeetingManager.ZoomMeetingManager
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
        // init the zoom meeting sdk
        initZoomMeetingSdk(this)
        enableEdgeToEdge()
        setContent {
            HappyDocxTheme {
               HappyDocx()
            }
        }
    }
    fun initZoomMeetingSdk(context: Context) {
        val meetingSdk = ZoomSDK.getInstance()
        // check if it is already initialized
        if (meetingSdk.isInitialized) {
            Log.d("ZoomSDK", "Already Initialized")
            return
        }
        // first i generate the token by using my class tokenGenerator
        val jwtTokenString = ZoomJWTGenerator.generateTokenZoom()
        Log.d("Token", jwtTokenString)
        val params = ZoomSDKInitParams().apply {
            domain = "zoom.us"
            enableLog = true
            jwtToken = jwtTokenString
        }
        // here i create the listener to handle the result
        val listener = object : ZoomSDKInitializeListener {
            override fun onZoomSDKInitializeResult(errorCode: Int, internalErrorCode: Int) {
              if(errorCode == ZoomError.ZOOM_ERROR_SUCCESS) { // 0 for success
                  Log.d("ZoomInitMessage", "Successfully initialized ... $errorCode")
                  // add listner here
                  ZoomMeetingManager.registerListener()
              }else{
                  Log.e("ZoomInitMessage", "Initialization Failed: Error Code $errorCode, Internal Code $internalErrorCode")
              }
            }
            override fun onZoomAuthIdentityExpired() {
                Log.d("ExpireToken","your token is expired man")
            }
        }

        // here we initialize our meetingSDk
        meetingSdk.initialize(context,listener,params)
    }

}
@Composable
fun HappyDocx(){
    NavigationGraph()
}
