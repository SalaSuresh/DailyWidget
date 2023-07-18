package com.suresh.dailywidget.screens.quotes

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _quotesLiveData = MutableLiveData<ArrayList<Quote>>()
    val quotesLiveData: LiveData<ArrayList<Quote>> = _quotesLiveData

    private fun setQuotesList(value: ArrayList<Quote>) {
        _quotesLiveData.value = value
    }

    fun closeFragment(quotesFragment: QuotesFragment) {
        NavHostFragment.findNavController(quotesFragment).navigateUp()
    }

    fun downloadQuotes(requireActivity: FragmentActivity) {
        val preferences = WidgetPreferences(requireActivity)
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
                    setQuotesList(preferences.getSavedQuotes())
                } catch (exception: Exception) {
                    showFailure(requireActivity)
                    setQuotesList(ArrayList())
                }
            }

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                showFailure(requireActivity)
                setQuotesList(ArrayList())
            }
        })
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

    fun readQuotesList(requireActivity: FragmentActivity) {
        val preferences = WidgetPreferences(requireActivity)
        if (preferences.getSavedQuotes().isEmpty()) {
            downloadQuotes(requireActivity)
        } else {
            setQuotesList(preferences.getSavedQuotes())
        }
    }
}