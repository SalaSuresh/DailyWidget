package com.suresh.dailywidget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.suresh.dailywidget.adapter.QuotesRecyclerAdapter2
import com.suresh.dailywidget.databinding.FragmentQuotesBinding
import com.suresh.dailywidget.models.Quote

class QuotesFragment : Fragment(), QuotesRecyclerAdapter2.QuoteSelectListener {
    private lateinit var binding: FragmentQuotesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                NavHostFragment.findNavController(this@QuotesFragment).navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, onBackPressedCallback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuotesBinding.inflate(layoutInflater, container, false)
        showQuotesList()
        return binding.root
    }

    private fun showQuotesList() {
        val widgetQuotes = ArrayList<Quote>()
        for (a in 1..5) {
            val quote = Quote()
            quote.quote = "Test Title $a"
            quote.quotemaster = "Test Message $a"
            widgetQuotes.add(quote)
        }
        binding.recyclerQuotes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val quotesRecyclerAdapter = QuotesRecyclerAdapter2(widgetQuotes, this)
        binding.recyclerQuotes.adapter = quotesRecyclerAdapter
    }

    override fun onItemClick(quote: Quote) {
        TODO("Not yet implemented")
    }

    override fun onShareClick(quote: Quote) {
        TODO("Not yet implemented")
    }
}