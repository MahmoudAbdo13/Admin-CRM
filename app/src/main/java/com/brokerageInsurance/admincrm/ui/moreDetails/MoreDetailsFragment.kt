package com.brokerageInsurance.admincrm.ui.moreDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.brokerageInsurance.admincrm.R
import com.brokerageInsurance.admincrm.databinding.FragmentMoreDetailsBinding
import com.brokerageInsurance.admincrm.ui.moreDetails.fragments.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class MoreDetailsFragment : Fragment() {
    private val args: MoreDetailsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentMoreDetailsBinding.bind(view)

        val fm: FragmentManager = requireActivity().supportFragmentManager

        val adapter = FragmentAdapter(fm, lifecycle)
        binding.apply {
            viewPager2.adapter = adapter
            tabLayout.addTab(tabLayout.newTab().setText("Calls"))
            tabLayout.addTab(binding.tabLayout.newTab().setText("Visits"))
            tabLayout.setBackgroundResource(R.drawable.tab_border)
            tabLayout.tabGravity = TabLayout.GRAVITY_FILL


            binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.viewPager2.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            binding.viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                }
            })
        }

        val sharedPreference =  requireActivity().getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt("uId",args.uId)
        editor.commit()
    }
}