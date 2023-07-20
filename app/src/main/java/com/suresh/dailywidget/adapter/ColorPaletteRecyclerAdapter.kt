package com.suresh.dailywidget.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suresh.dailywidget.R
import com.suresh.dailywidget.models.PaletteColor
import com.suresh.dailywidget.preferences.WidgetPreferences

class ColorPaletteRecyclerAdapter(
    private val context: Context,
    private val paletteColors: ArrayList<PaletteColor>,
    private val listener: ColorSelectListener
) :
    RecyclerView.Adapter<ColorPaletteRecyclerAdapter.ColorPaletteViewHolder>() {
    private lateinit var widgetPreferences: WidgetPreferences

    class ColorPaletteViewHolder(itemView: View) : ViewHolder(itemView) {
        private val layoutA: CardView = itemView.findViewById(R.id.cardLayoutA)
        private val imageSelector: ImageView = itemView.findViewById(R.id.imageSelector)
        private val textA: TextView = itemView.findViewById(R.id.textA)
        private val layoutItem: ConstraintLayout = itemView.findViewById(R.id.layoutItem)
        fun bindView(
            paletteColor: PaletteColor,
            listener: ColorSelectListener,
            widgetColor: PaletteColor
        ) {
            layoutA.setCardBackgroundColor(Color.parseColor(paletteColor.backgroundColor))
            textA.setTextColor(Color.parseColor(paletteColor.textColor))

            if (widgetColor.textColor == paletteColor.textColor &&
                widgetColor.backgroundColor == paletteColor.backgroundColor) {
                imageSelector.visibility = View.VISIBLE
                layoutItem.setBackgroundColor(Color.LTGRAY)
            } else {
                imageSelector.visibility = View.GONE
                layoutItem.setBackgroundColor(Color.WHITE)
            }

            layoutItem.setOnClickListener {
                listener.onColorSelected(paletteColor)
                imageSelector.visibility = View.VISIBLE
                layoutItem.setBackgroundColor(Color.LTGRAY)
            }
        }
    }

    interface ColorSelectListener {
        fun onColorSelected(paletteColor: PaletteColor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPaletteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_color_palette, parent, false)
        widgetPreferences = WidgetPreferences(context)
        return ColorPaletteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return paletteColors.size
    }

    override fun onBindViewHolder(holder: ColorPaletteViewHolder, position: Int) {
        holder.bindView(paletteColors[position], listener, widgetPreferences.getWidgetColor())
    }
}