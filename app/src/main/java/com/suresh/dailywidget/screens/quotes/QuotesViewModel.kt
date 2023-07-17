package com.suresh.dailywidget.screens.quotes

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import com.google.gson.Gson
import com.suresh.dailywidget.R
import com.suresh.dailywidget.models.Quote
import com.suresh.dailywidget.network.ApiClient
import com.suresh.dailywidget.network.ApiInterface
import com.suresh.dailywidget.preferences.WidgetPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuotesViewModel : ViewModel() {

    fun closeFragment(quotesFragment: QuotesFragment) {
        NavHostFragment.findNavController(quotesFragment).navigateUp()
    }

    fun downloadQuotes(requireActivity: FragmentActivity) {
        val apiInterface: ApiInterface =
            ApiClient().getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getWidgetQuotes().enqueue(object : Callback<List<Quote>> {
            override fun onResponse(
                call: Call<List<Quote>>,
                response: Response<List<Quote>>
            ) {
                try {
                    val widgetQuotes = response.body()!!
                    saveDownloadedQuotes(requireActivity, widgetQuotes)
                    refreshQuotesList()
                } catch (exception: Exception) {
                    showFailure(requireActivity)
                }
            }

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                showFailure(requireActivity)
            }
        })
    }

    private fun refreshQuotesList() {
        TODO("Not yet implemented")
    }

    private fun saveDownloadedQuotes(requireActivity: FragmentActivity, widgetQuotes: List<Quote>) {
        val preferences = WidgetPreferences(requireActivity)
        val quotesJson = Gson().toJson(widgetQuotes).toString()
        preferences.saveQuotes(quotesJson)
    }

    private fun showFailure(requireActivity: FragmentActivity) {
        Toast.makeText(
            requireActivity,
            requireActivity.getString(R.string.failed_to_load),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getQuotesList(requireActivity: FragmentActivity): ArrayList<Quote> {
        val preferences = WidgetPreferences(requireActivity)
        if (preferences.getSavedQuotes().isEmpty()) {
            downloadQuotes(requireActivity)
        }
        return preferences.getSavedQuotes()
    }
}