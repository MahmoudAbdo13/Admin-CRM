package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.calls

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brokerageInsurance.admincrm.R
import com.brokerageInsurance.admincrm.databinding.FragmentCallsBinding


class CallsFragment : Fragment(R.layout.fragment_calls) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCallsBinding.bind(view)
        val callAdapter = CallsAdapter()

        val viewModel = ViewModelProvider(this)[CallViewModel::class.java]
        viewModel.intiViewModel(this)

        binding.apply {
            callRecyclerView.apply {
                adapter = callAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            val sharedPreference =
                requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
            viewModel.getCalls(sharedPreference.getInt("uId", 0))
                .observe(viewLifecycleOwner) { calls ->
                    if (calls.isNotEmpty()) {
                        progressBar.isVisible = false
                        callAdapter.submitList(calls)
                    } else {
                        progressBar.isVisible = false
                        noCallsMessage.isVisible = true
                    }
                }
        }
    }
}