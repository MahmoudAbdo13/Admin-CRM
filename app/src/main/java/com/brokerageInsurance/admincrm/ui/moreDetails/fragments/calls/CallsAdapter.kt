package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.calls

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brokerageInsurance.admincrm.data.Call
import com.brokerageInsurance.admincrm.databinding.ItemVisitBinding

class CallsAdapter : ListAdapter<Call, CallsAdapter.CallViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CallViewHolder {
        val binding = ItemVisitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CallViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CallViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class CallViewHolder(private val binding: ItemVisitBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(call: Call) {
            binding.apply {
                textViewDate.text = "This visit did at: ${call.createdDateFormatted}"
                textViewDuration.text = "Take: ${call.callLength} Minutes"
                textViewResult.text = call.result
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Call>() {
        override fun areItemsTheSame(oldItem: Call, newItem: Call) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Call, newItem: Call) = oldItem == newItem

    }

}