package com.brokerageInsurance.admincrm.ui.customer

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.brokerageInsurance.admincrm.data.Customer
import com.brokerageInsurance.admincrm.database.CustomerDatabase
import com.google.android.material.snackbar.Snackbar
import java.util.ArrayList

class Repository {
    private var mCustomers = ArrayList<Customer>()
    val mDatabase= CustomerDatabase.getInstance(mFragment.requireContext())

    companion object
    {
        lateinit var mFragment: Fragment
        fun initializeModel(fragment: Fragment): Repository
        {
            mFragment=fragment
            return Repository()
        }
    }
    private fun getDatabase() {
        val mEntry=mDatabase.customerDao().getAllCustomers()
        for(m in mEntry)
        {
            mCustomers.add(m)
        }
    }
    fun customerLiveData(): MutableLiveData<List<Customer>>
    {
        getDatabase()
        val mMutable= MutableLiveData<List<Customer>>()
        mMutable.postValue(mCustomers)
        return mMutable
    }

    fun update(customer: Customer){
        mDatabase.customerDao().followCustomer(customer)
        Snackbar.make(
            mFragment.requireView(),
            "Customer Updated Successfully",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

}