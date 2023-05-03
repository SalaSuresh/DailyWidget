package com.suresh.dailywidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews

class MessageWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        Log.d(
            "test",
            "onUpdate() called with: context = $context, appWidgetManager = $appWidgetManager, appWidgetIds = $appWidgetIds"
        )
        for (appWidgetId in appWidgetIds!!) {
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            val remoteViews = RemoteViews(context!!.packageName, R.layout.title_widget)
            remoteViews.setOnClickPendingIntent(R.id.textMessage, pendingIntent)
            appWidgetManager!!.updateAppWidget(appWidgetId, remoteViews)
        }
    }
}