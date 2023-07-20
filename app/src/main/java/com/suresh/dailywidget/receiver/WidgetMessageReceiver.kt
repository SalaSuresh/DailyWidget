package com.suresh.dailywidget.receiver

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.suresh.dailywidget.preferences.WidgetPreferences
import com.suresh.dailywidget.screens.widget.MessageWidget

class WidgetMessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        updateWidgetQuote(context)
    }

    private fun updateWidgetQuote(context: Context?) {
        val widgetPreferences = WidgetPreferences(context!!)
        val savedQuotesList = widgetPreferences.getSavedQuotes()
        val randomNumber = (savedQuotesList.indices).random()
        widgetPreferences.saveQuote(savedQuotesList[randomNumber].quote!!)
        widgetPreferences.saveQuoteMaster(savedQuotesList[randomNumber].quotemaster!!)
        updateWidgetUI(context)
    }

    private fun updateWidgetUI(context: Context?) {
        val widgetIntent = Intent(context, MessageWidget::class.java)
        widgetIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context!!, MessageWidget::class.java))
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(widgetIntent)
    }
}