package com.suresh.dailywidget.preferences

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity

class WidgetPreferences(context: Context) {
    private var sharedPreference: SharedPreferences? = null
    private var editor: Editor? = null

    init {
        if (sharedPreference == null) {
            sharedPreference = context.getSharedPreferences(
                PreferenceConstants.PREF_NAME,
                AppCompatActivity.MODE_PRIVATE
            )
        }
    }

    private fun getPreferenceEditor(): Editor {
        if (editor == null) {
            editor = sharedPreference!!.edit()
        }
        return editor!!
    }

    fun saveRefreshOption(switchRefreshOption: Boolean) {
        getPreferenceEditor().putBoolean(PreferenceConstants.PREF_REFRESH, switchRefreshOption)
        getPreferenceEditor().apply()
    }

    fun getRefreshOption(): Boolean {
        return sharedPreference!!.getBoolean(PreferenceConstants.PREF_REFRESH, false)
    }

    fun saveQuote(quote: String) {
        getPreferenceEditor().putString(PreferenceConstants.PREF_QUOTE, quote)
        getPreferenceEditor().apply()
    }

    fun getQuote(): String? {
        return sharedPreference!!.getString(PreferenceConstants.PREF_QUOTE, "No Quote Available")
    }

    fun saveQuoteMaster(quoteMaster: String) {
        getPreferenceEditor().putString(PreferenceConstants.PREF_QUOTE_MASTER, quoteMaster)
        getPreferenceEditor().apply()
    }

    fun getQuoteMaster(): String? {
        return sharedPreference!!.getString(
            PreferenceConstants.PREF_QUOTE_MASTER,
            "No Quote Master Available"
        )
    }

}