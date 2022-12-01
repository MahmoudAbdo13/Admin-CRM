package com.brokerageInsurance.admincrm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.brokerageInsurance.admincrm.data.Call
import com.brokerageInsurance.admincrm.data.Customer
import com.brokerageInsurance.admincrm.data.Visit

@Database(entities = [Customer::class, Call::class, Visit::class], version = 1)
abstract class CustomerDatabase : RoomDatabase() {
    abstract fun customerDao(): CustomerDao

    companion object {

        @Synchronized
        fun getInstance(context: Context): CustomerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CustomerDatabase::class.java,
                "customer_database"
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}