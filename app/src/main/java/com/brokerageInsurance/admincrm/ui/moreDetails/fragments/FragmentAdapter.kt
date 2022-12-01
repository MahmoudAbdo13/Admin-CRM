package com.brokerageInsurance.admincrm.ui.moreDetails.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.brokerageInsurance.admincrm.ui.moreDetails.fragments.calls.CallsFragment
import com.brokerageInsurance.admincrm.ui.moreDetails.fragments.visits.VisitsFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> VisitsFragment()
            else -> CallsFragment()
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}