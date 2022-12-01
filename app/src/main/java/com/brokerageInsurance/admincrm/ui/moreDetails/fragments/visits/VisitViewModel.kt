package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.visits

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brokerageInsurance.admincrm.data.Visit

class VisitViewModel : ViewModel() {

    private var visitLiveData = MutableLiveData<List<Visit>>()
    private lateinit var repo: VisitRepository

    fun intiViewModel(fragment: Fragment) {
        repo = VisitRepository.initializeModel(fragment)
    }

    private fun visitsLiveData(uId: Int) {
        visitLiveData = repo.visitsLiveData(uId)
    }

    fun getVisits(uId: Int): MutableLiveData<List<Visit>> {
        visitsLiveData(uId)
        return visitLiveData
    }

    fun insertVisit(visit: Visit) {
        repo.addVisit(visit)
    }
}