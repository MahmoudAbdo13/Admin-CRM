package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.visits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brokerageInsurance.admincrm.data.Visit
import com.brokerageInsurance.admincrm.databinding.ItemVisitBinding

class VisitAdapter : ListAdapter<Visit, VisitAdapter.VisitViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitViewHolder {
        val binding = ItemVisitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VisitViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisitViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class VisitViewHolder(private val binding: ItemVisitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(visit: Visit) {
            binding.apply {
                textViewDate.text = "This visit did at: ${visit.createdDateFormatted}"
                textViewDuration.text = "Take: ${visit.visitLength} Minutes"
                textViewResult.text = visit.result
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Visit>() {
        override fun areItemsTheSame(oldItem: Visit, newItem: Visit) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Visit, newItem: Visit) = oldItem == newItem

    }

}