package com.vidapetra.swa.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vidapetra.swa.logic.Router.back
import com.vidapetra.swa.logic.Router.settings
import com.vidapetra.swa.logic.activityContext

class SettingsViewModel : ViewModel() {

    var position = MutableLiveData(settings.position)
    var celsius = MutableLiveData(settings.celsius)
    var dark = MutableLiveData(settings.dark)
    var language = MutableLiveData(settings.language)
    var isHu = MutableLiveData(language.value.equals("hu"))
    var notification = MutableLiveData(settings.notification)
    var auto = MutableLiveData(settings.auto)
    var locale = MutableLiveData(settings.locale)

    fun logout(){
        activityContext?.logout()
    }

    fun cancel(){
        back()
    }
}