package com.example.happydocx.Data

import android.content.Context

class TokenManager(context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "happydocx_prefs"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    // here the function for the save token after successful login
    fun saveToken(token:String){
       sharedPreferences.edit().apply{
           putString(KEY_TOKEN,token)
           putBoolean(KEY_IS_LOGGED_IN,true)
           apply()
       }
    }

    // get saved Token
    fun getToken():String?{
        return sharedPreferences.getString(KEY_TOKEN,null)
    }

    // check if user is logged in or not
    fun isLoggedIn():Boolean{
         return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false) && getToken() !=null
    }

    // clear all the data on logout
    fun clearToken(){
        sharedPreferences.edit().apply{
            remove(KEY_TOKEN)
            putBoolean(KEY_IS_LOGGED_IN,false)
            apply()
        }
    }

    // clear all preferences
    fun clearAll(){
        sharedPreferences.edit().clear().apply()
    }
}