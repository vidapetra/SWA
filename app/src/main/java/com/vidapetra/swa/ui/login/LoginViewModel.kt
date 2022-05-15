package com.vidapetra.swa.ui.login

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vidapetra.swa.R
import com.vidapetra.swa.logic.activityContext
import com.vidapetra.swa.logic.string

class LoginViewModel : ViewModel() {

    var email = MutableLiveData("")
    var password = MutableLiveData("")


    fun loginPressed(){
        if (email.value != null && password.value != null){
            if (email.value!!.isNotEmpty() && password.value!!.isNotEmpty()){
                activityContext?.signin(email.value!!, password.value!!)
            } else {
                Toast.makeText(
                    activityContext, string(R.string.empty),
                    Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                activityContext, string(R.string.error),
                Toast.LENGTH_SHORT).show()
        }
    }

}