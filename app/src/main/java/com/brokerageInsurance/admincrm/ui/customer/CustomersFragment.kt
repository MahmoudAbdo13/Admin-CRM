package com.brokerageInsurance.admincrm.ui.customer

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brokerageInsurance.admincrm.R
import com.brokerageInsurance.admincrm.databinding.FragmentCustomersBinding


class CustomersFragment : Fragment(R.layout.fragment_customers) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCustomersBinding.bind(view)
        val customersAdapter = CustomersAdapter()

        val viewModel = ViewModelProvider(this)[CustomerViewModel::class.java]
        viewModel.intiViewModel(this)

        binding.apply {
            recyclerView.apply {
                adapter = customersAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.getData().observe(viewLifecycleOwner) { customers ->
                if (customers.isNotEmpty()) {
                    progressBar.isVisible = false
                    customersAdapter.submitList(customers)
                } else {
                    progressBar.isVisible = false
                    textViewErrorMessage.isVisible = true
                }
            }
            fabAddCustomer.setOnClickListener {
                val action = CustomersFragmentDirections.actionCustomersFragmentToAddFragment()
                findNavController().navigate(action)
            }
        }
    }
}