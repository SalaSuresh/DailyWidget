package com.suresh.dailywidget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.suresh.dailywidget.model.QuoteMessage
import com.suresh.dailywidget.network.ApiClient
import com.suresh.dailywidget.network.ApiInterface
import com.suresh.dailywidget.preferences.WidgetPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var textQuote: TextView
    private lateinit var textQuoteMaster: TextView
    private lateinit var buttonRefreshQuote: Button
    private lateinit var switchRefreshOption: Switch
    private lateinit var widgetPreferences: WidgetPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        widgetPreferences = WidgetPreferences(this@MainActivity)

        textQuote = findViewById(R.id.textQuote)
        textQuoteMaster = findViewById(R.id.textQuoteMaster)

        switchRefreshOption = findViewById(R.id.switchRefresh)
        switchRefreshOption.setOnCheckedChangeListener { _, isChecked ->
            widgetPreferences.saveRefreshOption(isChecked)
            updateWidgetUI()
        }

        buttonRefreshQuote = findViewById(R.id.buttonRefreshQuote)
        buttonRefreshQuote.setOnClickListener {
            getQuoteData()
        }
    }

    override fun onResume() {
        super.onResume()
        updateQuoteUI()
    }

    private fun updateQuoteUI() {
        val quote = widgetPreferences.getQuote()
        val quoteMaster = widgetPreferences.getQuoteMaster()
        textQuote.text = quote
        textQuoteMaster.text = quoteMaster
    }

    private fun getQuoteData() {
        val apiInterface: ApiInterface =
            ApiClient().getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getWidgetQuotes().enqueue(object : Callback<List<QuoteMessage>> {
            override fun onResponse(
                call: Call<List<QuoteMessage>>,
                response: Response<List<QuoteMessage>>
            ) {
                val widgetQuotes = response.body()!!
                val randomNumber = (widgetQuotes.indices).random()
                updateWidgetQuote(widgetQuotes[randomNumber])
            }

            override fun onFailure(call: Call<List<QuoteMessage>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.failed_to_load),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateWidgetQuote(quoteMessage: QuoteMessage) {
        widgetPreferences.saveQuote(quoteMessage.quote!!)
        widgetPreferences.saveQuoteMaster(quoteMessage.quotemaster!!)
        updateWidgetUI()
        updateQuoteUI()
    }

    private fun updateWidgetUI() {
        val widgetIntent = Intent(this@MainActivity, MessageWidget::class.java)
        widgetIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val ids = AppWidgetManager.getInstance(this@MainActivity)
            .getAppWidgetIds(ComponentName(this@MainActivity, MessageWidget::class.java))
        widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(widgetIntent)
    }
}