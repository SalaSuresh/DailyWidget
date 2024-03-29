package com.suresh.dailywidget.screens.quotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suresh.dailywidget.R
import com.suresh.dailywidget.adapter.QuotesRecyclerAdapter
import com.suresh.dailywidget.databinding.FragmentQuotesBinding
import com.suresh.dailywidget.models.Quote
import com.suresh.dailywidget.utils.AppUtils

class QuotesFragment : Fragment(), QuotesRecyclerAdapter.QuoteSelectListener {
    private lateinit var binding: FragmentQuotesBinding
    private lateinit var viewModel: QuotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[QuotesViewModel::class.java]
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.closeFragment(this@QuotesFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        viewModel.quotesLiveData.observe(requireActivity(), Observer { quotes: ArrayList<Quote> ->
            showQuotesList(quotes)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuotesBinding.inflate(layoutInflater, container, false)
        setupActionbarItems()
        binding.fabAddQuote.setOnClickListener {
            showDialogToAddQuote()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.readQuotesList(requireActivity())
    }

    private fun showDialogToAddQuote() {
        findNavController().navigate(R.id.action_quotesFragment_to_myQuoteDialog)
    }

    private fun setupActionbarItems() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {

            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.quotes_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.actionDownload -> {
                        viewModel.downloadQuotes(requireActivity())
                    }

                    android.R.id.home -> {
                        viewModel.closeFragment(this@QuotesFragment)
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showQuotesList(quotes: ArrayList<Quote>) {
        binding.recyclerQuotes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val quotesRecyclerAdapter = QuotesRecyclerAdapter(quotes, this)
        binding.recyclerQuotes.adapter = quotesRecyclerAdapter
    }

    override fun onItemClick(quote: Quote) {
        viewModel.saveQuote(quote, requireActivity())
        AppUtils.updateWidgetUI(requireContext())
        viewModel.closeFragment(this@QuotesFragment)
    }

    override fun onShareClick(quote: Quote) {
        AppUtils.shareQuote(requireContext(), quote)
    }

    override fun onCopyClick(quote: Quote) {
        AppUtils.copyQuote(requireContext(), quote)
    }
}