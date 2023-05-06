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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suresh.dailywidget.adapter.QuotesRecyclerAdapter
import com.suresh.dailywidget.model.Quote
import com.suresh.dailywidget.network.ApiClient
import com.suresh.dailywidget.network.ApiInterface
import com.suresh.dailywidget.preferences.WidgetPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), QuotesRecyclerAdapter.QuoteSelectListener {
    private lateinit var textQuote: TextView
    private lateinit var textQuoteMaster: TextView
    private lateinit var buttonRefreshQuote: Button
    private lateinit var buttonShareQuote: Button
    private lateinit var switchRefreshOption: Switch
    private lateinit var recyclerQuotes: RecyclerView
    private lateinit var widgetPreferences: WidgetPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        widgetPreferences = WidgetPreferences(this@MainActivity)

        textQuote = findViewById(R.id.textQuote)
        textQuoteMaster = findViewById(R.id.textQuoteMaster)
        recyclerQuotes = findViewById(R.id.recyclerQuotes)

        switchRefreshOption = findViewById(R.id.switchRefresh)
        switchRefreshOption.setOnCheckedChangeListener { _, isChecked ->
            widgetPreferences.saveRefreshOption(isChecked)
            updateWidgetUI()
        }

        buttonRefreshQuote = findViewById(R.id.buttonRefreshQuote)
        buttonRefreshQuote.setOnClickListener {
            getQuoteData(true)
        }
        buttonShareQuote = findViewById(R.id.buttonShareQuote)
        buttonShareQuote.setOnClickListener {
            val quote = widgetPreferences.getQuote()
            val quoteMaster = widgetPreferences.getQuoteMaster()
            val quoteMessage = "${quote}\n-${quoteMaster}"
            shareQuote(quoteMessage)
        }
    }

    private fun shareQuote(quoteMessage: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, quoteMessage)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_title))
        startActivity(shareIntent)
    }

    override fun onResume() {
        super.onResume()
        updateQuoteUI()
        getQuoteData(false)
    }

    private fun updateQuoteUI() {
        val quote = widgetPreferences.getQuote()
        val quoteMaster = widgetPreferences.getQuoteMaster()
        textQuote.text = quote
        textQuoteMaster.text = quoteMaster
    }

    private fun getQuoteData(updateQuoteOnWidget: Boolean) {
        val apiInterface: ApiInterface =
            ApiClient().getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getWidgetQuotes().enqueue(object : Callback<List<Quote>> {
            override fun onResponse(
                call: Call<List<Quote>>,
                response: Response<List<Quote>>
            ) {
                val widgetQuotes = response.body()!!
                if (updateQuoteOnWidget) {
                    updateWidgetQuote(widgetQuotes)
                } else {
                    updateQuotesRecycler(widgetQuotes)
                }

            }

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.failed_to_load),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun updateQuotesRecycler(widgetQuotes: List<Quote>) {
        recyclerQuotes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val quotesRecyclerAdapter = QuotesRecyclerAdapter(widgetQuotes, this@MainActivity)
        recyclerQuotes.adapter = quotesRecyclerAdapter
    }

    private fun updateWidgetQuote(quotes: List<Quote>) {
        val randomNumber = (quotes.indices).random()
        widgetPreferences.saveQuote(quotes[randomNumber].quote!!)
        widgetPreferences.saveQuoteMaster(quotes[randomNumber].quotemaster!!)
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

    override fun onItemClick(quote: Quote) {
        widgetPreferences.saveQuote(quote.quote!!)
        widgetPreferences.saveQuoteMaster(quote.quotemaster!!)
        updateWidgetUI()
        updateQuoteUI()
    }

    override fun onShareClick(quote: Quote) {
        val quoteMessage = "${quote.quote}\n-${quote.quotemaster}"
        shareQuote(quoteMessage)
    }
}