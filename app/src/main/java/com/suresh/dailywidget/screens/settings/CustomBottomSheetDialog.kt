package com.suresh.dailywidget.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.suresh.dailywidget.adapter.ColorPaletteRecyclerAdapter
import com.suresh.dailywidget.databinding.CustomBottomSheetBinding
import com.suresh.dailywidget.models.PaletteColor
import com.suresh.dailywidget.preferences.WidgetPreferences

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

    private fun showRecyclerItems() {
        val colorsList: ArrayList<PaletteColor> = ArrayList()
        val paletteColor = PaletteColor()
        paletteColor.textColor = "#000000"
        paletteColor.backgroundColor = "#f0f0eb"

        val paletteColor2 = PaletteColor()
        paletteColor2.textColor = "#FFFFFF"
        paletteColor2.backgroundColor = "#61abc7"

        val paletteColor3 = PaletteColor()
        paletteColor3.textColor = "#FFFFFF"
        paletteColor3.backgroundColor = "#592496"

        val paletteColor4 = PaletteColor()
        paletteColor4.textColor = "#000000"
        paletteColor4.backgroundColor = "#cf4654"

        val paletteColor5 = PaletteColor()
        paletteColor5.textColor = "#FFFFFF"
        paletteColor5.backgroundColor = "#d6c918"

        val paletteColor6 = PaletteColor()
        paletteColor6.textColor = "#FFFFFF"
        paletteColor6.backgroundColor = "#183ed6"

        val paletteColor7 = PaletteColor()
        paletteColor7.textColor = "#FFFFFF"
        paletteColor7.backgroundColor = "#183de6"

        colorsList.add(paletteColor)
        colorsList.add(paletteColor2)
        colorsList.add(paletteColor3)
        colorsList.add(paletteColor4)
        colorsList.add(paletteColor5)
        colorsList.add(paletteColor6)
        colorsList.add(paletteColor7)

        colorPaletteRecyclerAdapter =
            ColorPaletteRecyclerAdapter(requireContext(), colorsList, this@CustomBottomSheetDialog)
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
    }
}