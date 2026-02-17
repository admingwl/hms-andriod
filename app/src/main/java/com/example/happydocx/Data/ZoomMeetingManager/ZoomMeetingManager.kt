package com.example.happydocx.Data.ZoomMeetingManager

import android.content.Context
import android.util.Log
import us.zoom.sdk.InviteOptions
import us.zoom.sdk.JoinMeetingOptions
import us.zoom.sdk.JoinMeetingParams
import us.zoom.sdk.MeetingParameter
import us.zoom.sdk.MeetingServiceListener
import us.zoom.sdk.MeetingStatus
import us.zoom.sdk.StartMeetingOptions
import us.zoom.sdk.StartMeetingParamsWithoutLogin
import us.zoom.sdk.ZoomSDK

object ZoomMeetingManager {

    // meeting listener
    private  val meetingServiceListener = object : MeetingServiceListener {
        override fun onMeetingStatusChanged(
            meetingStatus: MeetingStatus?,
            errorCode: Int,
            internalErrorCode: Int
        ) {
            when (meetingStatus) {
                MeetingStatus.MEETING_STATUS_CONNECTING -> {
                    Log.d("ZoomSDK", "Connecting to meeting...")
                }

                MeetingStatus.MEETING_STATUS_INMEETING -> {
                    Log.d("ZoomSDK", "Successfully joined meeting")
                }

                MeetingStatus.MEETING_STATUS_DISCONNECTING -> {
                    Log.d("ZoomSDK", "Disconnecting from meeting...")
                }

                MeetingStatus.MEETING_STATUS_FAILED -> {
                    Log.e(
                        "ZoomSDK",
                        "Meeting failed: Error $errorCode, Internal: $internalErrorCode"
                    )
                }

                MeetingStatus.MEETING_STATUS_ENDED -> {
                    Log.d("ZoomSDK", "Meeting ended")
                }

                else -> {
                    Log.d("ZoomSDK", "Meeting status: $meetingStatus")
                }
            }
        }

        override fun onMeetingParameterNotification(p0: MeetingParameter?) {

        }
    }



    fun jointMeeting(
        context: Context,
        meetingNumber: String,
        meetingPassword: String,
        userName: String
    ) {
        val meetingService = ZoomSDK.getInstance().meetingService
        val joinMeetingOptions = JoinMeetingOptions()
        val joinMeetingParams = JoinMeetingParams().apply {
            displayName = userName
            meetingNo = meetingNumber
            password = meetingPassword
        }
        meetingService.joinMeetingWithParams(context, joinMeetingParams, joinMeetingOptions)
    }


    fun registerListener(){
        ZoomSDK.getInstance().meetingService?.addListener(meetingServiceListener)
    }
    fun unRegisterListener(){
        ZoomSDK.getInstance().meetingService?.removeListener(meetingServiceListener)
    }
    fun initCall(
        context:Context,
        meetingNumber:String,
        ZakToken:String,
        userId: String,
        password: String,
        joinUrl: String
    ){
        val meetingService = ZoomSDK.getInstance().meetingService

        // setting parameter for host
        val params = StartMeetingParamsWithoutLogin().apply {
            meetingNo = meetingNumber
            zoomAccessToken = ZakToken
        }
        meetingService.addListener(meetingServiceListener)
        // how UI look like
        val options = StartMeetingOptions().apply {
         // Video settings
            no_video = false  // true = video off se start hoga
            no_audio = false     // true = audio muted start hoga

            // Meeting controls
            no_invite = false     // true = invite button hide hoga
            no_share = false     // true = screen share disable hoga
            no_meeting_end_message = false  // true = end meeting message nahi dikhega
            no_meeting_error_message = false

            // Participant controls
            no_unmute_confirm_dialog = false  // true = unmute confirmation nahi aayega
            no_webinar_register_dialog = false

            // UI customization
            no_titlebar = false   // true = top title bar hide hoga
            no_bottom_toolbar = false  // true = bottom toolbar hide hoga
            no_dial_in_via_phone = true  // true = phone dial-in option hide
            no_dial_out_to_phone = true  // true = dial-out option hide

            // Recording
            no_record = false  // true = recording disable hoga
            // Other options
            invite_options = InviteOptions.INVITE_VIA_EMAIL or InviteOptions.INVITE_VIA_SMS or InviteOptions.INVITE_COPY_URL
        }

        // start the call
        val result = meetingService.startMeetingWithParams(context,params,options)

        if(result == 0){
            Log.d("ZoomSDK", "Call Initiated Successfully")
        }else{
            Log.e("ZoomSDK", "Initiation Failed: Error code $result")
        }
    }
}