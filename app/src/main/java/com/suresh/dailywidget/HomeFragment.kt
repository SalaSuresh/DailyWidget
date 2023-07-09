package com.suresh.dailywidget

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.suresh.dailywidget.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val preferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val name = preferences.getString("keyUserName", "ABCD")
        val showQuotes = preferences.getBoolean("keyShowQuotes", false)
        val showRefresh = preferences.getBoolean("keyShowRefresh", false)
        Log.d("test", "NAME:::: $name")
        Log.d("test", "showQuotes:::: $showQuotes")
        Log.d("test", "showRefresh:::: $showRefresh")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        setupActionbarItems()
        return binding.root
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