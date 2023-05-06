package com.suresh.dailywidget.receiver

import android.appwidget.AppWidgetManager
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.suresh.dailywidget.MessageWidget
import com.suresh.dailywidget.R
import com.suresh.dailywidget.model.QuoteMessage
import com.suresh.dailywidget.network.ApiClient
import com.suresh.dailywidget.network.ApiInterface
import com.suresh.dailywidget.preferences.WidgetPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WidgetMessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        getQuoteData(context)
    }

    private fun getQuoteData(context: Context?) {
        val apiInterface: ApiInterface =
            ApiClient().getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getWidgetQuotes().enqueue(object : Callback<List<QuoteMessage>> {
            override fun onResponse(
                call: Call<List<QuoteMessage>>,
                response: Response<List<QuoteMessage>>
            ) {
                val widgetQuotes = response.body()!!
                val randomNumber = (widgetQuotes.indices).random()
                updateWidgetQuote(context, widgetQuotes[randomNumber])
            }

            override fun onFailure(call: Call<List<QuoteMessage>>, t: Throwable) {
                Toast.makeText(
                    context,
                    context!!.getString(R.string.failed_to_load),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateWidgetQuote(context: Context?, quoteMessage: QuoteMessage) {
        val widgetPreferences = WidgetPreferences(context!!)
        widgetPreferences.saveQuote(quoteMessage.quote!!)
        widgetPreferences.saveQuoteMaster(quoteMessage.quotemaster!!)
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