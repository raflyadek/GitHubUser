package com.example.githubuser.ui.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.R
import com.example.githubuser.ui.viewmodel.DarkModeViewModel
import com.example.githubuser.ui.viewmodel.DarkModeViewModelFactory
import com.google.android.material.switchmaterial.SwitchMaterial

class DarkModeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dark_mode)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val darkModeViewModel = ViewModelProvider(this, DarkModeViewModelFactory(pref)).get (
            DarkModeViewModel::class.java
        )

        darkModeViewModel.getThemeSettings().observe(this){ isDarkModeActive: Boolean ->
        if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener{ _: CompoundButton?, isChecked: Boolean ->
            darkModeViewModel.saveThemeSetting(isChecked)
        }
    }
}