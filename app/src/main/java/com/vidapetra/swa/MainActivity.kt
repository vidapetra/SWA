package com.vidapetra.swa

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vidapetra.swa.logic.Router.settings
import com.vidapetra.swa.logic.Settings
import com.vidapetra.swa.logic.Singletons.activityContext
import com.vidapetra.swa.logic.string
import com.vidapetra.swa.logic.userDataStore
import com.vidapetra.swa.ui.home.HomeFragment
import com.vidapetra.swa.ui.login.LoginFragment
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.firestore
    lateinit var currentLocale: Locale

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        settings = Settings(
            celsius = true,
            auto = true,
            dark = false,
            language = "en",
            notification = true,
            locale = false,
            position = "Budapest"
        )

        if (userDataStore.userId != null) {
            val docRef = db.collection("settings").document(userDataStore.userId!!)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        settings = Settings(
                            document.data?.get("celsius") as Boolean,
                            document.data!!["auto"] as Boolean,
                            document.data!!["dark"] as Boolean,
                            document.data!!["language"] as String,
                            document.data!!["notification"] as Boolean,
                            document.data!!["locale"] as Boolean,
                            document.data!!["position"] as String
                        )
                    }
                }
                .addOnFailureListener { exception ->

                }
        }
        setDarkMode()
        setLanguage()

        if(savedInstanceState == null) {
            loadFragment(LoginFragment())
        }
    }

    override fun onStart() {
        super.onStart()
        activityContext = this
        val currentUser = auth.currentUser
        userDataStore.userEmail = currentUser?.email
        userDataStore.userId = currentUser?.uid

    }

    override fun onStop() {
        super.onStop()
        if (activityContext == this) {
            activityContext = null
        }
    }

    fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("${fragment::class.java}")
            .commitAllowingStateLoss()
    }

    fun back(){
        supportFragmentManager.popBackStack()
    }

    fun setDarkMode() {
        if (settings.auto) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        } else {
            if (settings.dark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    fun setLanguage(){
        currentLocale = if (settings.language == "hu") {
            Locale("hu")
        } else {
            Locale.ENGLISH
        }

        val res = resources
        val dm = res.displayMetrics
        val conf = Configuration()
        conf.locale = currentLocale
        res.updateConfiguration(conf, dm)
    }

    fun signin(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    userDataStore.userId = user?.uid
                    userDataStore.userEmail = user?.email
                    loadFragment(HomeFragment())
                } else {
                    Toast.makeText(baseContext, string(R.string.auth_fail),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun logout(){
        Firebase.auth.signOut()
        for(i in 0..supportFragmentManager.backStackEntryCount) supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, LoginFragment())
            .commitAllowingStateLoss()
        userDataStore.clearLogin()
    }

    fun saveSettings(){
        userDataStore.userId?.let { db.collection("settings").document(it).set(settings) }
    }

}

