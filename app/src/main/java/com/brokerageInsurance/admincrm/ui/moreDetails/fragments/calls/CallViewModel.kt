package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.calls

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brokerageInsurance.admincrm.data.Call

class CallViewModel : ViewModel() {

    private var callLiveData = MutableLiveData<List<Call>>()
    private lateinit var repo: CallRepository

    fun intiViewModel(fragment: Fragment) {
        repo = CallRepository.initializeModel(fragment)
    }

    private fun visitsLiveData(uId: Int) {
        callLiveData = repo.callsLiveData(uId)
    }

    fun getCalls(uId: Int): MutableLiveData<List<Call>> {
        visitsLiveData(uId)
        return callLiveData
    }

    fun insertCall(call: Call) {
        repo.addCall(call)
    }
}