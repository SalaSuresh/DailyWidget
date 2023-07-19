package com.suresh.dailywidget.utils

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.suresh.dailywidget.R
import com.suresh.dailywidget.models.Quote
import com.suresh.dailywidget.screens.widget.MessageWidget

class AppUtils {
    companion object {
        fun shareQuote(context: Context, quote: Quote) {
            val quoteMessage = "${quote.quote}\n-${quote.quotemaster}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, quoteMessage)
                type = "text/plain"
            }
            
            val shareIntent =
                Intent.createChooser(sendIntent, context.getString(R.string.share_title))
            context.startActivity(shareIntent)
        }

        fun updateWidgetUI(context: Context) {
            val widgetIntent = Intent(context, MessageWidget::class.java)
            widgetIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            val ids = AppWidgetManager.getInstance(context)
                .getAppWidgetIds(ComponentName(context, MessageWidget::class.java))
            widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
            context.sendBroadcast(widgetIntent)
        }
    }
}