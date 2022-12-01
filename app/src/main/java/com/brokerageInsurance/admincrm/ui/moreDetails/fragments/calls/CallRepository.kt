package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.calls

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.brokerageInsurance.admincrm.data.Call
import com.brokerageInsurance.admincrm.database.CustomerDatabase
import com.google.android.material.snackbar.Snackbar

class CallRepository {
    private var callsList = ArrayList<Call>()
    val database = CustomerDatabase.getInstance(mFragment.requireContext())
    companion object {
        lateinit var mFragment: Fragment
        fun initializeModel(fragment: Fragment): CallRepository {
            mFragment = fragment
            return CallRepository()
        }
    }

    private fun getDatabase(id: Int) {
        val calls = database.customerDao().getAllCalls(id)
        for (call in calls) {
            callsList.add(call)
        }
    }

    fun callsLiveData(id: Int): MutableLiveData<List<Call>> {
        getDatabase(id)
        val callLiveData = MutableLiveData<List<Call>>()
        callLiveData.postValue(callsList)
        return callLiveData
    }

    fun addCall(call: Call){
        database.customerDao().addCall(call)
        Snackbar.make(
            mFragment.requireView(),
            "Call Added Successfully",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }
}