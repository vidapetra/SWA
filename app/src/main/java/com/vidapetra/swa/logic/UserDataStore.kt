package com.vidapetra.swa.logic

import android.content.Context
import hu.autsoft.krate.SimpleKrate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.gson.gson
import hu.autsoft.krate.gson.gsonPref
import hu.autsoft.krate.stringPref

class UserDataStore(context: Context): SimpleKrate(context,"user_prefs") {
    init {
        //gson = localGson
    }

    /*var firstRun: Boolean by booleanPref("firstRun",true)
    var pushToken: String? by stringPref("pushToken")
    var accessToken: String? by stringPref("accessToken")*/

    var userEmail: String? by stringPref("userEmail")
    var userId: String? by stringPref("userId")
    //var refreshToken: String? by stringPref("refreshToken")




    fun clearLogin(){
        userEmail = null
        userId = null

    }
}

