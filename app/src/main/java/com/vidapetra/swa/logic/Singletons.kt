package com.vidapetra.swa.logic

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.vidapetra.swa.MainActivity

@SuppressLint("StaticFieldLeak")
object Singletons {
    fun initAll(application: Application) {
        applicationContext = application
        userDataStore =
            UserDataStore(applicationContext)
    }

    lateinit var userDataStore: UserDataStore
    lateinit var applicationContext: Context
    var activityContext: MainActivity? = null
}

val activityContext: MainActivity?
    get() = Singletons.activityContext
val applicationContext: Context
    get() = Singletons.applicationContext
val userDataStore
    get() = Singletons.userDataStore

fun string(@StringRes res: Int): String = (activityContext ?: applicationContext).getString(res)

object Router {

    lateinit var settings: Settings

    fun loadFragment(fragment: Fragment){
        Singletons.activityContext?.loadFragment(fragment)
    }

    fun back(){
        Singletons.activityContext?.back()
    }
}