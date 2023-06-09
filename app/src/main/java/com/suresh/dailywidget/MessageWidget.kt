package com.suresh.dailywidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import com.suresh.dailywidget.preferences.WidgetPreferences
import com.suresh.dailywidget.receiver.WidgetMessageReceiver

class MessageWidget : AppWidgetProvider() {
    companion object {
        const val TAG = "widget"
    }

    override fun onUpdate(
        context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?
    ) {
        Log.d(
            TAG,
            "onUpdate() called with: context = $context, appWidgetManager = $appWidgetManager, appWidgetIds = $appWidgetIds"
        )
        for (appWidgetId in appWidgetIds!!) {
            updateWidgetUI(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateWidgetUI(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int
    ) {
        val remoteViews = RemoteViews(context!!.packageName, R.layout.widget_message)
        val widgetPreferences = WidgetPreferences(context)
        val quote = widgetPreferences.getQuote()
        val quoteMaster = widgetPreferences.getQuoteMaster()
        val isRefreshVisible = widgetPreferences.getRefreshOption()
        remoteViews.setTextViewText(R.id.textQuote, quote)
        remoteViews.setTextViewText(R.id.textQuoteMaster, quoteMaster)

        val refreshIntent = Intent(context, WidgetMessageReceiver::class.java)
        val pendingRefreshIntent = PendingIntent.getBroadcast(
            context,
            0,
            refreshIntent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        if (isRefreshVisible) {
            remoteViews.setViewVisibility(R.id.imageRefresh, View.VISIBLE)
            remoteViews.setOnClickPendingIntent(R.id.imageRefresh, pendingRefreshIntent)
        } else {
            remoteViews.setViewVisibility(R.id.imageRefresh, View.GONE)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        remoteViews.setOnClickPendingIntent(R.id.textQuote, pendingIntent)
        appWidgetManager!!.updateAppWidget(appWidgetId, remoteViews)
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d(
            TAG,
            "onAppWidgetOptionsChanged() called with: context = $context, " +
                    "appWidgetManager = $appWidgetManager, appWidgetId = $appWidgetId, " +
                    "newOptions = $newOptions"
        )
        //TODO: This is to add functionality to change the UI according to the size of widget
        val views = RemoteViews(context!!.packageName, R.layout.widget_message)

        val minWidth = newOptions!!.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)
        val maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH)
        val minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT)
        val maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT)

        Log.d(
            TAG,
            "minWidth: $minWidth maxWidth: $maxWidth minHeight: $minHeight maxHeight: $maxHeight"
        )
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d(TAG, "onDeleted() called with: context = $context, appWidgetIds = $appWidgetIds")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d(TAG, "onEnabled() called with: context = $context")
    }

    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        Log.d(TAG, "onDisabled() called with: context = $context")
    }
}