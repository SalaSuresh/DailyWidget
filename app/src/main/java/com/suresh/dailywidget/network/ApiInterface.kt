package com.suresh.dailywidget.network

import com.suresh.dailywidget.model.QuoteMessage
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("dailyWidget.json")
    fun getWidgetQuotes(): Call<List<QuoteMessage>>
}