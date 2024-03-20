package com.example.restaurantreview.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreview.data.response.CustomerReviewsItem
import com.example.restaurantreview.databinding.ItemReviewBinding

class ReviewAdapter : ListAdapter<CustomerReviewsItem, ReviewAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CustomerReviewsItem>() {
            override fun areItemsTheSame(
                oldItem: CustomerReviewsItem,
                newItem: CustomerReviewsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CustomerReviewsItem,
                newItem: CustomerReviewsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: CustomerReviewsItem) {
            binding.tvItem.text = "${review.review}\n- ${review.name}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
}