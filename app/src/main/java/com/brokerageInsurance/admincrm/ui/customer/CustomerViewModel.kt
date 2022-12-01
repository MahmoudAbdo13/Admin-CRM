package com.brokerageInsurance.admincrm.ui.customer

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brokerageInsurance.admincrm.data.Customer

class CustomerViewModel : ViewModel() {

    private var mDataClass = MutableLiveData<List<Customer>>()
    private lateinit var repo: Repository

    fun intiViewModel(fragment: Fragment) {
        repo = Repository.initializeModel(fragment)
    }

    private fun customerLiveData() {
        mDataClass = repo.customerLiveData()
    }

    fun getData(): MutableLiveData<List<Customer>> {
        customerLiveData()
        return mDataClass
    }

    fun updateCustomer(it: Customer) {
        repo.update(it)
    }
}