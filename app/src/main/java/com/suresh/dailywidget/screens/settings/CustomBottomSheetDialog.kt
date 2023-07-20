package com.suresh.dailywidget.screens.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.suresh.dailywidget.adapter.ColorPaletteRecyclerAdapter
import com.suresh.dailywidget.constants.ApplicationConstants
import com.suresh.dailywidget.databinding.CustomBottomSheetBinding
import com.suresh.dailywidget.models.PaletteColor
import com.suresh.dailywidget.preferences.WidgetPreferences
import com.suresh.dailywidget.utils.AppUtils

class CustomBottomSheetDialog : BottomSheetDialogFragment(),
    ColorPaletteRecyclerAdapter.ColorSelectListener {
    private lateinit var binding: CustomBottomSheetBinding
    private lateinit var colorPaletteRecyclerAdapter: ColorPaletteRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CustomBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add any listeners or event handlers that you need to your bottom sheet here.

        // Set the peek height of the bottom sheet.
        (view.parent as View).layoutParams.height = 200

        showRecyclerItems()
    }

    private fun getPaletteColors(context: Context, fileName: String): ArrayList<PaletteColor> {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)

        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val listType = object : TypeToken<ArrayList<PaletteColor?>?>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    private fun showRecyclerItems() {
        colorPaletteRecyclerAdapter =
            ColorPaletteRecyclerAdapter(
                requireContext(),
                getPaletteColors(requireContext(), ApplicationConstants.COLORS_FILE_NAME),
                this@CustomBottomSheetDialog
            )
        binding.recyclerColorPalette.layoutManager =
            LinearLayoutManager(requireContext(), GridLayoutManager.HORIZONTAL, false)
        binding.recyclerColorPalette.adapter = colorPaletteRecyclerAdapter
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            CustomBottomSheetDialog().show(fragmentManager, "CustomBottomSheet")
        }
    }

    override fun onColorSelected(paletteColor: PaletteColor) {
        val widgetPreferences = WidgetPreferences(requireContext())
        widgetPreferences.saveWidgetColor(paletteColor)
        colorPaletteRecyclerAdapter.notifyDataSetChanged()
        AppUtils.updateWidgetUI(requireContext())
    }
}