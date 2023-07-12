package com.suresh.dailywidget

import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val widgetColorPreference: Preference? = findPreference("widgetColor")
        widgetColorPreference!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            Log.d("test", "show color pallet")
            false
        }
    }
}