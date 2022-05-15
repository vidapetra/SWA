package com.vidapetra.swa.logic

import androidx.multidex.MultiDexApplication
import com.vidapetra.swa.logic.Singletons.initAll

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        initAll(this)
    }

}