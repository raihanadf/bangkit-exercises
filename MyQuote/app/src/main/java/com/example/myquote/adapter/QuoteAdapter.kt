package com.example.myquote.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myquote.R
import com.example.myquote.adapter.QuoteAdapter.ViewHolder

class QuoteAdapter(private val listReview: ArrayList<String>) : RecyclerView.Adapter<ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quote, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listReview.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvItem.text = listReview[position]
    }

}