package com.suresh.dailywidget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suresh.dailywidget.R
import com.suresh.dailywidget.model.Quote

class QuotesRecyclerAdapter(
    private val quotes: List<Quote>,
    private val itemClick: QuoteSelectListener
) :
    RecyclerView.Adapter<QuotesRecyclerAdapter.QuoteViewHolder>() {

    interface QuoteSelectListener {
        fun onItemClick(quote: Quote)
    }

    class QuoteViewHolder(itemView: View, private val itemClick: QuoteSelectListener) :
        RecyclerView.ViewHolder(itemView) {
        private val textQuote: TextView = itemView.findViewById(R.id.textQuote)
        private val textQuoteMaster: TextView = itemView.findViewById(R.id.textQuoteMaster)
        fun bindData(quote: Quote) {
            textQuote.text = quote.quote
            textQuoteMaster.text = quote.quotemaster
            itemView.setOnClickListener {
                itemClick.onItemClick(quote)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_quote, parent, false)
        return QuoteViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return quotes.size
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bindData(quotes[position])
    }
}