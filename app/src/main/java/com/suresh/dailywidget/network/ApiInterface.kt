package com.suresh.dailywidget.network

import com.suresh.dailywidget.models.Quote
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("dailyWidget.json")
    fun getWidgetQuotes(): Call<List<Quote>>
}