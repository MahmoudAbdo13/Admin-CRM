package com.brokerageInsurance.admincrm.database

import androidx.room.*
import com.brokerageInsurance.admincrm.data.Call
import com.brokerageInsurance.admincrm.data.Customer
import com.brokerageInsurance.admincrm.data.Visit

@Dao
interface CustomerDao {

    @Query("SELECT * FROM customer_table")
    fun getAllCustomers(): List<Customer>

    @Query("SELECT * FROM call_table WHERE uId = :uId")
    fun getAllCalls(uId: Int): List<Call>

    @Query("SELECT * FROM visit_table WHERE uId = :uId")
    fun getAllVisits(uId: Int): List<Visit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCall(call: Call)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addVisit(visit: Visit)

    @Update
    fun followCustomer(customer: Customer)
}