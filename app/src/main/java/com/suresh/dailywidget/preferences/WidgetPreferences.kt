package com.suresh.dailywidget.preferences

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.suresh.dailywidget.constants.ApplicationConstants
import com.suresh.dailywidget.models.PaletteColor
import com.suresh.dailywidget.models.Quote


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
        return sharedPreference!!.getString(
            PreferenceConstants.PREF_QUOTE,
            ApplicationConstants.DEFAULT_QUOTE
        )
    }

    fun saveQuoteMaster(quoteMaster: String) {
        getPreferenceEditor().putString(PreferenceConstants.PREF_QUOTE_MASTER, quoteMaster)
        getPreferenceEditor().apply()
    }

    fun getQuoteMaster(): String? {
        return sharedPreference!!.getString(
            PreferenceConstants.PREF_QUOTE_MASTER,
            ApplicationConstants.DEFAULT_QUOTE_MASTER
        )
    }

    fun saveQuotes(quotesJson: String) {
        getPreferenceEditor().putString(PreferenceConstants.PREF_QUOTES, quotesJson)
        getPreferenceEditor().apply()
    }

    fun getSavedQuotes(): ArrayList<Quote> {
        val savedQuotesJson = sharedPreference!!.getString(PreferenceConstants.PREF_QUOTES, "")
        return if (TextUtils.isEmpty(savedQuotesJson)) {
            ArrayList()
        } else {
            val listType = object : TypeToken<ArrayList<Quote?>?>() {}.type
            val quotesList: ArrayList<Quote> = Gson().fromJson(savedQuotesJson, listType)
            quotesList
        }
    }

    fun saveWidgetColor(paletteColor: PaletteColor) {
        getPreferenceEditor().putString(
            PreferenceConstants.PREF_WIDGET_BG_COLOR,
            paletteColor.backgroundColor
        )
        getPreferenceEditor().putString(
            PreferenceConstants.PREF_WIDGET_TEXT_COLOR,
            paletteColor.textColor
        )
        getPreferenceEditor().apply()
    }

    fun getWidgetColor(): PaletteColor {
        val widgetColor = sharedPreference!!.getString(
            PreferenceConstants.PREF_WIDGET_BG_COLOR,
            ApplicationConstants.DEFAULT_WIDGET_BG_COLOR
        )
        val textColor = sharedPreference!!.getString(
            PreferenceConstants.PREF_WIDGET_TEXT_COLOR,
            ApplicationConstants.DEFAULT_WIDGET_TEXT_COLOR
        )
        val paletteColor = PaletteColor()
        paletteColor.textColor = textColor!!
        paletteColor.backgroundColor = widgetColor!!
        return paletteColor
    }
}