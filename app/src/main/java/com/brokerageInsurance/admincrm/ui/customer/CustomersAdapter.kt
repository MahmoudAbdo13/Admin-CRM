package com.brokerageInsurance.admincrm.ui.customer

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brokerageInsurance.admincrm.data.Customer
import com.brokerageInsurance.admincrm.databinding.ItemCustomerBinding

class CustomersAdapter :
    ListAdapter<Customer, CustomersAdapter.CustomerViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
            ItemCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem, position)
    }

    class CustomerViewHolder(private val binding: ItemCustomerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(customer: Customer, number: Int) {
            binding.apply {
                textViewName.text = customer.name
                //number+=1
                textViewNumber.text = (number + 1).toString()
                if (customer.followUp)
                    textViewFollow.text = "Followed Up"
                itemView.setOnClickListener {
                    val action =
                        CustomersFragmentDirections.actionCustomersFragmentToCustomerDetailsFragment(
                            customer
                        )
                    itemView.findNavController().navigate(action)
                    Log.e("log", customer.createdDateFormatted)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Customer, newItem: Customer) = oldItem == newItem

    }

}