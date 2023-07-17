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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suresh.dailywidget.R
import com.suresh.dailywidget.adapter.QuotesRecyclerAdapter2
import com.suresh.dailywidget.databinding.FragmentQuotesBinding
import com.suresh.dailywidget.models.Quote

class QuotesFragment : Fragment(), QuotesRecyclerAdapter2.QuoteSelectListener {
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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuotesBinding.inflate(layoutInflater, container, false)
        setupActionbarItems()
        showQuotesList()
        binding.fabAddQuote.setOnClickListener {
            showDialogToAddQuote()
        }
        return binding.root
    }

    private fun showDialogToAddQuote() {
        TODO("Not yet implemented")
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

    private fun showQuotesList() {
        binding.recyclerQuotes.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val quotesList = viewModel.getQuotesList(requireActivity())
        val quotesRecyclerAdapter =
            QuotesRecyclerAdapter2(quotesList, this)
        binding.recyclerQuotes.adapter = quotesRecyclerAdapter
    }

    override fun onItemClick(quote: Quote) {
        TODO("Not yet implemented")
    }

    override fun onShareClick(quote: Quote) {
        TODO("Not yet implemented")
    }
}