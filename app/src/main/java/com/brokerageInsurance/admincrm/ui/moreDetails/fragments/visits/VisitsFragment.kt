package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.visits

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.brokerageInsurance.admincrm.R
import com.brokerageInsurance.admincrm.databinding.FragmentCustomersBinding
import com.brokerageInsurance.admincrm.databinding.FragmentVisitsBinding
import com.brokerageInsurance.admincrm.ui.customer.CustomerViewModel
import com.brokerageInsurance.admincrm.ui.customer.CustomersAdapter


class VisitsFragment : Fragment(R.layout.fragment_visits) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentVisitsBinding.bind(view)
        val visitAdapter = VisitAdapter()

        val viewModel = ViewModelProvider(this)[VisitViewModel::class.java]
        viewModel.intiViewModel(this)

        binding.apply {
            visitRecyclerView.apply {
                adapter = visitAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            viewModel.getVisits(sharedPreference.getInt("uId", 0)).observe(viewLifecycleOwner) { visits ->
                if (visits.isNotEmpty()) {
                    progressBar.isVisible = false
                    visitAdapter.submitList(visits)
                } else {
                    progressBar.isVisible = false
                    noVisitsMessage.isVisible = true
                }
            }
        }
    }
}