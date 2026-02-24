package com.example.happydocx

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.happydocx.Data.ZoomMeetingManager.ZoomJWTGenerator
import com.example.happydocx.ui.Navigation.NavigationGraph
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.AddNewVitalSignsScreen
import com.example.happydocx.ui.Screens.StartConsulting.UpdatedVersion1_ConsultingScreen.BasicInfoOfPatient
import com.example.happydocx.ui.theme.HappyDocxTheme
import us.zoom.sdk.MeetingError
import us.zoom.sdk.MeetingParameter
import us.zoom.sdk.MeetingServiceListener
import us.zoom.sdk.MeetingStatus
import us.zoom.sdk.StartMeetingOptions
import us.zoom.sdk.StartMeetingParamsWithoutLogin
import us.zoom.sdk.ZoomError
import us.zoom.sdk.ZoomSDK
import us.zoom.sdk.ZoomSDKInitParams
import us.zoom.sdk.ZoomSDKInitializeListener


class MainActivity : ComponentActivity(){
    private val meetingServiceListener = object : MeetingServiceListener {
        override fun onMeetingStatusChanged(
            meetingStatus: MeetingStatus?,
            errorCode: Int,
            internalErrorCode: Int
        ) {
            when (meetingStatus) {
                MeetingStatus.MEETING_STATUS_CONNECTING -> {
                    Log.d("ZoomSDK", " Connecting to meeting...")
                }
                MeetingStatus.MEETING_STATUS_INMEETING -> {
                    Log.d("ZoomSDK", " Successfully joined the meeting!")
                }
                MeetingStatus.MEETING_STATUS_FAILED -> {
                    Log.e("ZoomSDK", " Meeting failed! Error: $errorCode")
                }
                MeetingStatus.MEETING_STATUS_DISCONNECTING -> {
                    Log.d("ZoomSDK", " Disconnecting from meeting...")
                }
                MeetingStatus.MEETING_STATUS_ENDED -> {
                    Log.d("ZoomSDK", " Meeting ended!")
                }
                MeetingStatus.MEETING_STATUS_IDLE -> {
                    Log.d("ZoomSDK", " Meeting idle")
                }
                else -> {
                    Log.d("ZoomSDK", "Meeting status: $meetingStatus")
                }
            }
        }

        override fun onMeetingParameterNotification(p0: MeetingParameter?) {

        }


    }
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

    override fun onDestroy() {
        super.onDestroy()
        ZoomSDK.getInstance().meetingService?.removeListener(meetingServiceListener)
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

    fun initZoomCall(
        zakToken: String = "123445345345345345",
        meetingNumber: String = "8461870425",
        context: ComponentActivity
    ) {
        val meetingService = ZoomSDK.getInstance().meetingService

        if (meetingService == null) {
            Log.e("ZoomSDK", " Meeting service is null! SDK not initialized?")
            return
        }

        val startParams = StartMeetingParamsWithoutLogin().apply {
            zoomAccessToken = zakToken
            meetingNo = meetingNumber
        }

        val options = StartMeetingOptions().apply {
            no_titlebar = false
            no_bottom_toolbar = false
            no_video = false
            no_audio = false
            no_invite = true
            no_share = true
        }

        meetingService.addListener(meetingServiceListener)

        val result = meetingService.startMeetingWithParams(context, startParams, options)

        when (result) {
            MeetingError.MEETING_ERROR_SUCCESS -> {
                Log.d("ZoomSDK", " Meeting started successfully!")
            }
            MeetingError.MEETING_ERROR_NETWORK_UNAVAILABLE -> {
                Log.e("ZoomSDK", " No internet connection!")
            }
            MeetingError.MEETING_ERROR_INVALID_ARGUMENTS -> {
                Log.e("ZoomSDK", " Invalid ZAK token or meeting number!")
            }
            MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE -> {
                Log.e("ZoomSDK", " Zoom SDK version incompatible!")
            }
            else -> {
                Log.e("ZoomSDK", " Unknown error code: $result")
            }
        }
    }
}
@Composable
fun HappyDocx(){
    NavigationGraph()
}

@Composable
fun StartCall(modifier: Modifier = Modifier,context: ComponentActivity) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                (context as MainActivity).initZoomCall(
                    context = context
                )
            }
        ) {
            Text("Start Meeting")
        }
    }
}
