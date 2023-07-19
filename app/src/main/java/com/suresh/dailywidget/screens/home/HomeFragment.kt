package com.suresh.dailywidget.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.suresh.dailywidget.R
import com.suresh.dailywidget.databinding.FragmentHomeBinding
import com.suresh.dailywidget.models.Quote
import com.suresh.dailywidget.preferences.WidgetPreferences
import com.suresh.dailywidget.utils.AppUtils

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var viewAllFab: Boolean = false
    private lateinit var widgetPreferences: WidgetPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        widgetPreferences = WidgetPreferences(requireContext())
        /*val preferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val name = preferences.getString("keyUserName", "ABCD")
        val showQuotes = preferences.getBoolean("keyShowQuotes", false)
        val showRefresh = preferences.getBoolean("keyShowRefresh", false)
        Log.d("test", "NAME:::: $name")
        Log.d("test", "showQuotes:::: $showQuotes")
        Log.d("test", "showRefresh:::: $showRefresh")*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupActionbarItems()

        binding.fabRefresh.visibility = View.GONE
        binding.fabMore.visibility = View.GONE
        binding.fabShare.visibility = View.GONE

        binding.textClose.visibility = View.GONE
        binding.textRefresh.visibility = View.GONE
        binding.textMore.visibility = View.GONE
        binding.textShare.visibility = View.GONE

        viewAllFab = false
        binding.fabActions.setOnClickListener {
            if (!viewAllFab) {
                binding.fabRefresh.show()
                binding.fabMore.show()
                binding.fabShare.show()

                binding.textClose.visibility = View.VISIBLE
                binding.textRefresh.visibility = View.VISIBLE
                binding.textMore.visibility = View.VISIBLE
                binding.textShare.visibility = View.VISIBLE

                binding.fabActions.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_close
                    )
                )
                viewAllFab = true
            } else {
                binding.fabRefresh.hide()
                binding.fabMore.hide()
                binding.fabShare.hide()

                binding.textClose.visibility = View.GONE
                binding.textRefresh.visibility = View.GONE
                binding.textMore.visibility = View.GONE
                binding.textShare.visibility = View.GONE

                binding.fabActions.setImageDrawable(
                    AppCompatResources.getDrawable(
                        requireActivity(),
                        R.drawable.ic_quote_light
                    )
                )

                viewAllFab = false
            }
        }

        binding.fabRefresh.setOnClickListener {
            Log.d("test", "Fab clicked")
            //TODO("Need to implement")
        }
        binding.fabMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_quotesFragment)
        }
        binding.fabShare.setOnClickListener {
//            val quote = widgetPreferences.getQuote()
//            val quoteMaster = widgetPreferences.getQuoteMaster()
            val quote = Quote()
            quote.quote = widgetPreferences.getQuote()
            quote.quotemaster = widgetPreferences.getQuoteMaster()
            AppUtils.shareQuote(requireContext(), quote)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val widgetPreferences = WidgetPreferences(requireContext())
        val quote = widgetPreferences.getQuote()
        val quoteMaster = widgetPreferences.getQuoteMaster()
        binding.textQuote.text = quote
        binding.textQuoteMaster.text = quoteMaster
    }

    private fun setupActionbarItems() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {

            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.app_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.actionSettings) {
                    findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

}