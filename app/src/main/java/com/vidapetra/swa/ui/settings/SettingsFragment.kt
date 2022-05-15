package com.vidapetra.swa.ui.settings

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.vidapetra.swa.R
import com.vidapetra.swa.databinding.FragmentSettingsBinding
import com.vidapetra.swa.logic.Router.back
import com.vidapetra.swa.logic.Router.settings
import com.vidapetra.swa.logic.activityContext
import com.vidapetra.swa.logic.string
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {
    val layoutResId: Int = com.vidapetra.swa.R.layout.fragment_settings
    lateinit var binding: FragmentSettingsBinding
    val viewModel by viewModels<SettingsViewModel>()

    fun onBindingCreated(binding: FragmentSettingsBinding) {
        binding.vm = viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        onBindingCreated(binding)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (settings.auto){
            sDark.visibility = View.GONE
        }

        sAuto.setOnClickListener {
            if (sAuto.isChecked){
                sDark.visibility = View.GONE
            } else {
                sDark.visibility = View.VISIBLE
            }
        }

        if (settings.locale){
            etPosition.visibility = View.GONE
        }

        sLocale.setOnClickListener {
            if (sLocale.isChecked){
                etPosition.visibility = View.GONE
            } else {
                etPosition.visibility = View.VISIBLE
            }
        }

        btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {
        settings.celsius = celsius.isChecked
        settings.dark = sDark.isChecked
        settings.auto = sAuto.isChecked
        settings.notification = sNoti.isChecked
        settings.position = viewModel.position.value ?: ""
        settings.locale = sLocale.isChecked
        settings.language = if (hu.isChecked) "hu" else "en"
        activityContext?.setDarkMode()
        activityContext?.setLanguage()
        activityContext?.saveSettings()
        back()
    }
}