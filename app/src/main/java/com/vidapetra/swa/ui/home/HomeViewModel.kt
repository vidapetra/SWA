package com.vidapetra.swa.ui.home

import androidx.lifecycle.ViewModel
import com.vidapetra.swa.logic.Router.loadFragment
import com.vidapetra.swa.logic.Router.settings
import com.vidapetra.swa.ui.settings.SettingsFragment
import java.text.DecimalFormat
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.toolbox.JsonObjectRequest
import com.vidapetra.swa.R
import com.vidapetra.swa.logic.activityContext
import com.vidapetra.swa.logic.string
import org.json.JSONObject


class HomeViewModel : ViewModel() {

    var city = settings.position
    var unit = if(settings.celsius) "C" else "F"
    var text = MutableLiveData("")
    var feelsLikeText = MutableLiveData("")
    var clear = MutableLiveData(true)

    private val url = "https://api.openweathermap.org/data/2.5/weather"
    private val appid = "2970d887cfbc2fc7003accee91c4783e"
    var df: DecimalFormat = DecimalFormat("#.##")

    fun setData(){
        city = settings.position
        unit = if(settings.celsius) "C" else "F"
        getJsonData()
    }

    fun openSettings(){
        loadFragment(SettingsFragment())
    }

    private fun getJsonData()
    {
        val queue = Volley.newRequestQueue(activityContext)
        val tempurl = "$url?q=$city&appid=$appid"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, tempurl,null,
            { response ->
                setValues(response)
            },
            {
                Toast.makeText(activityContext, string(R.string.city_error), Toast.LENGTH_LONG).show()
                text.postValue("")
                feelsLikeText.postValue("")
                clear.postValue(false)
            })


        queue.add(jsonRequest)
    }

    private fun setValues(response:JSONObject){
        val tempr=response.getJSONObject("main").getString("temp")
        val feelsLikeTempr=response.getJSONObject("main").getString("feels_like")
        val weather = response.getJSONArray("weather").getJSONObject(0).getString("main")
        clear.postValue(weather == "Clear")
        writeTemps((tempr).toFloat()-273.15f, (feelsLikeTempr).toFloat()-273.15f)
    }

    fun writeTemps(tempr : Float, feelsLikeTempr: Float){
        val x: String
        val y: String
        if (unit == "F") {
            x = df.format((tempr) * 1.8 + 32)
            y = df.format((feelsLikeTempr) * 1.8 + 32)
        } else {
            x = df.format(tempr)
            y = df.format(feelsLikeTempr)
        }

        text.postValue("$x °$unit")
        feelsLikeText.postValue(string(R.string.feels_like) + " $y °$unit")
    }

}