package com.brokerageInsurance.admincrm.ui.addCustomer

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brokerageInsurance.admincrm.data.Call
import com.brokerageInsurance.admincrm.data.Customer

class AddViewModel : ViewModel() {
    private lateinit var repo: AddRepository

    fun intiViewModel(fragment: Fragment) {
        repo = AddRepository.intializeModel(fragment)
    }

    fun insertCustomer(customer: Customer) {
        repo.insertCustomer(customer)
    }

}