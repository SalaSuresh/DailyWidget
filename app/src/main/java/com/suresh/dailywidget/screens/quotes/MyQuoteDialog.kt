package com.suresh.dailywidget.screens.quotes

import android.content.SharedPreferences
import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.suresh.dailywidget.R
import com.suresh.dailywidget.databinding.DialogFragmentMyquoteBinding
import com.suresh.dailywidget.preferences.WidgetPreferences
import com.suresh.dailywidget.utils.AppUtils


class MyQuoteDialog : DialogFragment() {
    private lateinit var binding: DialogFragmentMyquoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentMyquoteBinding.inflate(layoutInflater, container, false)
        binding.buttonSave.setOnClickListener {
            val myQuote: String = binding.editMyQuote.text.toString()
            val widgetPreferences = WidgetPreferences(requireActivity())
            widgetPreferences.saveQuote(myQuote)
            val preferences: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(requireActivity())
            val appName:String = requireActivity().getString(R.string.app_name)
            val quoteMaster:String = preferences.getString("keyUserName", appName)!!
            widgetPreferences.saveQuoteMaster(quoteMaster)
            AppUtils.updateWidgetUI(requireContext())
            findNavController().popBackStack()
        }
        return binding.root
    }

    override fun onResume() {
        // Set the width of the dialog proportional to 90% of the screen width
        val window = dialog!!.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
        super.onResume()
    }
}