package com.example.happydocx.Data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserStore(
    private val context: Context
){

    companion object{
        /**This line uses a special Kotlin trick called an Extension Property. It says, "Whenever you have a Context, it now has a special property called dataStore."*/
        /** preferencesDataStore-> give me dataStore object. We named this specific safe "userToken".*/
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userToken")
        /** Every item inside the safe needs a unique label so we can find it later. The stringPreferencesKey function creates
        a special key (of type Preferences.Key<String>) that can only hold a text string. We call its label "user_token".*/
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    }

    /**
     * val getAccessToken : Flow<String>: The type is Flow<String>. Think of a Flow as a river of data. It's not just a single value; it's a way to get
     * the latest token value, and if the token ever changes (like if we save a new one)
     * , the river will automatically send out the new value. This is perfect for Compose,
     * as your composables can collect this Flow and automatically update!
     */

    /**
     * context.dataStore.data: We access the dataStore we defined earlier, and call the .data property on it. This gives us the Flow of everything inside our safe (which is a Preferences object).
     *
     *
     * .map { preferences -> ... }: The .map function is like a filter or a processor on the river. It takes the big preferences object and pulls out only the one thing we care about: the token.
     * preferences[USER_TOKEN_KEY]: We use our label to get the token value.
     * ?: "": This is the Elvis operator (the ?:). It's a safety check: if we try to open the safe and the token isn't there yet
     * (maybe it's the very first time the app runs), we return an empty string ("") instead of causing a crash.
     */
    val getAccessToken : Flow<String> =   context.dataStore.data.map { preferences->
        preferences[USER_TOKEN_KEY] ?: ""
    }

    /**
     * suspend fun saveToken(token:String): This is a suspend function. Because writing data to storage takes a little bit of time, we mark it with suspend to tell Kotlin,
     * "Hey, this is a background task that needs to pause the current execution until it's done."
     *
     * context.dataStore.edit { preferences -> ... }: The .edit function is the special way we tell the DataStore safe that we want to change something. It gives us the current preferences object.
     * preferences[USER_TOKEN_KEY] = token: Just like we retrieved it with the label, we set the new token value into the safe using the same USER_TOKEN_KEY label.
     */
    suspend fun saveToken(token:String){
        context.dataStore.edit { preferences->
            preferences[USER_TOKEN_KEY] = token
        }
    }
}