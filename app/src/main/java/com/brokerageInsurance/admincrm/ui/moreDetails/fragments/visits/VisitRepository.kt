package com.brokerageInsurance.admincrm.ui.moreDetails.fragments.visits

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.brokerageInsurance.admincrm.database.CustomerDatabase
import com.brokerageInsurance.admincrm.data.Visit
import com.google.android.material.snackbar.Snackbar

class VisitRepository {
    private var visitList = ArrayList<Visit>()
    val mDatabase = CustomerDatabase.getInstance(mFragment.requireContext())

    companion object {
        lateinit var mFragment: Fragment
        fun initializeModel(fragment: Fragment): VisitRepository {
            mFragment = fragment
            return VisitRepository()
        }
    }

    private fun getDatabase(id: Int) {
        val visits = mDatabase.customerDao().getAllVisits(id)
        for (visit in visits) {
            visitList.add(visit)
        }
    }

    fun visitsLiveData(id: Int): MutableLiveData<List<Visit>> {
        getDatabase(id)
        val visitLiveData = MutableLiveData<List<Visit>>()
        visitLiveData.postValue(visitList)
        return visitLiveData
    }

    fun addVisit(visit: Visit) {
        mDatabase.customerDao().addVisit(visit)
        Snackbar.make(
            mFragment.requireView(),
            "Visit Added Successfully",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }
}