package com.brokerageInsurance.admincrm.ui.addCustomer

import androidx.fragment.app.Fragment
import com.brokerageInsurance.admincrm.data.Customer
import com.brokerageInsurance.admincrm.database.CustomerDatabase

class AddRepository {

    companion object {
        lateinit var mFragment: Fragment
        fun intializeModel(fragment: Fragment): AddRepository {
            mFragment = fragment
            return AddRepository()
        }
    }

    fun insertCustomer(customer: Customer) {
        val database = CustomerDatabase.getInstance(mFragment.requireContext())
        database.customerDao().addCustomer(customer)
    }
}