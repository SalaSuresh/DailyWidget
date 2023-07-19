package com.suresh.dailywidget.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.suresh.dailywidget.R
import com.suresh.dailywidget.models.PaletteColor

class ColorPaletteRecyclerAdapter(private val paletteColors: ArrayList<PaletteColor>) :
    RecyclerView.Adapter<ColorPaletteRecyclerAdapter.ColorPaletteViewHolder>() {

    class ColorPaletteViewHolder(itemView: View) : ViewHolder(itemView) {
        private val layoutA: CardView = itemView.findViewById(R.id.cardLayoutA)
        private val textA: TextView = itemView.findViewById(R.id.textA)
        fun bindView(paletteColor: PaletteColor) {
            layoutA.setCardBackgroundColor(Color.parseColor(paletteColor.backgroundColor))
            textA.setTextColor(Color.parseColor(paletteColor.textColor))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPaletteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_color_palette, parent, false)
        return ColorPaletteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return paletteColors.size
    }

    override fun onBindViewHolder(holder: ColorPaletteViewHolder, position: Int) {
        holder.bindView(paletteColors[position])
    }
}