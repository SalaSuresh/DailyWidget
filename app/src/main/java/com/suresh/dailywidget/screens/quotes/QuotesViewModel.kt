package com.suresh.dailywidget.screens.quotes

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import com.suresh.dailywidget.R
import com.suresh.dailywidget.models.Quote
import com.suresh.dailywidget.network.ApiClient
import com.suresh.dailywidget.network.ApiInterface
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
                    TODO("Add functionality to show downloaded quotes")
                } catch (exception: Exception) {
                    showFailure(requireActivity)
                }
            }

            override fun onFailure(call: Call<List<Quote>>, t: Throwable) {
                showFailure(requireActivity)
            }
        })
    }

    private fun showFailure(requireActivity: FragmentActivity) {
        Toast.makeText(
            requireActivity,
            requireActivity.getString(R.string.failed_to_load),
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getQuotesList(): ArrayList<Quote> {
        val widgetQuotes = ArrayList<Quote>()
        for (a in 1..5) {
            val quote = Quote()
            quote.quote = "Test Title $a"
            quote.quotemaster = "Test Message $a"
            widgetQuotes.add(quote)
        }
        return widgetQuotes
    }
}